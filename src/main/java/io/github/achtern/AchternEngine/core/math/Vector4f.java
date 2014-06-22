package io.github.achtern.AchternEngine.core.math;

public class Vector4f {

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
}
