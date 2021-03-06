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

package org.achtern.AchternEngine.core.rendering;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;

@AllArgsConstructor
@Data
public class Vertex {

    public static final int SIZE = 11;

    protected Vector3f pos;
    protected Vector2f texCor;
    protected Vector3f normal;
    protected Vector3f tangent;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCor, Vector3f normal) {
        this(pos, texCor, normal, null);
    }

    public Vertex(Vector3f pos, Vector2f texCor) {
        this(pos, texCor, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
    }

    public Vertex(float x, float y, float z, float texX, float texY) {
        this(new Vector3f(x, y, z), new Vector2f(texX, texY));
    }
}
