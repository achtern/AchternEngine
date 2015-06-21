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

import lombok.AllArgsConstructor;
import lombok.Data;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Vertex;

import java.util.ArrayList;
import java.util.List;

public class TangentGenerator {


    @AllArgsConstructor
    static @Data class TriangleDelta {
        float u1;
        float v1;

        float u2;
        float v2;
    }

    public static void calculate(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i +=3) {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vertex v0 = vertices[i0];
            Vertex v1 = vertices[i1];
            Vertex v2 = vertices[i2];

            Vector3f e1 = v1.getPos().sub(v0.getPos());
            Vector3f e2 = v2.getPos().sub(v0.getPos());

            TriangleDelta delta = getTriangleDelta(v0.getTexCor(), v1.getTexCor(), v2.getTexCor());

            Vector3f tangent = getTangent(delta, e1, e2);

            if (v0.getTangent() == null) {
                v0.setTangent(Vector3f.ZERO.get());
            }
            if (v1.getTangent() == null) {
                v1.setTangent(Vector3f.ZERO.get());
            }
            if (v2.getTangent() == null) {
                v2.setTangent(Vector3f.ZERO.get());
            }

            v0.getTangent().addLocal(tangent);
            v1.getTangent().addLocal(tangent);
            v2.getTangent().addLocal(tangent);
        }

        for (Vertex vertex : vertices) {
            vertex.getTangent().normalize();
        }
    }

    public static List<Vector3f> calculate(List<Vector3f> positions, List<Integer> indices, List<Vector2f> texCoords) {
        List<Vector3f> tangents = new ArrayList<Vector3f>(indices.size());
        for (int i = 0; i <= indices.size(); i++) {
            // fill tangent with empty vector3fs
            tangents.add(i, Vector3f.ZERO.get());
        }

        for (int i = 0; i < indices.size(); i +=3) {
            int i0 = indices.get(i);
            int i1 = indices.get(i + 1);
            int i2 = indices.get(i + 2);

            Vector3f v0 = positions.get(i0);
            Vector3f v1 = positions.get(i1);
            Vector3f v2 = positions.get(i2);

            Vector2f tex0 = texCoords.get(i0);
            Vector2f tex1 = texCoords.get(i1);
            Vector2f tex2 = texCoords.get(i2);

            Vector3f e1 = v1.sub(v0);
            Vector3f e2 = v2.sub(v0);

            TriangleDelta delta = getTriangleDelta(tex0, tex1, tex2);

            Vector3f tangent = getTangent(delta, e1, e2);


            tangents.get(i).addLocal(tangent);
            tangents.get(i + 1).addLocal(tangent);
            tangents.get(i + 2).addLocal(tangent);
        }


        return tangents;
    }

    protected static Vector3f getTangent(TriangleDelta delta, Vector3f e1, Vector3f e2) {
        float f = getFactor(delta);

        return new Vector3f(
                f * (delta.v2 * e1.getX() - delta.v1 * e2.getX()),
                f * (delta.v2 * e1.getY() - delta.v1 * e2.getY()),
                f * (delta.v2 * e1.getZ() - delta.v1 * e2.getZ())
        );
    }

    protected static float getFactor(TriangleDelta delta) {
        return 1.0f / (delta.u1 * delta.v2 - delta.u2 * delta.v1);
    }

    protected static TriangleDelta getTriangleDelta(Vector2f tex0, Vector2f tex1, Vector2f tex2) {
        return new TriangleDelta(
                tex1.getX() - tex0.getX(),
                tex1.getY() - tex0.getY(),
                tex2.getX() - tex0.getX(),
                tex2.getY() - tex0.getY()
        );
    }

}
