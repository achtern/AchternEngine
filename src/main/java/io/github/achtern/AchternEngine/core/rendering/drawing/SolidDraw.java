package io.github.achtern.AchternEngine.core.rendering.drawing;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SolidDraw implements DrawStrategy {

    @Override
    public void draw(MeshData data) {
        glBindVertexArray(data.getVao());

        drawElements(data);
    }

    protected void drawElements(MeshData data) {
        glDrawElements(GL_TRIANGLES, data.getSize(), GL_UNSIGNED_INT, 0);
    }


}
