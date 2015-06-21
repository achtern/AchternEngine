/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Christian Gärtner
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

package org.achtern.AchternEngine.core.util;

import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Vertex;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TangentGeneratorTest {


    @Test
    public void testCalculateVertex() {
        Vertex[] data = new Vertex[3];
        int[] indices = new int[] {
                0, 1, 2
        };

        data[0] = new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0), new Vector3f(0, 0, 1));
        data[1] = new Vertex(new Vector3f(1, 0, 0), new Vector2f(1, 0), new Vector3f(0, 0, 1));
        data[2] = new Vertex(new Vector3f(0, 1, 0), new Vector2f(0, 1), new Vector3f(0, 0, 1));

        TangentGenerator.calculate(data, indices);


        assertNotNull("Should populate tangent vectors in each vertex [0]", data[0].getTangent());
        assertNotNull("Should populate tangent vectors in each vertex [1]", data[1].getTangent());
        assertNotNull("Should populate tangent vectors in each vertex [2]", data[2].getTangent());


        assertEquals("Should generate correct tangents [0]", new Vector3f(1, 0, 0), data[0].getTangent());
        assertEquals("Should generate correct tangents [1]", new Vector3f(1, 0, 0), data[1].getTangent());
        assertEquals("Should generate correct tangents [2]", new Vector3f(1, 0, 0), data[2].getTangent());

    }

    @Test
    public void testCalculateLists() {
        List<Vector3f> pos = new ArrayList<Vector3f>(3);
        pos.add(new Vector3f(0, 0, 0));
        pos.add(new Vector3f(1, 0, 0));
        pos.add(new Vector3f(0, 1, 0));

        List<Integer> integers = new ArrayList<Integer>(3);
        integers.add(0);
        integers.add(1);
        integers.add(2);

        List<Vector2f> tex = new ArrayList<Vector2f>(3);
        tex.add(new Vector2f(0, 0));
        tex.add(new Vector2f(1, 0));
        tex.add(new Vector2f(0, 1));

        List<Vector3f> tangents = TangentGenerator.calculate(pos, integers, tex);


        assertNotNull("Should return a list of vector3fs", tangents);
        assertEquals("Should calculate the correct amount of tangents", 3, tangents.size());

        assertEquals("Should generate correct tangents [0]", new Vector3f(1, 0, 0), tangents.get(0));
        assertEquals("Should generate correct tangents [1]", new Vector3f(1, 0, 0), tangents.get(1));
        assertEquals("Should generate correct tangents [2]", new Vector3f(1, 0, 0), tangents.get(2));

    }


}
