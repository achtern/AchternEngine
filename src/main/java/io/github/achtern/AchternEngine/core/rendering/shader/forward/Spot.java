package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.SpotLight;
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
            setup(ResourceLoader.getShaderProgram("forward.spot"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Spot Shader GLSL files.", e);
        }
    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        uniform.setShouldSet(false);
        setUniform("spotLight", (SpotLight) renderEngine.getActiveRenderPass());

        if (uniform.getType().equalsIgnoreCase("SpotLight")) {
            uniform.setValue(SpotLight.class, (SpotLight) renderEngine.getActiveRenderPass());
        }
    }
}
