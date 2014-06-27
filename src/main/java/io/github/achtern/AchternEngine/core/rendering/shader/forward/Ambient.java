package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.AmbientLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Ambient extends Shader {
    public static final Logger LOGGER = LoggerFactory.getLogger(Ambient.class);

    private static final Ambient instance = new Ambient();

    public static Ambient getInstance() {
        return instance;
    }

    private Ambient() {
        super();

        try {
            setUpFromFile("forward.ambient");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Ambient Shader GLSL files.", e);
        }
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        super.updateUniforms(transform, material, renderEngine, projection);


        setUniform("MVP", projection);
        setUniform("ambientIntensity", (AmbientLight) renderEngine.getActiveRenderPass());

    }

    protected void setUniform(String name, AmbientLight light) {
        setUniform(name, light.getColor());
    }
}
