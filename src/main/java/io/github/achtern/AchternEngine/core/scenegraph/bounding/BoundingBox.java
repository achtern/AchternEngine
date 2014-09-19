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

package io.github.achtern.AchternEngine.core.scenegraph.bounding;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.mesh.WireBox;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
public class BoundingBox extends BoundingObject {


    public static Node makeNode(BoundingBox bb) {
        WireBox wireBox = new WireBox(bb.getExtents());
        Figure figure = new Figure("BoundingBox Figure", wireBox);

        Node node = new Node("BoundingBox").add(figure);

        node.getTransform().setPosition(bb.getCenter().get());

        return node;
    }


    @Getter @Setter protected Vector3f extents;

    public BoundingBox(Vector3f center, Vector3f extents) {
        super(center);
        this.extents = extents;
    }

    public BoundingBox(Vector3f center, float x, float y, float z) {
        this(center, new Vector3f(x, y, z));
    }

    public BoundingBox(BoundingBox copy) {
        this(copy.getCenter(), copy.getExtents());
    }

    public BoundingBox copy(BoundingBox copy) {
        setExtents(copy.getExtents());
        setCenter(copy.getCenter());
        setCheckPlane(copy.getCheckPlane());
        return this;
    }

    /**
     * Calculates a BoundingBox from Vertex Array. Only takes positions into account.
     * The outter most vertices will be directly on the edge of the bounding box.
     * @param vertices Vertex Array. Positions are required only
     * @return this
     */
    public BoundingBox fromVertices(Vertex[] vertices) {
        return fromVertices(vertices, 0);
    }

    /**
     * Calculates a BoundingBox from Vertex Array. Only takes positions into account.
     * The outter most vertices will be directly on the edge of the bounding box.
     * After this the padding will get added to all sides.
     * @param vertices Vertex Array. Positions are required only
     * @param padding Will get added to all sides (extends, after the calculation) should be 0 in most cases
     * @return this
     */
    public BoundingBox fromVertices(Vertex[] vertices, float padding) {
        List<Vector3f> points = new ArrayList<Vector3f>(vertices.length);
        for (Vertex v : vertices) {
            points.add(v.getPos());
        }
        return fromPoints(points);
    }

    /**
     * Calculates a BoundingBox from list of points (Vector3f s).
     * The outter most points will be directly on the edge of the bounding box.
     * @param vectors List of vectors to be contained by this boundingbox
     * @return this
     */
    @Override
    public BoundingBox fromPoints(List<Vector3f> vectors) {
        return fromPoints(vectors, 0);
    }

    /**
     * Calculates a BoundingBox from list of points (Vector3f s).
     * The outter most points will be directly on the edge of the bounding box. (not if padding is added!)
     * @param vectors List of vectors to be contained by this boundingbox
     * @param padding Will get added to all sides (extends, after the calculation) should be 0 in most cases
     * @return this
     */
    public BoundingBox fromPoints(List<Vector3f> vectors, float padding) {
        if (vectors.size() == 0) {
            throw new IllegalArgumentException("At least 1 vertex is needed to calculate a BoundingBox");
        }

        List<Float> xs = new ArrayList<Float>(vectors.size());
        List<Float> ys = new ArrayList<Float>(vectors.size());
        List<Float> zs = new ArrayList<Float>(vectors.size());

        for (Vector3f point : vectors) {
            xs.add(point.getX());
            ys.add(point.getY());
            zs.add(point.getZ());
        }

        float maxX = Collections.max(xs);
        float minX = Collections.min(xs);

        float maxY = Collections.max(ys);
        float minY = Collections.min(ys);

        float maxZ = Collections.max(zs);
        float minZ = Collections.min(zs);

        setCenter(new Vector3f(
                (maxX + minX) / 2,
                (maxY + minY) / 2,
                (maxZ + minZ) / 2
        ));

        /* Calculate extents
        Since the distance from both positive max and negative max (p.. & n..)
        is the same, just calculate distance from center to pX, pY and pZ
        */
        setExtents(new Vector3f(
                maxX - getCenter().getX() + padding,
                maxY - getCenter().getY() + padding,
                maxZ - getCenter().getZ() + padding
        ));


        return this;
    }

    @Override
    public BoundingObject merge(BoundingObject other) {
        if (other == null) return this;

        if (other instanceof BoundingBox) {
            return merge((BoundingBox) other);
        } else {
            throw new UnsupportedOperationException("Cannot merge with BoundingObject other then BoundingBox");
        }
    }

    public BoundingBox merge(BoundingBox bb) {


        // To points
        List<Vector3f> bb1 = toPoints();
        List<Vector3f> bb2 = bb.toPoints();

        // Convert from local to "absolute" coordinates
        for (Vector3f v : bb1) {
            v.set(v.add(getCenter()));
        }

        for (Vector3f v : bb2) {
            v.set(v.add(bb.getCenter()));
        }

        bb1.addAll(bb2);

        // Apply to current Object
        fromPoints(bb1);

        return this;
    }

    @Override
    public boolean intersects(BoundingObject bo) {

        if (bo instanceof BoundingBox) {
            return intersects((BoundingBox) bo);
        } else {
            throw new UnsupportedOperationException("Cannot merge with BoundingObject other then BoundingBox");
        }
    }

    public boolean intersects(BoundingBox bb) {


        if (getCenter().getX() - getExtents().getX() > bb.getCenter().getX() + bb.getExtents().getX()) {
            return false;
        }

        if (getCenter().getX() + getExtents().getX() < bb.getCenter().getX() - bb.getExtents().getX()) {
            return false;
        }

        if (getCenter().getY() - getExtents().getY() > bb.getCenter().getY() + bb.getExtents().getY()) {
            return false;
        }

        if (getCenter().getY() + getExtents().getY() < bb.getCenter().getY() - bb.getExtents().getY()) {
            return false;
        }

        if (getCenter().getZ() - getExtents().getZ() > bb.getCenter().getZ() + bb.getExtents().getZ()) {
            return false;
        }

        if (getCenter().getZ() + getExtents().getZ() < bb.getCenter().getZ() - bb.getExtents().getZ()) {
            return false;
        }


        return true;
    }

    @Override
    public boolean contains(Vector3f point) {
        return
                Math.abs(getCenter().getX() - point.getX()) < getExtents().getX()
                &&
                Math.abs(getCenter().getY() - point.getY()) < getExtents().getY()
                &&
                Math.abs(getCenter().getZ() - point.getZ()) < getExtents().getZ()
        ;
    }

    public Node makeNode() {
        return makeNode(this);
    }

    public Vertex[] toVertexArray() {
        return new Vertex[] {
                new Vertex(getExtents().mul(new Vector3f(-1, -1, 1))),
                new Vertex(getExtents().mul(new Vector3f(1, -1, 1))),
                new Vertex(getExtents().mul(new Vector3f(1, 1, 1))),
                new Vertex(getExtents().mul(new Vector3f(-1, 1, 1))),

                new Vertex(getExtents().mul(new Vector3f(-1, -1, -1))),
                new Vertex(getExtents().mul(new Vector3f(1, -1, -1))),
                new Vertex(getExtents().mul(new Vector3f(1, 1, -1))),
                new Vertex(getExtents().mul(new Vector3f(-1, 1, -1))),
        };
    }

    public List<Vector3f> toPoints() {
        List<Vector3f> points = new ArrayList<Vector3f>(8);

        points.add(getExtents().mul(new Vector3f(-1, -1, 1)));
        points.add(getExtents().mul(new Vector3f(1, -1, 1)));
        points.add(getExtents().mul(new Vector3f(1, 1, 1)));
        points.add(getExtents().mul(new Vector3f(-1, 1, 1)));

        points.add(getExtents().mul(new Vector3f(-1, -1, -1)));
        points.add(getExtents().mul(new Vector3f(1, -1, -1)));
        points.add(getExtents().mul(new Vector3f(1, 1, -1)));
        points.add(getExtents().mul(new Vector3f(-1, 1, -1)));

        return points;
    }

    @Override
    public String toString() {
        return "center=" + getCenter() + ";extents=" + getExtents();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BoundingBox)) return false;
        BoundingBox bb = (BoundingBox) obj;

        return
                bb.getExtents().equals(getExtents())
             && bb.getCenter().equals(getCenter())
             && bb.getCheckPlane() == getCheckPlane()
        ;

    }
}
