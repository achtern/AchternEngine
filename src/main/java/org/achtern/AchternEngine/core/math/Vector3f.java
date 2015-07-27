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

package org.achtern.AchternEngine.core.math;

import lombok.Getter;

/**
 * A Vector3f is a implementation of the mathematical
 * Vector in R3.
 * It is made up of 3 float variables and has the
 * common vector manipulation methods.
 * All methods return a new instance rather then modifying
 * the object it self. If the modification is desired,
 * the ...Local() method perform the modification. Careful!
 */
public class Vector3f implements Cloneable {


    /**
     * Zero Vector (0/0/0)
     */
    public static final Vector3f ZERO   = new Vector3f(0, 0, 0);
    /**
     * Unit Vector of X (1/0/0)
     */
    public static final Vector3f UNIT_X = new Vector3f(1, 0, 0);
    /**
     * Unit Vector of Y (0/1/0)
     */
    public static final Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    /**
     * Unit Vector of Z (0/0/1)
     */
    public static final Vector3f UNIT_Z = new Vector3f(0, 0, 1);
    /**
     * Unit Vector (1/1/1)
     */
    public static final Vector3f ONE    = new Vector3f(1, 1, 1);

    protected boolean locked = false;

    @Getter private float x;
    @Getter private float y;
    @Getter private float z;

    /**
     * Calculates the euclidean distance.
     * Calls to this static method are equivalent
     * to <code>p.euclidean(q);</code>
     * @param p First Vector
     * @param q Second Vector
     * @return Euclidean distance
     */
    public static float distance(Vector3f p, Vector3f q) {
        return p.euclidean(q);
    }

    /**
     * Linearly interpolates between two Vectors.
     * Calls to this static method are equivalent
     * to <code>p.lerp(q, factor);</code>
     * @param p First Vector
     * @param q Second Vector
     * @param factor Interpolation factor
     * @return interpolated Vector
     */
    public static Vector3f lerp(Vector3f p, Vector3f q, float factor) {
        return p.lerp(q, factor);
    }

    /**
     * Construct a new Vector
     * @param x X-Component
     * @param y Y-Component
     * @param z Z-Component
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Construct a new Vector, where all components are the same
     * @param v X, Y, Z - Component
     */
    public Vector3f(float v) {
        this(v, v, v);
    }

    /**
     * Copy constructor
     * @param v copy
     */
    public Vector3f(Vector3f v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    public Vector3f lock() {
        locked = true;
        return this;
    }

    public Vector3f unlock() {
        locked = false;
        return this;
    }

    public boolean isLocked() {
        return locked;
    }

    /**
     * Calculates the euclidean distance.
     * @param v destination
     * @return distance
     */
    public float euclidean(Vector3f v) {
        return (float) Math.sqrt(
                (getX() - v.getX()) * (getX() - v.getX()) +
                (getY() - v.getY()) * (getY() - v.getY()) +
                (getZ() - v.getZ()) * (getZ() - v.getZ())
        );
    }

    /**
     * Calculates length a.k.a. magnitude
     * @return length
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Dot Product
     * @param v other
     * @return dot
     */
    public float dot(Vector3f v) {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    /**
     * Cross Product
     * @param v other
     * @return cross
     */
    public Vector3f cross(Vector3f v) {
        float _x = y * v.getZ() - z * v.getY();
        float _y = z * v.getX() - x * v.getZ();
        float _z = x * v.getY() - y * v.getX();

        return new Vector3f(_x, _y, _z);
    }

    /**
     * Normalizes this object
     */
    public void normalize() {
        float l = length();

        x /= l;
        y /= l;
        z /= l;
    }

    /**
     * Returns normalized version of this
     * Vector. DOES NOT modify this object.
     * Use {@link Vector3f#normalize()} for this!
     * @return normalized Vector
     */
    public Vector3f normalized() {
        float l = length();

        return new Vector3f(x / l, y / l, z / l);
    }

    /**
     * Linearly interpolates between two Vectors.
     * @param dest Destination Vector
     * @param factor Interpolation factor
     * @return interpolated Vector
     */
    public Vector3f lerp(Vector3f dest, float factor) {
        return dest.sub(this).mul(factor).add(this);
    }

    /**
     * Returns Vector of this rotated around the given axis by angle amount.
     * @param axis Rotation Axis
     * @param angle angle in radians!
     * @return new instance!!
     */
    public Vector3f rotate(Vector3f axis, float angle) {
        return this.rotate(new Quaternion().initRotation(axis, angle));
    }

    /**
     * Returns Vector of this rotated by using thw Quaternion
     * @param q Quaternion to rotate.
     * @return new instance!!
     */
    public Vector3f rotate(Quaternion q) {
        Quaternion conjugate = q.conjugate();
        Quaternion w = q.mul(this).mul(conjugate);
        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    /**
     * Returns the largest component of this Vector
     * @return largest component
     */
    public float max() {
        return Math.max(getX(), Math.max(getY(), getZ()));
    }

    /**
     * Add two Vectors
     * @param v other
     * @return Added Vectors (new instance!!)
     */
    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /**
     * Add constant to Vector
     * @param v constant
     * @return new instance!!
     */
    public Vector3f add(float v) {
        return new Vector3f(x + v, y + v, z + v);
    }

    /**
     * Add Vector to THIS Vector
     * @param v other
     * @return this
     */
    public Vector3f addLocal(Vector3f v) {
        x += v.getX();
        y += v.getY();
        z += v.getZ();

        return this;
    }

    /**
     * Add constant to THIS Vector
     * @param v constant
     * @return this
     */
    public Vector3f addLocal(float v) {
        x += v;
        y += v;
        z += v;

        return this;
    }

    /**
     * Subtract two Vectors
     * @param v other
     * @return Subtracted Vectors (new instance!!)
     */
    public Vector3f sub(Vector3f v) {
        return new Vector3f(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    /**
     * Subtract constant from Vector
     * @param v constant
     * @return new instance!!
     */
    public Vector3f sub(float v) {
        return new Vector3f(x - v, y - v, z - v);
    }

    /**
     * Subtract Vector from THIS Vector
     * @param v other
     * @return this
     */
    public Vector3f subLocal(Vector3f v) {
        x -= v.getX();
        y -= v.getY();
        z -= v.getZ();

        return this;
    }

    /**
     * Subtract constant from THIS Vector
     * @param v constant
     * @return this
     */
    public Vector3f subLocal(float v) {
        x -= v;
        y -= v;
        z -= v;

        return this;
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

    /**
     * Returns the absolute version of this Vector
     * @return new instance!!
     */
    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * Returns clamped version of this vector
     * @see Vector3f#clampLocal(float)
     * @param c constant
     * @return clamped version (new instance!!)
     */
    public Vector3f clamp(float c) {
        Vector3f r = new Vector3f(this);
        r.clampLocal(c);
        return r;
    }

    /**
     * Clamp all components to the constant c.
     * Just checks:
     * <pre>
     * {@code
     * COMPONENT > c
     * }
     * </pre>
     * -4 will stay the same if clamped to 3!
     * @param c constant
     * @return this
     */
    public Vector3f clampLocal(float c) {
        if (getX() > c) setX(c);
        if (getY() > c) setY(c);
        if (getZ() > c) setZ(c);

        return this;
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

    public void setX(float x) {
        if (locked) {
            throw new IllegalAccessError("Vector is locked!");
        }
        this.x = x;
    }

    public void setY(float y) {
        if (locked) {
            throw new IllegalAccessError("Vector is locked!");
        }
        this.y = y;
    }

    public void setZ(float z) {
        if (locked) {
            throw new IllegalAccessError("Vector is locked!");
        }
        this.z = z;
    }

    public boolean equals(Vector3f v) {
        return x == v.getX() && y == v.getY() && z == v.getZ();
    }

    /**
     * Returns true when all 3 components are 0
     * @return nullVector
     */
    public boolean isNullVector() {
        return getXY().isNullVector() && getZ() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector3f && equals((Vector3f) obj);
    }

    @Override
    public String toString() {
        return "(" + getX() + "/" + getY() + "/" + getZ() + ")";
    }

    /**
     * Copy components into new Vector object
     * @return new instance
     */
    public Vector3f get() {
        return new Vector3f(this);
    }

    @Override
    public Vector3f clone() throws CloneNotSupportedException {
        try {
            return (Vector3f) super.clone();
        } catch (CloneNotSupportedException e) {

            return null;
        }
    }
}

