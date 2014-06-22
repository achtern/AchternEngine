package io.github.achtern.AchternEngine.core.math;

public class Quaternion {

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Quaternion q) {
        this(q.getX(), q.getY(), q.getZ(), q.getW());
    }

    public Quaternion() {
        this(0, 0, 0, 1);
    }

    public Quaternion(Vector3f axis, float angle) {
        this();
        this.initRotation(axis, angle);
    }

    public Quaternion (Matrix4f rotationMatrix) {
        fromRotationMatrix(rotationMatrix);
    }

    public Quaternion fromRotationMatrix(Matrix4f rotationMatrix) {
        float trace = rotationMatrix.get(0, 0) + rotationMatrix.get(1, 1) + rotationMatrix.get(2, 2);

        if (trace > 0) {
            float s = 0.5f / (float) Math.sqrt(trace + 1);
            setX((rotationMatrix.get(1, 2) - rotationMatrix.get(2, 1)) * s);
            setY((rotationMatrix.get(2, 0) - rotationMatrix.get(0, 2)) * s);
            setZ((rotationMatrix.get(0, 1) - rotationMatrix.get(1, 0)) * s);
            setW(0.25f / s);
        } else {
            if (rotationMatrix.get(0, 0) > rotationMatrix.get(1, 1) && rotationMatrix.get(0, 0) > rotationMatrix.get(2, 2)) {
                float s = 2.0f * (float) Math.sqrt(1 + rotationMatrix.get(0, 0) - rotationMatrix.get(1, 1) - rotationMatrix.get(2, 2));
                setX(0.25f * s);
                setY((rotationMatrix.get(1, 0) + rotationMatrix.get(0, 1)) / s);
                setZ((rotationMatrix.get(2, 0) + rotationMatrix.get(0, 2)) / s);
                setW((rotationMatrix.get(1, 2) - rotationMatrix.get(2, 1)) / s);
            } else if (rotationMatrix.get(1, 1) > rotationMatrix.get(2, 2)) {
                float s = 2.0f * (float) Math.sqrt(1 + rotationMatrix.get(1, 1) - rotationMatrix.get(0, 0) - rotationMatrix.get(2, 2));
                setX((rotationMatrix.get(1, 0) + rotationMatrix.get(0, 1)) / s);
                setY(0.25f * s);
                setZ((rotationMatrix.get(2, 1) + rotationMatrix.get(1, 2)) / s);
                setW((rotationMatrix.get(2, 0) - rotationMatrix.get(0, 2)) / s);
            } else {
                float s = 2.0f * (float) Math.sqrt(+rotationMatrix.get(2, 2) - rotationMatrix.get(0, 0) - rotationMatrix.get(1, 1));
                setX((rotationMatrix.get(2, 0) + rotationMatrix.get(0, 2)) / s);
                setY((rotationMatrix.get(1, 2) + rotationMatrix.get(2, 1)) / s);
                setZ(0.25f * s);
                setW((rotationMatrix.get(0, 1) - rotationMatrix.get(1, 0)) / s);
            }
        }

        float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return this;
    }

    public Quaternion initRotation(Vector3f axis, float angle) {
        float sAngle = (float) Math.sin(angle / 2);
        float cAngle = (float) Math.cos(angle / 2);

        setX(axis.getX() * sAngle);
        setY(axis.getY() * sAngle);
        setZ(axis.getZ() * sAngle);
        setW(cAngle);

        return this;
    }

    public Quaternion faceAt(Vector3f direction, Vector3f up) {
        return fromRotationMatrix(new Matrix4f().initRotation(direction.normalized(), up));
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public void normalize() {
        float l = length();

        x /= l;
        y /= l;
        z /= l;
        w /= l;
    }

    public Quaternion normalized() {
        float l = length();

        return new Quaternion(x / l, y / l, z / l, w / l);
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(Quaternion q) {
        float _x = x * q.getW() + w * q.getX() + y * q.getZ() - z * q.getY();
        float _y = y * q.getW() + w * q.getY() + z * q.getX() - x * q.getZ();
        float _z = z * q.getW() + w * q.getZ() + x * q.getY() - y * q.getX();
        float _w = w * q.getW() - x * q.getX() - y * q.getY() - z * q.getZ();


        return new Quaternion(_x, _y, _z, _w);
    }

    public Quaternion mul(Vector3f v) {
        float _x = w * v.getX() + y * v.getZ() - z * v.getY();
        float _y = w * v.getY() + z * v.getX() - x * v.getZ();
        float _z = w * v.getZ() + x * v.getY() - y * v.getX();
        float _w = -x * v.getX() - y * v.getY() - z * v.getZ();

        return new Quaternion(_x, _y, _z, _w);
    }

    public Quaternion mul(float q) {
        return new Quaternion(x * q, y * q, z * q, w * q);
    }

    public Vector3f mult(Vector3f v) {
        Vector3f r = new Vector3f(0, 0, 0);

        if (v.isNullVector()) return r;

        float vx = v.getX(), vy = v.getY(), vz = v.getZ();

        r.setX(getW() * getW() * vx + 2 * getY() * getW() * vz - 2 * getZ() * getW() * vy + getX() * getX()
                * vx + 2 * getY() * getX() * vy + 2 * getZ() * getX() * vz - getZ() * getZ() * vx - getY()
                * getY() * vx);


        r.setY(2 * getX() * getY() * vx + getY() * getY() * vy + 2 * getZ() * getY() * vz + 2 * getW()
                * getZ() * vx - getZ() * getZ() * vy + getW() * getW() * vy - 2 * getX() * getW() * vz - getX()
                * getX() * vy);


        r.setX(2 * getX() * getZ() * vx + 2 * getY() * getZ() * vy + getZ() * getZ() * vz - 2 * getW()
                * getY() * vx - getY() * getY() * vz + 2 * getW() * getX() * vy - getX() * getX() * vz + getW()
                * getW() * vz);

        return r;
    }

    public Quaternion sub(Quaternion q) {
        return new Quaternion(getX() - q.getX(), getY() - q.getY(), getZ() - q.getZ(), getW() - q.getW());
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(getX() + q.getX(), getY() + q.getY(), getZ() + q.getZ(), getW() + q.getW());
    }

    public float dot(Quaternion q) {
        return getX() * q.getX() + getY() * q.getY() + getZ() * q.getZ() + getW() * q.getW();
    }


    public Matrix4f toRotationMatrix() {
        Vector3f f = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
        Vector3f u = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
        Vector3f r = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

        return new Matrix4f().initRotation(f, u, r);
    }

    public Quaternion nlerp(Quaternion dest, float factor, boolean shortest) {

        if (shortest && this.dot(dest) < 0) {
            dest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
        }

        return dest.sub(this).mul(factor).add(this).normalized();

    }

    public Vector3f getForward() {
        return new Vector3f(0, 0, 1).rotate(this);
    }

    public Vector3f getBack() {
        return new Vector3f(0, 0, -1).rotate(this);
    }

    public Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(this);
    }

    public Vector3f getDown() {
        return new Vector3f(0, -1, 0).rotate(this);
    }

    public Vector3f getRight() {
        return new Vector3f(1, 0, 0).rotate(this);
    }

    public Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(this);
    }

    public void set(Quaternion q) {
        setX(q.getX());
        setY(q.getY());
        setZ(q.getZ());
        setW(q.getW());
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public boolean equals(Quaternion q) {
        return x == q.getX() && y == q.getY() && z == q.getZ() && w == q.getW();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Quaternion && equals((Quaternion) obj);
    }
}
