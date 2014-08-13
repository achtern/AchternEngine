package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.contracts.PassFilter;
import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.binding.LWJGLDataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.shadow.BasicShadowRenderer;
import io.github.achtern.AchternEngine.core.rendering.sorting.AmbientFirstSorter;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;
import io.github.achtern.AchternEngine.core.util.CommonDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class LWJGLRenderEngine extends CommonDataStore implements RenderEngine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RenderEngine.class);

    protected Camera mainCamera;

    protected LWJGLDataBinder dataBinder;

    protected ArrayList<RenderPass> passes;
    protected RenderPass activePass;

    protected RenderTarget target;

    protected Color clearColor;

    protected DrawStrategy drawStrategy;

    //TMP will be proteceted after testing!
    public List<PassFilter> passFilters;

    public LWJGLRenderEngine() {

        dataBinder = new LWJGLDataBinder(this);

        drawStrategy = DrawStrategyFactory.get(DrawStrategyFactory.Common.SOLID);
        clearColor = new Color(0, 0, 0, 0);

        passes = new ArrayList<RenderPass>();
        passFilters = new ArrayList<PassFilter>();

        // TODO: do not hardcode this filter!
        addPassFilter(new BasicShadowRenderer());

        setRenderTarget(Window.getTarget());


        addInteger("diffuse", 0);
        addInteger("shadowMap", 1);

        setClearColor(clearColor);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        glEnable(GL_DEPTH_CLAMP);

        useTextures(true);
    }

    @Override
    public void render(Node node) {
        render(node, true);
    }

    @Override
    public void render(Node node, boolean clear) {

        if (target != null) {
            target.bindAsRenderTarget();
        }

        if (clear) {
            clearScreen();
        }

        if (passes.isEmpty()) {
            LOGGER.debug("No render passes. Skip render!");
            return;
        }

        this.activePass = passes.get(0);
        LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());

        if (!(this.activePass instanceof AmbientLight)) {
            LOGGER.trace("First Pass not instance of AmbientLight, sorting...");
            Collections.sort(passes, new AmbientFirstSorter());
            this.activePass = passes.get(0);
        }
        node.render(activePass.getShader(), this);


        boolean skip = true;
        for (RenderPass pass : this.passes) {
            if (skip) {
                skip = false;
                continue;
            }

            this.activePass = pass;

            for (PassFilter filter : passFilters) {
                filter.pre(node, pass, this);
            }


            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE);
            glDepthMask(false);
            glDepthFunc(GL_EQUAL);
            {

                LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());
                node.render(pass.getShader(), this);

                for (PassFilter filter : passFilters) {
                    filter.post(node, pass, this);
                }
            }
            glDepthFunc(GL_LESS);
            glDepthMask(true);
            glDisable(GL_BLEND);
        }

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
        if (pass instanceof AmbientLight) {
            this.passes.add(0, pass);
        } else {
            this.passes.add(pass);
        }
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
    public int getSamplerSlot(String name) {
        return getInteger(name);
    }

    @Override
    public DataBinder getDataBinder() {
        return dataBinder;
    }

    @Override
    public void addPassFilter(PassFilter filter) {
        filter.init(this);
        passFilters.add(filter);
    }

    /**
     * Removes a PassFilter from the RenderEngine
     *
     * @param filter The filter to remove
     * @return Whether the remove was successful.
     */
    @Override
    public boolean removePassFilter(PassFilter filter) {
        return passFilters.remove(filter);
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
