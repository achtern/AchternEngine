package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.DirectionalLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Directional extends Shader {
    public static final Logger LOGGER = LoggerFactory.getLogger(Directional.class);

    private static final Directional instance = new Directional();

    public static Directional getInstance() {
        return instance;
    }

    private Directional() {
        super();

        try {
            setUpFromFile("forward.directional");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Directional Shader GLSL files.", e);
        }
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        super.updateUniforms(transform, material, renderEngine, projection);

        Matrix4f worldMat = transform.getTransformation();


        setUniform("model", worldMat);
        setUniform("MVP", projection);

        setUniform("specularIntensity", material.getFloat("specularIntensity"));
        setUniform("specularPower", material.getFloat("specularPower"));
        setUniform("eyePos", renderEngine.getMainCamera().getTransform().getTransformedPosition());

        setUniform("directionalLight", (DirectionalLight) renderEngine.getActiveRenderPass());

    }
}
