package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.bootstrap.NativeObject;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeshData extends NativeObject {

    public static final Logger LOGGER = LoggerFactory.getLogger(MeshData.class);

    public enum Mode {

        TRIANGLES,
        LINES,
        LINE_LOOP

    }

    protected int vbo;
    protected int ibo;
    protected int size;

    protected Vertex[] vertices;
    protected int[] indices;

    protected Mode mode = Mode.TRIANGLES;


    public MeshData() {
    }

    public void setBufferIDs(int vbo, int ibo) {
        this.vbo = vbo;
        this.ibo = ibo;
    }

    public void set(Vertex[] vertices, int[] indices) {
        set(vertices, indices, indices.length);
    }

    public void set(Vertex[] vertices, int[] indices, int size) {
        if (getID() != -1) {

            // If data is the same, ignore!
            if (getVertices() == vertices && getIndices() == indices && getSize() == size) return;

            // Otherwise reset ID
            setID(INVALID_ID);
        }

        this.setSize(size);
        this.setVertices(vertices);
        this.setIndices(indices);
    }

    public int getVbo() {
        return vbo;
    }

    public int getIbo() {
        return ibo;
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
}
