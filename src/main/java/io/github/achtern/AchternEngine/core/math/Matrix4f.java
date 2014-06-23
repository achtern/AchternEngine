package io.github.achtern.AchternEngine.core.math;


public class Matrix4f {

    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

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

        float width = right - left;
        float height = top - bottom;
        float depth = far - near;


        m[0][0] = 2 / width;    m[0][1] = 0;            m[0][2] = 0;            m[0][3] = - (right + left) / width;
        m[1][0] = 0;            m[1][1] = 2 / height;   m[1][2] = 0;            m[1][3] = - (top + bottom) / height;
        m[2][0] = 0;            m[2][1] = 0;            m[2][2] = - 2 / depth;  m[2][3] = - (far + near) / depth;
        m[3][0] = 0;            m[3][1] = 0;            m[3][2] = 0;            m[3][3] = 1;

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

    public float[][] getM() {
        float[][] res = new float[4][4];

        System.arraycopy(m, 0, res, 0, m.length);

        return res;
    }

    public void setM(float[][] m) {
        this.m = m;
    }
}
