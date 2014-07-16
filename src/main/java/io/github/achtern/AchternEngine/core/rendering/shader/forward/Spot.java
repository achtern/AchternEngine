package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
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
}
