/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.achtern.AchternEngine.core.rendering.mesh;

import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Vertex;
import org.achtern.AchternEngine.core.scenegraph.bounding.BoundingBox;
import lombok.Getter;

public class Mesh {

    @Getter protected MeshData data;

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

        this.data.set(vertices, indices);
        updateBounds();
    }

    public void setMode(MeshData.Mode mode) {
        getData().setMode(mode);
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

    protected void calcTangents(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i +=3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vertex v0 = vertices[i0];
            Vertex v1 = vertices[i1];
            Vertex v2 = vertices[i2];

            Vector3f e1 = v1.getPos().sub(v0.getPos());
            Vector3f e2 = v2.getPos().sub(v0.getPos());

            float deltaU1 = v1.getTexCor().getX() - v0.getTexCor().getX();
            float deltaV1 = v1.getTexCor().getY() - v0.getTexCor().getY();
            float deltaU2 = v2.getTexCor().getX() - v0.getTexCor().getX();
            float deltaV2 = v2.getTexCor().getY() - v0.getTexCor().getY();

            float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

            Vector3f tangent = new Vector3f(
                    f * (deltaV2 * e1.getX() - deltaV1 * e2.getX()),
                    f * (deltaV2 * e1.getY() - deltaV1 * e2.getY()),
                    f * (deltaV2 * e1.getZ() - deltaV1 * e2.getZ())
            );

            v0.getTangent().addLocal(tangent);
            v1.getTangent().addLocal(tangent);
            v2.getTangent().addLocal(tangent);
        }

        for (Vertex vertex : vertices) {
            vertex.getTangent().normalize();
        }
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
