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

package org.achtern.AchternEngine.core.rendering;

import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.Window;
import org.achtern.AchternEngine.core.bootstrap.GraphicsBindingProvider;
import org.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.achtern.AchternEngine.core.rendering.shadow.BasicShadowRenderer;
import org.achtern.AchternEngine.core.rendering.sorting.NoShadowFirstSorter;
import org.achtern.AchternEngine.core.rendering.state.*;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Camera;
import org.achtern.AchternEngine.core.scenegraph.entity.GlobalEntity;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.BaseLight;
import org.achtern.AchternEngine.core.util.CommonDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BasicRenderEngine extends CommonDataStore implements RenderEngine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RenderEngine.class);

    @Getter protected RenderEngineState state;
    @Getter protected DataBinder dataBinder;

    @Getter @Setter protected RenderTarget renderTarget;

    protected List<RenderPass> renderPasses;
    protected RenderPass activePass;

    protected List<PassFilter> passFilters;

    @Getter @Setter protected Camera camera;

    @Getter @Setter protected DrawStrategy drawStrategy;

    protected Map<Class, GlobalEntity> globalEntities;

    public BasicRenderEngine(GraphicsBindingProvider graphicsBindingProvider) {
        this.state = graphicsBindingProvider.getRenderEngineState();
        this.dataBinder = graphicsBindingProvider.getDataBinder();

        this.setRenderTarget(Window.get());

        this.renderPasses = new ArrayList<RenderPass>();

        this.passFilters = new ArrayList<PassFilter>();

        this.drawStrategy = DrawStrategyFactory.get(DrawStrategyFactory.Common.SOLID);

        this.globalEntities = new HashMap<Class, GlobalEntity>();

        setupStates();

        // TODO: Do not hard code this filter
        addPassFilter(new BasicShadowRenderer());


    }

    @Override
    public void render(Node node) {
        this.render(node, true);
    }

    @Override
    public void render(Node node, boolean clear) {
        if (getRenderTarget() != null) {
            getRenderTarget().bindAsRenderTarget(getDataBinder());
        }

        if (clear) {
            state.clear(true, true, false);
        }

        if (renderPasses.isEmpty()) {
            LOGGER.debug("No render passes. Skip render!");
            return;
        }

        this.activePass = renderPasses.get(0);

        LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());


        boolean skip = true;


        if (hasShadow(this.activePass)) {
            LOGGER.debug("First Pass has shadow, sorting...");
            Collections.sort(renderPasses, new NoShadowFirstSorter());
            this.activePass = renderPasses.get(0);
            if (hasShadow(this.activePass)) {
                LOGGER.info("Using own Ambient Pass with Color(0.01f, 0.01f, 0.01f), to avoid shadow render loss");
                this.activePass = new AmbientLight(new Color(0.01f, 0.01f, 0.01f));
                skip = false;
            }
        }

        // Bind Shader, just bind the default shader, if there is no specified in Material
        getDataBinder().bind(this.activePass.getShader());
        // Render first pass
        node.render(this);

        // Now we enter the forward specific part

        for (RenderPass pass : this.renderPasses) {
            if (skip) {
                skip = false;
                continue;
            }

            this.activePass = pass;

            for (PassFilter filter : passFilters) {
                filter.pre(node, pass, this);
            }

            state.enable(Feature.BLEND); // we need to blend in the other passes
            state.setBlendFunction(BlendFunction.ONE, BlendFunction.ONE); // just one-to-one
            state.enableDepthWrite(false); // we have written depth in the first pass already!
            state.setDepthFunction(DepthFunction.EQUAL); // so just pass, when depth is equal!
            {
                LOGGER.trace("Rendering Pass of type: {}", this.activePass.getClass());
                getDataBinder().bind(pass.getShader()); // bind the shader
                node.render(this);
            }
            state.setDepthFunction(DepthFunction.LESS);
            state.enableDepthWrite(true);
            state.disable(Feature.BLEND);


            for (PassFilter filter : passFilters) {
                filter.post(node, pass, this);
            }
        }

    }

    @Override
    public void addRenderPass(RenderPass pass) {
        renderPasses.add(pass);
    }

    @Override
    public void removeRenderPass(RenderPass pass) {
        renderPasses.remove(pass);
    }

    @Override
    public RenderPass getActiveRenderPass() {
        return activePass;
    }

    /**
     * internal use only
     *
     * @param active Will be set as active RenderPass
     */
    @Override
    public void setActiveRenderPass(RenderPass active) {
        this.activePass = active;
    }

    @Override
    public void addPassFilter(PassFilter filter) {
        passFilters.add(filter);
        filter.init(this);
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
        return (GlobalEntity<T>) this.globalEntities.get(type);
    }

    protected void setupStates() {
        state.setClearColor(new Color(0, 0, 0, 0));

        state.setFrontFace(FrontFaceMethod.CLOCKWISE);
        state.enable(Feature.CULL_FACE);
        state.cullFace(Face.BACK);
        state.enable(Feature.DEPTH_TEST);
        state.setDepthFunction(DepthFunction.LESS);

        state.enable(Feature.DEPTH_CLAMP);

        state.enable(Feature.TEXTURE_2D);
    }

    protected boolean hasShadow(RenderPass pass) {
        return pass instanceof BaseLight && ((BaseLight) pass).getShadowInfo() != null;
    }
}
