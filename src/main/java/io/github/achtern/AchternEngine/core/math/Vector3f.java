package io.github.achtern.AchternEngine.core.math;

public class Vector3f {

    private float x;
    private float y;
    private float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f(Vector3f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(Vector3f v) {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    public Vector3f cross(Vector3f v) {
        float _x = y * v.getZ() - z * v.getY();
        float _y = z * v.getX() - x * v.getZ();
        float _z = x * v.getY() - y * v.getX();

        return new Vector3f(_x, _y, _z);
    }

    public void normalize() {
        float l = length();

        x /= l;
        y /= l;
        z /= l;
    }

    public Vector3f normalized() {
        float l = length();

        return new Vector3f(x / l, y / l, z / l);
    }

    public Vector3f lerp(Vector3f dest, float factor) {
        return dest.sub(this).mul(factor).add(this);
    }

    public Vector3f rotate(Vector3f axis, float angle) {
        return this.rotate(new Quaternion().initRotation(axis, angle));
    }

    public Vector3f rotate(Quaternion q) {
        Quaternion conjugate = q.conjugate();
        Quaternion w = q.mul(this).mul(conjugate);
        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    public float max() {
        return Math.max(getX(), Math.max(getY(), getZ()));
    }

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Vector3f add(float v) {
        return new Vector3f(x + v, y + v, z + v);
    }

    public Vector3f sub(Vector3f v) {
        return new Vector3f(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public Vector3f sub(float v) {
        return new Vector3f(x - v, y - v, z - v);
    }

    public Vector3f mul(Vector3f v) {
        return new Vector3f(x * v.getX(), y * v.getY(), z * v.getZ());
    }

    public Vector3f mul(float v) {
        return new Vector3f(x * v, y * v, z * v);
    }

    public Vector3f multLocal(Vector3f v) {
        x *= v.getX();
        y *= v.getY();
        z *= v.getZ();

        return this;
    }

    public Vector3f multLocal(float v) {
        x *= v;
        y *= v;
        z *= v;

        return this;
    }

    public Vector3f div(Vector3f v) {
        return new Vector3f(x / v.getX(), y / v.getY(), z / v.getZ());
    }

    public Vector3f div(float v) {
        return new Vector3f(x / v, y / v, z / v);
    }

    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public Vector2f getXY() {
        return new Vector2f(getX(), getY());
    }

    public Vector2f getYZ() {
        return new Vector2f(getY(), getZ());
    }

    public Vector2f getXZ() {
        return new Vector2f(getX(), getZ());
    }

    public Vector2f getYX() {
        return new Vector2f(getY(), getX());
    }

    public Vector2f getZY() {
        return new Vector2f(getZ(), getY());
    }

    public Vector2f getZX() {
        return new Vector2f(getZ(), getX());
    }

    public Vector3f set(float x, float y, float z) {
        setX(x);
        setY(y);
        setZ(z);

        return this;
    }

    public Vector3f set(Vector3f v) {
        setX(v.getX());
        setY(v.getY());
        setZ(v.getZ());

        return this;
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

    public boolean equals(Vector3f v) {
        return x == v.getX() && y == v.getY() && z == v.getZ();
    }

    public boolean isNullVector() {
        return getXY().isNullVector() && getZ() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        Vector3f v = (Vector3f) obj;

        if (obj == null) return false;

        return equals((Vector3f) obj);
    }

    @Override
    public String toString() {
        return "(" + getX() + "/" + getY() + "/" + getZ() + ")";
    }
}

