package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.rendering.Vertex;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
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
