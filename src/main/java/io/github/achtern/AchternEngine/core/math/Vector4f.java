package io.github.achtern.AchternEngine.core.math;

import io.github.achtern.AchternEngine.core.rendering.Color;

public class Vector4f {

    public static final Vector4f ZERO   = new Vector4f(0, 0, 0, 0);
    public static final Vector4f UNIT_X = new Vector4f(1, 0, 0, 0);
    public static final Vector4f UNIT_Y = new Vector4f(0, 1, 0, 0);
    public static final Vector4f UNIT_Z = new Vector4f(0, 0, 1, 0);
    public static final Vector4f UNIT_W = new Vector4f(0, 0, 0, 1);
    public static final Vector4f ONE    = new Vector4f(1, 1, 1, 1);

    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector4f vec) {
        this(vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    public Vector4f(Vector3f vec, float w) {
        this(vec.getX(), vec.getY(), vec.getZ(), w);
    }

    public void set(float x, float y, float z, float w) {
        setX(x);
        setY(y);
        setZ(z);
        setW(w);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public Vector3f getXYZ() {
        return new Vector3f(getX(), getY(), getZ());
    }

    public boolean isNullVector() {
        return getXYZ().isNullVector() && getW() == 0;
    }

    @Override
    public String toString() {
        return "(" + getX() + "/" + getY() + "/" + getZ() + "/" + getW() + ")";
    }

    public Color toColor() {
        return new Color(this);
    }
}
