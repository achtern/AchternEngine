package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final Logger LOGGER = LoggerFactory.getLogger(MeshData.class);

    public enum Mode {

        TRIANGLES,
        LINES,
        LINE_LOOP

    }

    protected int vbo;
    protected int ibo;
    protected int vao;
    protected int size;

    protected Vertex[] vertices;
    protected int[] indices;

    protected Mode mode = Mode.TRIANGLES;

    protected boolean bound;

    public MeshData() {
        generate();
    }

    public void setIDs(int vbo, int ibo, int vao, int size) {
        this.vbo = vbo;
        this.ibo = ibo;
        this.vao = vao;
        this.size = size;
        this.bound = false;
    }

    protected void generate() {
        setIDs(glGenBuffers(), glGenBuffers(), glGenVertexArrays(), 0);
    }

    public void bind(Vertex[] vertices, int[] indices) {
        bind(vertices, indices, indices.length);
    }

    public void bind(Vertex[] vertices, int[] indices, int size) {
        if (isBound()) {

            // If data is the same, ignore!
            if (getVertices() == vertices && getIndices() == indices && getSize() == size) return;


            LOGGER.warn("Rebinding a MeshData Class with new data. You should a new instance instead. " +
                    "Goind to bind mesh anyway master and generating new VBOs, IBOs and new VAO");
            generate();
        }

        this.setSize(size);
        this.setVertices(vertices);
        this.setIndices(indices);
        this.bind();
    }


    public void bind() {

        if (isBound()) {
            throw new IllegalStateException("MeshData already bound to VAO " + getVao());
        }

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

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public boolean isBound() {
        return bound;
    }
}
