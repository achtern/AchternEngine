package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.PointLight;
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

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        uniform.setShouldSet(false);
        setUniform("pointLight", (PointLight) renderEngine.getActiveRenderPass());

        if (uniform.getType().equalsIgnoreCase("PointLight")) {
            uniform.setValue(PointLight.class, (PointLight) renderEngine.getActiveRenderPass());
        }
    }
}
