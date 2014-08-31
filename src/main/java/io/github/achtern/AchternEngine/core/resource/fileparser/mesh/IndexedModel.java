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

package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;

import java.util.ArrayList;

public class IndexedModel {

    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> texCoord;
    private ArrayList<Vector3f> normal;
    private ArrayList<Integer> indices;


    public IndexedModel() {
        positions = new ArrayList<Vector3f>();
        texCoord = new ArrayList<Vector2f>();
        normal = new ArrayList<Vector3f>();
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

    public ArrayList<Vector3f> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector3f> positions) {
        this.positions = positions;
    }

    public ArrayList<Vector2f> getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(ArrayList<Vector2f> texCoord) {
        this.texCoord = texCoord;
    }

    public ArrayList<Vector3f> getNormal() {
        return normal;
    }

    public void setNormal(ArrayList<Vector3f> normal) {
        this.normal = normal;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }
}
