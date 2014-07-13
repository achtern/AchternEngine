package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.WireframeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Wireframe extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Wireframe.class);

    private static final Wireframe instance = new Wireframe();

    public static Wireframe getInstance() {
        return instance;
    }

    private Wireframe() {
        super();

        try {
            setUpFromFile("debug.wireframe", true, true, true);
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Debug Wireframe Shader GLSL files.", e);
        }
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {

        super.updateUniforms(transform, material, renderEngine, projection);


        setUniform("MVP", projection);
        setUniform("WIN_SCALE", Window.get());
        setUniform("wirecolor", ((WireframeDisplay) renderEngine.getActiveRenderPass()).getWireColor());
        setUniform("fillcolor", ((WireframeDisplay) renderEngine.getActiveRenderPass()).getFillColor());
    }
}
