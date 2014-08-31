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

package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class SkyBox extends Mesh {

    private static float X_MOD = 1f / 4;
    private static float Y_MOD = 1f / 3;


    public SkyBox(float size) {

        Vertex[] vertices = {

                // top
                new Vertex(-size, size, -size, X_MOD, 0),
                new Vertex(-size, size, size, X_MOD, Y_MOD),
                new Vertex(size, size, size, X_MOD * 2, Y_MOD),
                new Vertex(size, size, -size, X_MOD * 2, 0),

                // Bottom
                new Vertex(-size, -size, -size, X_MOD, Y_MOD * 2),
                new Vertex(-size, -size, size, X_MOD, Y_MOD * 3),
                new Vertex(size, -size, size, X_MOD * 2, Y_MOD * 3),
                new Vertex(size, -size, -size, X_MOD * 2, Y_MOD * 2),

                // YZ Plane negative X
                new Vertex(-size, -size, -size,  0, Y_MOD * 2),
                new Vertex(-size,  size, -size,  0, Y_MOD),
                new Vertex(-size,  size,  size,  X_MOD, Y_MOD),
                new Vertex(-size, -size,  size,  X_MOD, Y_MOD * 2),

                // YZ Plane positive X
                new Vertex( size, -size, -size,  X_MOD * 3, Y_MOD * 2),
                new Vertex( size,  size, -size,  X_MOD * 3, Y_MOD),
                new Vertex( size,  size,  size,  X_MOD * 2, Y_MOD),
                new Vertex( size, -size,  size,  X_MOD * 2, Y_MOD * 2),

                // XY Plane negative Z
                new Vertex(-size, -size, -size,  X_MOD * 4, Y_MOD * 2),
                new Vertex(-size,  size, -size,  X_MOD * 4, Y_MOD),
                new Vertex( size,  size, -size,  X_MOD * 3, Y_MOD),
                new Vertex( size, -size, -size,  X_MOD * 3, Y_MOD * 2),

                // XY Plane positive Z
                new Vertex(-size, -size,  size,  X_MOD, Y_MOD * 2),
                new Vertex(-size,  size,  size,  X_MOD, Y_MOD),
                new Vertex( size,  size,  size,  X_MOD * 2, Y_MOD),
                new Vertex( size, -size,  size,  X_MOD * 2, Y_MOD * 2),


        };


        int[] indices = {
                2, 1, 0,
                3, 2, 0,
                4, 5, 6,
                4, 6, 7,
                8, 9, 10,
                8, 10, 11,
                14, 13, 12,
                15, 14, 12,
                18, 17, 16,
                19, 18, 16,
                20, 21, 22,
                20, 22, 23,
        };

        setVertices(vertices, indices, true);

    }

    public static float getX_MOD() {
        return X_MOD;
    }

    public static void setX_MOD(float x_MOD) {
        X_MOD = x_MOD;
    }

    public static float getY_MOD() {
        return Y_MOD;
    }

    public static void setY_MOD(float y_MOD) {
        Y_MOD = y_MOD;
    }
}
