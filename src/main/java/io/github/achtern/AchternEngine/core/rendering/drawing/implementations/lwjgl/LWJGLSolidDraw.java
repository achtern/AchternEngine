package io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl;

import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl.LWJGLModeConverter.convert;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LWJGLSolidDraw implements DrawStrategy {

    @Override
    public void draw(MeshData data) {
        glBindVertexArray(data.getVao());

        drawElements(data);
    }

    protected void drawElements(MeshData data) {
        glDrawElements(convert(data.getMode()), data.getSize(), GL_UNSIGNED_INT, 0);
    }


}
