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

/**
 * Implementation of the mathematical Matrix
 * with 4x4 float values
 */
public class Matrix4f {

    /**
     * Main Data Array
     */
    private float[][] m;

    /**
     * Initializes with 0
     */
    public Matrix4f() {
        m = new float[4][4];
    }

    /**
     * Initializes this matrix as identiy Matrix
     * @return this
     */
    public Matrix4f initIdentiy() {

        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = 0;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = 0;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = 0;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1;    m[0][1] = 0;    m[0][2] = 0;    m[0][3] = x;
        m[1][0] = 0;    m[1][1] = 1;    m[1][2] = 0;    m[1][3] = y;
        m[2][0] = 0;    m[2][1] = 0;    m[2][2] = 1;    m[2][3] = z;
        m[3][0] = 0;    m[3][1] = 0;    m[3][2] = 0;    m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {

        Matrix4f mx = new Matrix4f();
        Matrix4f my = new Matrix4f();
        Matrix4f mz = new Matrix4f();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        mz.m[0][0] = (float) Math.cos(z);   mz.m[0][1] = (float) - Math.sin(z); mz.m[0][2] = 0;                     mz.m[0][3] = 0;
        mz.m[1][0] = (float) Math.sin(z);   mz.m[1][1] = (float) + Math.cos(z); mz.m[1][2] = 0;                     mz.m[1][3] = 0;
        mz.m[2][0] = 0;                     mz.m[2][1] = 0;                     mz.m[2][2] = 1;                     mz.m[2][3] = 0;
        mz.m[3][0] = 0;                     mz.m[3][1] = 0;                     mz.m[3][2] = 0;                     mz.m[3][3] = 1;

        mx.m[0][0] = 1;                     mx.m[0][1] = 0;                     mx.m[0][2] = 0;                     mx.m[0][3] = 0;
        mx.m[1][0] = 0;                     mx.m[1][1] = (float) Math.cos(x);   mx.m[1][2] = (float) - Math.sin(x); mx.m[1][3] = 0;
        mx.m[2][0] = 0;                     mx.m[2][1] = (float) Math.sin(x);   mx.m[2][2] = (float) + Math.cos(x); mx.m[2][3] = 0;
        mx.m[3][0] = 0;                     mx.m[3][1] = 0;                     mx.m[3][2] = 0;                     mx.m[3][3] = 1;

        my.m[0][0] = (float) Math.cos(y);   my.m[0][1] = 0;                     my.m[0][2] = (float) - Math.sin(y); my.m[0][3] = 0;
        my.m[1][0] = 0;                     my.m[1][1] = 1;                     my.m[1][2] = 0;                     my.m[1][3] = 0;
        my.m[2][0] = (float) Math.sin(y);   my.m[2][1] = 0;                     my.m[2][2] = (float) + Math.cos(y); my.m[2][3] = 0;
        my.m[3][0] = 0;                     my.m[3][1] = 0;                     my.m[3][2] = 0;                     my.m[3][3] = 1;

        m = mz.mul(my.mul(mx)).getM();

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x; m[1][0] = 0; m[2][0] = 0; m[3][0] = 0;
        m[0][1] = 0; m[1][1] = y; m[2][1] = 0; m[3][1] = 0;
        m[0][2] = 0; m[1][2] = 0; m[2][2] = z; m[3][2] = 0;
        m[0][3] = 0; m[1][3] = 0; m[2][3] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f initPerspective(float fov, float aspect, float zNear, float zFar) {

        float fgv = (float) Math.tan(fov / 2);
        float range = zNear - zFar;

        m[0][0] = 1.0f / (fgv * aspect);  m[0][1] = 0;               m[0][2] = 0;                     m[0][3] = 0;
        m[1][0] = 0;                      m[1][1] = 1.0f / (fgv);    m[1][2] = 0;                     m[1][3] = 0;
        m[2][0] = 0;                      m[2][1] = 0;               m[2][2] = (-zNear - zFar)/range; m[2][3] = 2 * zFar * zNear / range;
        m[3][0] = 0;                      m[3][1] = 0;               m[3][2] = 1;                     m[3][3] = 0;

        return this;
    }

    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {

        float RmL = right - left;
        float TmB = top - bottom;
        float FmN = far - near;
        float RpL = right + left;
        float TpB = top + bottom;
        float FpN = far + near;

        m[0][0] = 2 / RmL;   m[0][1] = 0;         m[0][2] = 0;         m[0][3] = - RpL / RmL;
        m[1][0] = 0;         m[1][1] = 2 / TmB;   m[1][2] = 0;         m[1][3] = - TpB / TmB;
        m[2][0] = 0;         m[2][1] = 0;         m[2][2] = - 1 / FmN; m[2][3] = - near / FmN;
        m[3][0] = 0;         m[3][1] = 0;         m[3][2] = 0;         m[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {

        Vector3f f = forward.normalized();
        Vector3f r = up.normalized().cross(f);
        Vector3f u = f.cross(r);


        return initRotation(f, u, r);
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {


        m[0][0] = right.getX();     m[0][1] = right.getY();     m[0][2] = right.getZ();    m[0][3] = 0;
        m[1][0] = up.getX();        m[1][1] = up.getY();        m[1][2] = up.getZ();       m[1][3] = 0;
        m[2][0] = forward.getX();   m[2][1] = forward.getY();   m[2][2] = forward.getZ();  m[2][3] = 0;
        m[3][0] = 0;                m[3][1] = 0;                m[3][2] = 0;               m[3][3] = 1;

        return this;
    }

    public Vector3f transform(Vector3f v) {

        return new Vector3f(
                m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3],
                m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3],
                m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3]
        );
    }

    public Matrix4f mul(Matrix4f ma) {

        Matrix4f ret = new Matrix4f();

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {
                ret.set(i, j, m[i][0] * ma.get(0, j) +
                              m[i][1] * ma.get(1, j) +
                              m[i][2] * ma.get(2, j) +
                              m[i][3] * ma.get(3, j));
            }
        }


        return ret;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void set(int x, int y, float val) {
        m[x][y] = val;
    }

    public void set1Line(float x1, float x2, float x3, int x4) {
        m[0][0] = x1;
        m[0][1] = x2;
        m[0][2] = x3;
        m[0][3] = x4;
    }

    public void set2Line(float x1, float x2, float x3, float x4) {
        m[1][0] = x1;
        m[1][1] = x2;
        m[1][2] = x3;
        m[1][3] = x4;
    }

    public void set3Line(float x1, float x2, float x3, float x4) {
        m[2][0] = x1;
        m[2][1] = x2;
        m[2][2] = x3;
        m[2][3] = x4;
    }

    public void set4Line(float x1, float x2, float x3, float x4) {
        m[3][0] = x1;
        m[3][1] = x2;
        m[3][2] = x3;
        m[3][3] = x4;
    }

    /**
     * Returns a copy of the array!
     * @return copy
     */
    public float[][] getM() {
        float[][] res = new float[4][4];

        System.arraycopy(m, 0, res, 0, m.length);

        return res;
    }

    /**
     * Returns the array itself
     * @return data array
     */
    public float[][] getMReference() {
        return m;
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "(" +
                "(" + get(0, 0) + "/" + get(0, 1) + "/" + get(0, 2) + "/" + get(0, 3) + ")," +
                "(" + get(1, 0) + "/" + get(1, 1) + "/" + get(1, 2) + "/" + get(1, 3) + ")," +
                "(" + get(2, 0) + "/" + get(2, 1) + "/" + get(2, 2) + "/" + get(2, 3) + ")," +
                "(" + get(3, 0) + "/" + get(3, 1) + "/" + get(3, 2) + "/" + get(3, 3) + ")," +
                ")";
    }
}
