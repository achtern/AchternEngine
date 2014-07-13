package io.github.achtern.AchternEngine.core.rendering.shader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ShadowGenerator extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(ShadowGenerator.class);

    private static final ShadowGenerator instance = new ShadowGenerator();

    public static ShadowGenerator getInstance() {
        return instance;
    }

    private ShadowGenerator() {
        super();

        try {
            setUpFromFile("forward.point");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Point Shader GLSL files.", e);
        }

    }


}
