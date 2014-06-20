package io.github.achtern.AchternEngine.core.rendering.drawing;

import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SolidDraw implements DrawStrategy {

    @Override
    public void draw(MeshData data) {
        glBindVertexArray(data.getVao());

        // Position
        glEnableVertexAttribArray(0);
        // Texture Coordinates
        glEnableVertexAttribArray(1);
        // Normals
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, data.getVbo());

        // Position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        // Texture Coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        // Normals
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        drawElements(data);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.getIbo());



        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    protected void drawElements(MeshData data) {
        glDrawElements(GL_TRIANGLES, data.getSize(), GL_UNSIGNED_INT, 0);
    }


}
