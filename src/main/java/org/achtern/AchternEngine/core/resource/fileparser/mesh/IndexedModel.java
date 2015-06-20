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

package org.achtern.AchternEngine.core.resource.fileparser.mesh;

import lombok.Data;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;

import java.util.ArrayList;

@Data
public class IndexedModel {

    protected ArrayList<Vector3f> positions;
    protected ArrayList<Vector2f> texCoord;
    protected ArrayList<Vector3f> normal;
    protected ArrayList<Vector3f> tangent;
    protected ArrayList<Integer> indices;


    public IndexedModel() {
        positions = new ArrayList<Vector3f>();
        texCoord = new ArrayList<Vector2f>();
        normal = new ArrayList<Vector3f>();
        tangent = new ArrayList<Vector3f>();
        indices = new ArrayList<Integer>();
    }

    public void calcNormals() {

        for (int i = 0; i < getIndices().size(); i += 3) {
            int i0 = getIndices().get(i);
            int i1 = getIndices().get(i + 1);
            int i2 = getIndices().get(i + 2);

            Vector3f v1 = getPositions().get(i1).sub(getPositions().get(i0));
            Vector3f v2 = getPositions().get(i2).sub(getPositions().get(i0));

            Vector3f normal = v1.cross(v2).normalized();

            getNormal().get(i0).set(getNormal().get(i0).add(normal));
            getNormal().get(i1).set(getNormal().get(i1).add(normal));
            getNormal().get(i2).set(getNormal().get(i2).add(normal));
        }

        for (Vector3f normal : getNormal()) {
            normal.normalize();
        }

    }

    public void calcTangents() {
        for (int i = 0; i <= getIndices().size(); i++) {
            // fill tangent with empty vector3fs
            getTangent().add(i, Vector3f.ZERO.get());
        }
        for (int i = 0; i < getIndices().size(); i +=3) {
            int i0 = getIndices().get(i);
            int i1 = getIndices().get(i + 1);
            int i2 = getIndices().get(i + 2);

            Vector3f v0 = getPositions().get(i0);
            Vector3f v1 = getPositions().get(i1);
            Vector3f v2 = getPositions().get(i2);

            Vector2f tex0 = getTexCoord().get(i0);
            Vector2f tex1 = getTexCoord().get(i1);
            Vector2f tex2 = getTexCoord().get(i2);

            Vector3f e1 = v1.sub(v0);
            Vector3f e2 = v2.sub(v0);

            float deltaU1 = tex1.getX() - tex0.getX();
            float deltaV1 = tex1.getY() - tex0.getY();
            float deltaU2 = tex2.getX() - tex0.getX();
            float deltaV2 = tex2.getY() - tex0.getY();

            float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

            Vector3f tangent = new Vector3f(
                    f * (deltaV2 * e1.getX() - deltaV1 * e2.getX()),
                    f * (deltaV2 * e1.getY() - deltaV1 * e2.getY()),
                    f * (deltaV2 * e1.getZ() - deltaV1 * e2.getZ())
            );
//            Vector3f biTangent = new Vector3f(
//                    f * (-deltaU2 * e1.getX() - deltaU2 * e2.getX()),
//                    f * (-deltaU2 * e1.getY() - deltaU2 * e2.getY()),
//                    f * (-deltaU2 * e1.getZ() - deltaU2 * e2.getZ())
//            );


            Vector3f t0 = getTangent().get(i);
            Vector3f t1 = getTangent().get(i + 1);
            Vector3f t2 = getTangent().get(i + 2);

            getTangent().set(i    , t0.add(tangent));
            getTangent().set(i + 1, t1.add(tangent));
            getTangent().set(i + 2, t2.add(tangent));
        }

        for (Vector3f tangent : getTangent()) {
            tangent.normalize();
        }
    }
}
