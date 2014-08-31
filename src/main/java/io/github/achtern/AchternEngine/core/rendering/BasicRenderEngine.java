/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.bootstrap.BindingProvider;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.shadow.BasicShadowRenderer;
import io.github.achtern.AchternEngine.core.rendering.sorting.AmbientFirstSorter;
import io.github.achtern.AchternEngine.core.rendering.state.*;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.GlobalEntity;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;
import io.github.achtern.AchternEngine.core.util.CommonDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BasicRenderEngine extends CommonDataStore implements RenderEngine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RenderEngine.class);

    protected Camera mainCamera;

    protected DataBinder dataBinder;

    protected RenderEngineState state;

    protected ArrayList<RenderPass> passes;
    protected RenderPass activePass;

    protected Map<Class, GlobalEntity> globalEntities;

    protected RenderTarget target;

    protected DrawStrategy drawStrategy;

    protected List<PassFilter> passFilters;

    public BasicRenderEngine(BindingProvider provider) {

        dataBinder = provider.getDataBinder();
        state = provider.getRenderEngineState();

        drawStrategy = DrawStrategyFactory.get(DrawStrategyFactory.Common.SOLID);

        passes = new ArrayList<RenderPass>();
        passFilters = new ArrayList<PassFilter>();

        globalEntities = new HashMap<Class, GlobalEntity>();

        // TODO: do not hardcode this filter!
        addPassFilter(new BasicShadowRenderer());

        setRenderTarget(Window.get());

        state.setClearColor(new Color(0, 0, 0, 0));

        state.setFrontFace(FrontFaceMethod.CLOCKWISE);
        state.enable(Feature.CULL_FACE);
        state.cullFace(Face.BACK);
        state.enable(Feature.DEPTH_TEST);
        state.setDepthFunction(DepthFunction.LESS);

        state.enable(Feature.DEPTH_CLAMP);

        state.enable(Feature.TEXTURE_2D);
    }

    @Override
    public void render(Node node) {
        render(node, true);
    }

    @Override
    public void render(Node node, boolean clear) {

        if (target != null) {
            target.bindAsRenderTarget(getDataBinder());
        }

        if (clear) {
            state.clear(true, true, false);
        }

        if (passes.isEmpty()) {
            LOGGER.debug("No render passes. Skip render!");
            return;
        }

        this.activePass = passes.get(0);
        LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());

        boolean skip = true;

        if (!(this.activePass instanceof AmbientLight)) {
            LOGGER.trace("First Pass not instance of AmbientLight, sorting...");
            Collections.sort(passes, new AmbientFirstSorter());
            this.activePass = passes.get(0);
            if (!(this.activePass instanceof AmbientLight)) {
                LOGGER.info("Using own Ambient Path with Color(0.01f, 0.01f, 0.01f)");
                this.activePass = new AmbientLight(new Color(0.01f, 0.01f, 0.01f));
                skip = false;
            }
        }
        getDataBinder().bind(activePass.getShader());
        node.render(this);


        for (RenderPass pass : this.passes) {
            if (skip) {
                skip = false;
                continue;
            }

            this.activePass = pass;

            for (PassFilter filter : passFilters) {
                filter.pre(node, pass, this);
            }


            state.enable(Feature.BLEND);
            state.setBlendFunction(BlendFunction.ONE, BlendFunction.ONE);
            state.enableDepthWrite(false);
            state.setDepthFunction(DepthFunction.EQUAL);
            {

                LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());
                getDataBinder().bind(pass.getShader());
                node.render(this);

                for (PassFilter filter : passFilters) {
                    filter.post(node, pass, this);
                }
            }
            state.setDepthFunction(DepthFunction.LESS);
            state.enableDepthWrite(true);
            state.disable(Feature.BLEND);
        }

    }

    @Override
    public RenderEngineState getState() {
        return state;
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

    /**
     * internal use only
     */
    @Override
    public void setActiveRenderPass(RenderPass active) {
        this.activePass = active;
    }

    @Override
    public String getOpenGLVersion() {
        return state.getVersion();
    }

    @Override
    public Camera getMainCamera() {
        if (mainCamera == null) {
            LOGGER.error("Please add a camera to the scene graph!");
        }
        return mainCamera;
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
    public void addGlobal(GlobalEntity entity) {
        this.globalEntities.put(entity.getObject().getClass(), entity);
    }

    @Override
    public void removeGlobal(GlobalEntity entity) {
        this.globalEntities.remove(entity.getObject().getClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> GlobalEntity<T> getGlobal(Class<? extends T> type) {
        GlobalEntity g = this.globalEntities.get(type);
        if (g == null) return null;

        if (g.getObject().getClass().isAssignableFrom(type)) {
            return (GlobalEntity<T>) g;
        } else {
            return null;
        }
    }

    @Override
    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
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
