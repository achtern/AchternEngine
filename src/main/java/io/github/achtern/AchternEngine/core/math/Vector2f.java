/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

package io.github.achtern.AchternEngine.core.math;

import io.github.achtern.AchternEngine.core.rendering.Dimension;

public class Vector2f {


    public static final Vector2f ZERO   = new Vector2f(0, 0);
    public static final Vector2f UNIT_X = new Vector2f(1, 0);
    public static final Vector2f UNIT_Y = new Vector2f(0, 1);
    public static final Vector2f ONE    = new Vector2f(1, 1);


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

    public void set(float x, float y) {
        setX(x);
        setY(y);
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

    /**
     * Returns a new Dimension instance
     * width from X-value
     * height from Y-value
     * @return dimension from this vector
     */
    public Dimension toDimension() {
        return new Dimension((int) getX(), (int) getY());
    }
}
