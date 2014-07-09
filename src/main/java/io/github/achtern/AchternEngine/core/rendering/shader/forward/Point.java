package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.PointLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Point extends Shader {
    public static final Logger LOGGER = LoggerFactory.getLogger(Point.class);

    private static final Point instance = new Point();

    public static Point getInstance() {
        return instance;
    }

    private Point() {
        super();

        try {
            setUpFromFile("forward.point");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Point Shader GLSL files.", e);
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

        setUniform("pointLight", (PointLight) renderEngine.getActiveRenderPass());

    }
}
