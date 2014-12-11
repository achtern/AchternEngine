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

import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Vertex;
import org.achtern.AchternEngine.core.util.UInteger;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic line based Grid
 */
public class Grid extends Mesh {

    /**
     * Generates the Grid Mesh
     * @param xCount Lines on X
     * @param yCount Lines on Y
     */
    public Grid(int xCount, int yCount) {
        this(xCount, yCount, 1);
    }

    /**
     * Generates the Grid Mesh
     * @param xCount Lines on X
     * @param yCount Lines on Y
     * @param lineSeparation Distance between lines
     */
    public Grid(int xCount, int yCount, float lineSeparation){
        generate(xCount, yCount, lineSeparation);
    }

    /**
     * Generates the Grid Mesh
     * @param xCount Lines on X
     * @param yCount Lines on Y
     * @param lineSeparation Distance between lines
     */
    public void generate(int xCount, int yCount, float lineSeparation) {

        int lineCount = xCount + yCount;

        List<Vector3f> positions = new ArrayList<Vector3f>(lineCount * 2);
        List<Integer> integers = new ArrayList<Integer>(lineCount);

        Vector2f length = new Vector2f(yCount - 1, xCount - 1).mul(lineSeparation);

        for (int i = 0; i < xCount; i++){
            float y = (i) * lineSeparation;

            final Vector3f v1 = new Vector3f(0, 0, y);
            final Vector3f v2 = new Vector3f(length.getX(), 0, y);

            if (!positions.contains(v1)) {
                positions.add(v1);
            }

            if (!positions.contains(v2)) {
                positions.add(v2);
            }

            // Add an index for each ne vertex.
            integers.add(positions.indexOf(v1));
            integers.add(positions.indexOf(v2));

        }

        // add lines along Y
        for (int i = 0; i < yCount; i++){
            float x = (i) * lineSeparation;

            final Vector3f v1 = new Vector3f(x, 0, 0);
            final Vector3f v2 = new Vector3f(x, 0, length.getY());

            if (!positions.contains(v1)) {
                positions.add(v1);
            }

            if (!positions.contains(v2)) {
                positions.add(v2);
            }

            // Add an index for each ne vertex.
            integers.add(positions.indexOf(v1));
            integers.add(positions.indexOf(v2));
        }

        Vertex[] vertices = new Vertex[positions.size()];
        Integer[] indices = new Integer[integers.size()];

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(positions.get(i));
        }

        setVertices(vertices, UInteger.toIntArray(integers.toArray(indices)), false);
        setMode(MeshData.Mode.LINES);
    }
}
