package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.util.UBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MeshData {

    protected int vbo;
    protected int ibo;
    protected int vao;
    protected int size;

    protected Vertex[] vertices;
    protected int[] indices;

    public MeshData() {
        this.vbo = glGenBuffers();
        this.ibo = glGenBuffers();
        this.vao = glGenVertexArrays();
        size = 0;
    }

    public void bind(Vertex[] vertices, int[] indices) {
        bind(vertices, indices, indices.length);
    }

    public void bind(Vertex[] vertices, int[] indices, int size) {
        this.setSize(size);
        this.setVertices(vertices);
        this.setIndices(indices);

        this.bind();
    }


    public void bind() {
        glBindVertexArray(getVao());


        glBindBuffer(GL_ARRAY_BUFFER, getVbo());

        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) UBuffer.create(vertices).flip(), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, getIbo());
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) UBuffer.create(indices).flip(), GL_STATIC_DRAW);

        // Position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        // Texture Coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        // Normals
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        // Position
        glEnableVertexAttribArray(0);
        // Texture Coordinates
        glEnableVertexAttribArray(1);
        // Normals
        glEnableVertexAttribArray(2);


        // Unbind
        glBindVertexArray(0);

    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();

        glDeleteBuffers(getVbo());
        glDeleteBuffers(getIbo());
        glDeleteVertexArrays(getVao());


    }

    public int getVbo() {
        return vbo;
    }

    public int getIbo() {
        return ibo;
    }

    public int getVao() {
        return vao;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int getVertexCount() {
        return getVertices().length;
    }
}
