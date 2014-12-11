package org.achtern.AchternEngine.core.scenegraph.bounding;

import org.achtern.AchternEngine.core.math.Vector3f;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoundingBoxTest {

    @Test
    public void testFromPoints() throws Exception {

        List<Vector3f> points = new ArrayList<Vector3f>();

        points.add(new Vector3f(1, 1, 0));
        points.add(new Vector3f(1, 5, 2));
        points.add(new Vector3f(3, 7, -3));
        points.add(new Vector3f(2, 8, 4));


        BoundingBox expected = new BoundingBox(new Vector3f(2, 4.5f, 0.5f), new Vector3f(1, 3.5f, 3.5f));

        assertEquals(expected, new BoundingBox().fromPoints(points));

        // -------
        List<Vector3f> singlePoint = new ArrayList<Vector3f>();
        singlePoint.add(new Vector3f(1, 2, 3));

        expected = new BoundingBox(new Vector3f(1, 2, 3), Vector3f.ZERO.get());
        assertEquals(expected, new BoundingBox().fromPoints(singlePoint));

        // -------
        List<Vector3f> points2 = new ArrayList<Vector3f>();

        points2.add(new Vector3f(-2, 1, 1));
        points2.add(new Vector3f(1, 2, 1));
        points2.add(new Vector3f(2, 1, 1));
        points2.add(new Vector3f(2, -1, 1));

        points2.add(new Vector3f(-1, 2, 1));
        points2.add(new Vector3f(-1, -2, 1));
        points2.add(new Vector3f(-2, -1, 1));
        points2.add(new Vector3f(-2, 1, 1));

        expected = new BoundingBox(new Vector3f(0, 0, 1), new Vector3f(2, 2, 0));
        assertEquals(expected, new BoundingBox().fromPoints(points2));

    }

    @Test
    public void testMerge() throws Exception {
        BoundingBox bb1 = new BoundingBox(new Vector3f(0, 0, 0), new Vector3f(1, 2, 1));
        BoundingBox bb2 = new BoundingBox(new Vector3f(0, 0, 0), new Vector3f(2, 1, 1));

        BoundingBox expected = new BoundingBox(new Vector3f(0, 0, 0), new Vector3f(2, 2, 1));

        assertEquals(expected, bb1.merge(bb2));

        // With different center points

        bb1 = new BoundingBox(new Vector3f(3, 3, 1), new Vector3f(2, 2, 1));
        bb2 = new BoundingBox(new Vector3f(-4, 2, 1), new Vector3f(1, 1, 1));

        expected = new BoundingBox(new Vector3f(0, 3, 1), new Vector3f(5, 2, 1));

        assertEquals(expected, bb1.merge(bb2));


    }

    @Test
    public void testToPoints() throws Exception {
        BoundingBox bb = new BoundingBox(Vector3f.ZERO.get(), Vector3f.ONE.get());

        List<Vector3f> points = new ArrayList<Vector3f>(8);

        points.add(new Vector3f(-1, -1, 1));
        points.add(new Vector3f(1, -1, 1));
        points.add(new Vector3f(1, 1, 1));
        points.add(new Vector3f(-1, 1, 1));

        points.add(new Vector3f(-1, -1, -1));
        points.add(new Vector3f(1, -1, -1));
        points.add(new Vector3f(1, 1, -1));
        points.add(new Vector3f(-1, 1, -1));

        assertEquals(points, bb.toPoints());

    }
}