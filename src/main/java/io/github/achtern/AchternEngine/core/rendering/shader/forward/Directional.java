package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.DirectionalLight;
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
        try {
            setup(ResourceLoader.getShaderProgram("forward.directional"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Directional Shader GLSL files.", e);
        }
    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        uniform.setShouldSet(false);
        setUniform("directionalLight", (DirectionalLight) renderEngine.getActiveRenderPass());

        if (uniform.getType().equalsIgnoreCase("DirectionalLight")) {
            uniform.setValue(DirectionalLight.class, (DirectionalLight) renderEngine.getActiveRenderPass());
        }
    }


}
