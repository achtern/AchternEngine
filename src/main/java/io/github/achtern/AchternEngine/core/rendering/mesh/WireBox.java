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

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class WireBox extends Mesh {


    public WireBox() {
        this(Vector3f.ONE.get());
    }

    public WireBox(Vector3f extents) {

        setMode(MeshData.Mode.LINES);

        Vertex[] vertices = new Vertex[] {
                new Vertex(extents.mul(new Vector3f(-1, -1, 1))),
                new Vertex(extents.mul(new Vector3f(1, -1, 1))),
                new Vertex(extents.mul(new Vector3f(1, 1, 1))),
                new Vertex(extents.mul(new Vector3f(-1, 1, 1))),

                new Vertex(extents.mul(new Vector3f(-1, -1, -1))),
                new Vertex(extents.mul(new Vector3f(1, -1, -1))),
                new Vertex(extents.mul(new Vector3f(1, 1, -1))),
                new Vertex(extents.mul(new Vector3f(-1, 1, -1))),
        };

        int[] indices = new int[] {
                0, 1, 1, 2,
                2, 3, 3, 0,

                4, 5, 5, 6,
                6, 7, 7, 4,

                0, 4,  1, 5,
                2, 6,  3, 7,
        };

        setVertices(vertices, indices, false);

    }
}
