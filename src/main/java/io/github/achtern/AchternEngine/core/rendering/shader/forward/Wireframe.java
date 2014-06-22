package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.entity.renderpasses.WireframeDisplay;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
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
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine) {

        super.updateUniforms(transform, material, renderEngine);

        Matrix4f worldMat = transform.getTransformation();
        Matrix4f projectedMat = renderEngine.getMainCamera().getViewProjection().mul(worldMat);


        setUniform("MVP", projectedMat);
//        setUniform("color", new Vector3f(1, 0, 0));
        setUniform("WIN_SCALE", new Vector2f(Window.getHeight(), Window.getWidth()));
        setUniform("wirecolor", ((WireframeDisplay) renderEngine.getActiveRenderPass()).getWireColor());
        setUniform("fillcolor", ((WireframeDisplay) renderEngine.getActiveRenderPass()).getFillColor());
    }
}
