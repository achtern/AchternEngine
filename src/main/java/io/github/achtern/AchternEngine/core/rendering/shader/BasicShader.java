package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BasicShader extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(BasicShader.class);

    private static final BasicShader instance = new BasicShader();

    public static BasicShader getInstance() {
        return instance;
    }

    private BasicShader() {
        super();

        try {
            addVertexShader(ResourceLoader.getShader("basicVertex.gvs"));
            addFragmentShader(ResourceLoader.getShader("basicFragment.gfs"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Basic Shader GLSL files.", e);
        }
        compile();

        addUniform("transform");
        addUniform("color");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine) {
        super.updateUniforms(transform, material, renderEngine);

        Matrix4f worldMat = transform.getTransformation();
        Matrix4f projectedMat = renderEngine.getMainCamera().getViewProjection().mul(worldMat);


        setUniform("transform", projectedMat);
        setUniform("color", material.getFloat("color"));

    }

}
