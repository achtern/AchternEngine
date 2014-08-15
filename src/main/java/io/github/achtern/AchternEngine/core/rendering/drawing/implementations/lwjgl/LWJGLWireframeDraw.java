package io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl;

import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLWireframeDraw extends LWJGLSolidDraw {

    @Override
    public void draw(DataBinder binder, Mesh mesh) {

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        super.draw(binder, mesh);

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    }
}
