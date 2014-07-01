package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public class QuickRenderPass extends QuickEntity implements RenderPass {

    private Shader shader;

    @Override
    public void setEngine(CoreEngine engine) {
        if (engine != null && engine != getEngine()) {
            super.setEngine(engine);
            engine.getRenderEngine().addRenderPass(this);
        } else if (engine == null) {
            super.setEngine(null);
        }
    }

    @Override
    public void removed() {
        getEngine().getRenderEngine().removeRenderPass(this);
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

}
