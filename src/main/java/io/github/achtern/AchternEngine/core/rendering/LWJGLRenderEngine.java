package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class LWJGLRenderEngine implements RenderEngine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RenderEngine.class);

    protected Camera mainCamera;

    protected ArrayList<RenderPass> passes;
    protected RenderPass activePass;

    protected RenderTarget target;

    protected Color clearColor;

    protected DrawStrategy drawStrategy;

    public LWJGLRenderEngine() {

        drawStrategy = DrawStrategyFactory.get(DrawStrategyFactory.Common.SOLID);
        clearColor = new Color(0, 0, 0, 0);

        passes = new ArrayList<RenderPass>();
        target = Window.getTarget();

        setClearColor(clearColor);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
    }

    @Override
    public void render(Node node) {

        if (target != null) {
            target.bindAsRenderTarget();
        }

        clearScreen();

        if (passes.isEmpty()) {
            LOGGER.debug("No render passes. Skip render!");
            return;
        }

        this.activePass = passes.get(0);
        LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());
        node.render(activePass.getShader(), this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        boolean skip = true;
        for (RenderPass pass : this.passes) {
            if (skip) {
                skip = false;
                continue;
            }
            this.activePass = pass;
            LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());
            node.render(pass.getShader(), this);
        }


        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public void setRenderTarget(RenderTarget target) {
        this.target = target;
    }

    public RenderTarget getRenderTarget() {
        return target;
    }

    @Override
    public void addRenderPass(RenderPass pass) {
        LOGGER.debug("Added RenderPass {}", pass.getClass());
        this.passes.add(pass);
    }

    @Override
    public void removeRenderPass(RenderPass pass) {
        LOGGER.debug("Removing RenderPass {}", pass.getClass());
        this.passes.remove(pass);
    }

    @Override
    public void addCamera(Camera camera) {
        this.mainCamera = camera;
    }

    @Override
    public RenderPass getActiveRenderPass() {
        return this.activePass;
    }

    @Override
    public void setClearColor(Color color) {
        this.clearColor = color;
        glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    private static void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private static void useTextures(boolean enabled) {
        if (enabled) {
            glEnable(GL_TEXTURE_2D);
        } else {
            glDisable(GL_TEXTURE_2D);
        }
    }

    private static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    @Override
    public Camera getMainCamera() {
        if (mainCamera == null) {
            LOGGER.error("Please add a camera to the scene graph!");
        }
        return mainCamera;
    }

    @Override
    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    @Override
    public Color getClearColor() {
        return clearColor;
    }

    @Override
    public DrawStrategy getDrawStrategy() {
        return drawStrategy;
    }

    @Override
    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
}
