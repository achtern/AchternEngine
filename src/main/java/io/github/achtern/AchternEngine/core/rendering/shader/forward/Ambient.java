package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;
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
        try {
            setup(ResourceLoader.getShaderProgram("forward.ambient"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Ambient Shader GLSL files.", e);
        }
    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        if (uniform.getName().equalsIgnoreCase("ambientIntensity")) {
            uniform.setValue(Vector3f.class, ((AmbientLight) renderEngine.getActiveRenderPass()).getColor());
        }
    }
}
