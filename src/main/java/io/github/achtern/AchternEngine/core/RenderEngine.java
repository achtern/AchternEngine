package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.entity.Camera;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.drawing.SolidDraw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderEngine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RenderEngine.class);

    private Camera mainCamera;

    private ArrayList<RenderPass> passes;
    private RenderPass activePass;

    private Color clearColor;

    private DrawStrategy drawStrategy;

    public RenderEngine() {

        drawStrategy = new SolidDraw();
        clearColor = new Color(0, 0, 0, 0);

        passes = new ArrayList<RenderPass>();

        setClearColor(clearColor);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);
    }

    public void render(Node node) {

        Window.bindAsRenderTarget();

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

    public void addRenderPass(RenderPass pass) {
        this.passes.add(pass);
    }

    public void removeRenderPass(RenderPass pass) {
        this.passes.remove(pass);
    }

    public void addCamera(Camera camera) {
        this.mainCamera = camera;
    }

    public RenderPass getActiveRenderPass() {
        return this.activePass;
    }

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

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }

    public Camera getMainCamera() {
        if (mainCamera == null) {
            LOGGER.error("Please add a camera to the scene graph!");
        }
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public Color getClearColor() {
        return clearColor;
    }

    public DrawStrategy getDrawStrategy() {
        return drawStrategy;
    }

    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
}
