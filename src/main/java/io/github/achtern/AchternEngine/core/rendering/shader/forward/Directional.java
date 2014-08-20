package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Directional extends Shader {
    public static final Logger LOGGER = LoggerFactory.getLogger(Directional.class);

    private static final Directional instance = new Directional();

    public static Directional getInstance() {
        return instance;
    }

    private Directional() {
        try {
            this.program = ResourceLoader.getShaderProgram("forward.directional");
        } catch (Exception e) {
            LOGGER.warn("Error Loading Bundled Directional Shader GLSL files.", e);
        }
    }
}
