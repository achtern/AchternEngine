package io.github.achtern.AchternEngine.core.scenegraph.bounding;

import io.github.achtern.AchternEngine.core.math.Vector3f;

import java.util.List;

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

    public Vector3f getCenter() {
        return center;
    }

    public Vector3f getCenter(Vector3f store) {
        store.set(getCenter());
        return store;
    }

    public void setCenter(Vector3f center) {
        this.center = center;
    }

    public int getCheckPlane() {
        return checkPlane;
    }

    public void setCheckPlane(int checkPlane) {
        this.checkPlane = checkPlane;
    }
}
