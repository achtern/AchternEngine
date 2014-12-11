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

public class SimpleArrow extends Mesh {

    protected static final Vector3f[] positions = new Vector3f[] {
            /*0*/new Vector3f(0, 0, 0), // BOTTOM
            /*1*/new Vector3f(0, 1, 0), // TOP
            /*2*/new Vector3f(0.5f, 0.5f, 0), // X-side-down
            /*3*/new Vector3f(-0.5f, 0.5f, 0), // neg X-side-down
            /*4*/new Vector3f(0, 0.5f, 0.5f), // Z-side-down
            /*5*/new Vector3f(0, 0.5f, -0.5f) // neg Z-side-down
    };

    public SimpleArrow(int length) {

        Vertex[] vertices = new Vertex[positions.length];
        for (int i = 0; i < positions.length; i++) {
            vertices[i] = new Vertex(positions[i].mul(length));
        }

        setVertices(vertices, new int[] {
                0, 1, 2, 1, 3, 1, 4, 1, 5, 1
        }, false);

        setMode(MeshData.Mode.LINES);


    }

}
