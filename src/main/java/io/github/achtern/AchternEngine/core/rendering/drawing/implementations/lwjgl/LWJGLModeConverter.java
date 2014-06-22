package io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static org.lwjgl.opengl.GL11.*;

public class LWJGLModeConverter {


    public static int convert(MeshData.Mode mode) {

        switch (mode) {
            case TRIANGLES:
                return GL_TRIANGLES;
            case LINES:
                return GL_LINES;
            case LINE_LOOP:
                return GL_LINE_LOOP;
            default:
                throw new UnsupportedOperationException("Mode not supported by LWJGL!");
        }
    }

}
