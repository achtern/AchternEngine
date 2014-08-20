package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicShader extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(BasicShader.class);

    private static final BasicShader instance = new BasicShader();

    public static BasicShader getInstance() {
        return instance;
    }

    private BasicShader() {
        try {
            this.program = ResourceLoader.getShaderProgram("basic");
        } catch (Exception e) {
            LOGGER.warn("Error Loading Bundled Basic Shader GLSL files.", e);
        }
    }
}
