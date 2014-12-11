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

package org.achtern.AchternEngine.core.scenegraph.bounding;

import org.achtern.AchternEngine.core.math.Vector3f;
import lombok.Data;

import java.util.List;

@Data
public abstract class BoundingObject implements Cloneable {

    protected Vector3f center;
    protected int checkPlane = 0;

    public BoundingObject() {
    }

    public BoundingObject(Vector3f center) {
        this.center = center;
    }

    public abstract BoundingObject fromPoints(List<Vector3f> points);

    public abstract BoundingObject merge(BoundingObject other);

    public abstract boolean intersects(BoundingObject bo);

    public abstract boolean contains(Vector3f point);

    @Override
    public BoundingObject clone() throws CloneNotSupportedException {

        BoundingObject clone = (BoundingObject) super.clone();

        clone.setCenter(center.clone());

        return clone;
    }

    public final float distanceTo(Vector3f point) {
        return getCenter().euclidean(point);
    }

    public Vector3f getCenter(Vector3f store) {
        store.set(getCenter());
        return store;
    }
}
