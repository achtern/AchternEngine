package io.github.achtern.AchternEngine.core.rendering.drawing;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static org.lwjgl.opengl.GL11.*;

public class WireframeDraw extends SolidDraw {

    @Override
    public void draw(MeshData data) {

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        super.draw(data);

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

    }
}
