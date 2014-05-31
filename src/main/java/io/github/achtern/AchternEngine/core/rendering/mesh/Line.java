package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Line extends Mesh {

    public Line(Vertex[] vertices, int[] indices) {
        super(vertices, indices);
    }

    @Override
    public void draw(DrawStrategy drawStrategy) {

        glLineWidth(1000);

        glBindVertexArray(this.getData().getVao());

        // Position
        glEnableVertexAttribArray(0);
        // Texture Coordinates
        glEnableVertexAttribArray(1);
        // Normals
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, this.getData().getVbo());

        // Position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        // Texture Coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        // Normals
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.getData().getIbo());
        glDrawElements(GL_LINES, this.getData().getSize(), GL_UNSIGNED_INT, 0);
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);


        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

    }
}
