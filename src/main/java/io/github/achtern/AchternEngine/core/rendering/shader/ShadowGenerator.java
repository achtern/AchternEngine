package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
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
        try {
            setup(ResourceLoader.getShaderProgram("shadow.basic"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Basic Shadow Shader GLSL files.", e);
        }

    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
    }


}
