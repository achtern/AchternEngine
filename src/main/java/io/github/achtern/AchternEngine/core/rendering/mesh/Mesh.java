package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.scenegraph.bounding.BoundingBox;

public class Mesh {

    protected MeshData data;

    protected BoundingBox bb;

    public Mesh(MeshData data) {
        this.data = data;
    }

    public Mesh(Vertex[] vertices, int[] indices) {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals) {
        this();
        setVertices(vertices, indices, calcNormals);
    }

    public Mesh() {
        this.data = new MeshData();
    }

    protected void setVertices(Vertex[] vertices, int[] indices) {
        setVertices(vertices, indices, true);
    }

    protected void setVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {

        if (calcNormals) {
            calcNormals(vertices, indices);
        }

        this.data.bind(vertices, indices);
        updateBounds();
    }

    public void setMode(MeshData.Mode mode) {
        getData().setMode(mode);
    }

    public void draw(DrawStrategy drawStrategy) {
        drawStrategy.draw(this.data);
    }

    public void updateBounds() {
        this.bb = new BoundingBox().fromVertices(getData().getVertices());
    }

    protected void calcNormals(Vertex[] vertices, int[] indices) {

        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalized();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (Vertex vertex : vertices) {
            vertex.getNormal().normalize();
        }

    }

    public MeshData getData() {
        return data;
    }

    protected void setData(MeshData data) {
        this.data = data;
        updateBounds();
    }

    public BoundingBox getBoundingBox() {
        return bb;
    }

    public int getVertexCount() {
        return getData().getVertexCount();
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.bb = boundingBox;
    }
}
