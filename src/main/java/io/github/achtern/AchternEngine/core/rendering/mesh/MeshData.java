/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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
