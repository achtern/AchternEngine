package io.github.achtern.AchternEngine.core.math;

public class Vector2f {

    private float x;
    private float y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float dot(Vector2f v) {
        return x * v.getX() + y * v.getY();
    }

    public float cross(Vector2f v) {
        return x * v.getY() + y * v.getX();
    }

    public Vector2f normalized() {
        float l = length();

        return new Vector2f(x / l, y / l);
    }

    public Vector2f normalize() {
        float l = length();

        x /= l;
        y /= l;

        return this;
    }

    public Vector2f lerp(Vector2f dest, float factor) {
        return dest.sub(this).mul(factor).add(this);
    }

    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    public float max() {
        return Math.max(getX(), getY());
    }

    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.getX(), y + v.getY());
    }

    public Vector2f add(float v) {
        return new Vector2f(x + v, y + v);
    }

    public Vector2f sub(Vector2f v) {
        return new Vector2f(x - v.getX(), y - v.getY());
    }

    public Vector2f sub(float v) {
        return new Vector2f(x - v, y - v);
    }

    public Vector2f mul(Vector2f v) {
        return new Vector2f(x * v.getX(), y * v.getY());
    }

    public Vector2f mul(float v) {
        return new Vector2f(x * v, y * v);
    }

    public Vector2f div(Vector2f v) {
        return new Vector2f(x / v.getX(), y / v.getY());
    }

    public Vector2f div(float v) {
        return new Vector2f(x / v, y / v);
    }

    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    public void set(Vector2f v) {
        setX(v.getX());
        setY(v.getY());
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

    public boolean equals(Vector2f v) {
        return x == v.getX() && y == v.getY();
    }

    public boolean isNullVector() {
        return getX() == 0 && getY() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector2f && equals((Vector2f) obj);
    }

    @Override
    public String toString() {
        return "(" + getX() + "/" + getY() + ")";
    }
}
