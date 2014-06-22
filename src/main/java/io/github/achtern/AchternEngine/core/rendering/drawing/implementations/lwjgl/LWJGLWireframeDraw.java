package io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLWireframeDraw extends LWJGLSolidDraw {

    @Override
    public void draw(MeshData data) {

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        super.draw(data);

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    }
}
