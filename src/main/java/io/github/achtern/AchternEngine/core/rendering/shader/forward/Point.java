package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
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
            setup(ResourceLoader.getShaderProgram("forward.point"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Point Shader GLSL files.", e);
        }

    }
}
