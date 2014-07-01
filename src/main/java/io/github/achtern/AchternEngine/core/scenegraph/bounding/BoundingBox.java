package io.github.achtern.AchternEngine.core.scenegraph.bounding;

import io.github.achtern.AchternEngine.core.math.Vector3f;

public class BoundingBox extends BoundingObject {

    protected Vector3f extents;


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
        // TODO: implement
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

    public Vector3f getExtents() {
        return extents;
    }

    public void setExtents(Vector3f extents) {
        this.extents = extents;
    }
}
