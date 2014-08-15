package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
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
            this.program = ResourceLoader.getShaderProgram("forward.ambient");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Ambient Shader GLSL files.", e);
        }
    }
}
