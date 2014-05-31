package io.github.achtern.AchternEngine.core.entity.renderpasses;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.rendering.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public class QuickRenderPass extends QuickEntity implements RenderPass {

    private Shader shader;

    @Override
    public void setEngine(CoreEngine engine) {
        super.setEngine(engine);
        if (engine != null) {
            engine.getRenderEngine().addRenderPass(this);
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
