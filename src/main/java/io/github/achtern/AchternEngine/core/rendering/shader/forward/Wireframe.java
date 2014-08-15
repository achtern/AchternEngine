package io.github.achtern.AchternEngine.core.rendering.shader.forward;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.WireframeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Wireframe Shader. Adds a wireframe overlay to all objects
 */
public class Wireframe extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Wireframe.class);

    private static final Wireframe instance = new Wireframe();

    public static Wireframe getInstance() {
        return instance;
    }

    private Wireframe() {
        try {
            this.program = ResourceLoader.getShaderProgram("debug.wireframe");
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Debug Wireframe Shader GLSL files.", e);
        }
    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        if (uniform.getName().equalsIgnoreCase("WIN_SCALE")) {
            uniform.setValue(Window.get());
        }

        if (uniform.getName().equalsIgnoreCase("wirecolor")) {
            uniform.setValue(((WireframeDisplay) renderEngine.getActiveRenderPass()).getWireColor());
        }

        if (uniform.getName().equalsIgnoreCase("fillcolor")) {
            uniform.setValue(((WireframeDisplay) renderEngine.getActiveRenderPass()).getFillColor());
        }
    }
}
