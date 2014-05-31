package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.SpotLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Spot extends Shader {
    public static final Logger LOGGER = LoggerFactory.getLogger(Spot.class);

    private static final Spot instance = new Spot();

    public static Spot getInstance() {
        return instance;
    }

    private Spot() {
        super();

        try {
            setUpFromFile("forward.spot");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Spot Shader GLSL files.", e);
        }
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine) {

        super.updateUniforms(transform, material, renderEngine);

        Matrix4f worldMat = transform.getTransformation();
        Matrix4f projectedMat = renderEngine.getMainCamera().getViewProjection().mul(worldMat);


        setUniform("model", worldMat);
        setUniform("MVP", projectedMat);

        setUniform("specularIntensity", material.getFloat("specularIntensity"));
        setUniform("specularPower", material.getFloat("specularPower"));
        setUniform("eyePos", renderEngine.getMainCamera().getTransform().getTransformedPosition());

        setUniform("spotLight", (SpotLight) renderEngine.getActiveRenderPass());

    }
}
