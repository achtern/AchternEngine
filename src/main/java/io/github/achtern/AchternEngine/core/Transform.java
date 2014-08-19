package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Quaternion;
import io.github.achtern.AchternEngine.core.math.Vector3f;

public class Transform {

    public static final Vector3f X_AXIS = new Vector3f(1, 0, 0).lock();
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0).lock();
    public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1).lock();

    private Transform parent;
    private Matrix4f parentMat;

    private Vector3f position;
    private Quaternion rotation;
    private Vector3f scale;

    private Vector3f oldPosition;
    private Quaternion oldRotation;
    private Vector3f oldScale;

    public Transform() {
        position = new Vector3f(0, 0, 0);
        rotation = new Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);
        parentMat = new Matrix4f().initIdentiy();
    }

    public void update() {
        if (oldPosition != null) {
            oldPosition.set(position);
            oldRotation.set(rotation);
            oldScale.set(scale);
        } else {
            oldPosition = position.add(1);

            oldRotation = rotation.mul(0.5f);

            oldScale = scale.add(1);
        }
    }

    public Matrix4f getTransformation() {
        Matrix4f positionMat = new Matrix4f().initTranslation(position.getX(), position.getY(), position.getZ());
        Matrix4f rotationMat = rotation.toRotationMatrix();
        Matrix4f scaleMat = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        return getParentMatrix().mul(positionMat.mul(rotationMat.mul(scaleMat)));
    }

    public void rotate(Vector3f axis, float angle) {
        setRotation(new Quaternion().initRotation(axis, angle).mul(getRotation()).normalized());
    }

    public void faceAt(Vector3f at, Vector3f up) {
        getRotation().faceAt(at, up);
    }

    public Quaternion getFaceAt(Vector3f at, Vector3f up) {
        return new Quaternion(new Matrix4f().initRotation(at.sub(getPosition()).normalized(), up));
    }

    public boolean hasChanged() {

        if (parent != null && parent.hasChanged()) {
            return true;
        }

        if (!position.equals(oldPosition)) {
            return true;
        }

        if (!rotation.equals(oldRotation)) {
            return true;
        }

        if (!scale.equals(oldScale)) {
            return true;
        }

        return false;
    }

    public Vector3f getTransformedPosition() {
        return getParentMatrix().transform(this.getPosition());
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Quaternion getTransformedRotation() {

        Quaternion parent = new Quaternion(0, 0, 0, 1);

        if (this.parent != null) {
            parent = this.parent.getTransformedRotation();
        }

        return parent.mul(getRotation());

    }

    public Quaternion getRotation() {
        return rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setParent(Transform parent) {
        this.parent = parent;
    }

    private Matrix4f getParentMatrix() {
        if (this.parent != null && this.parent.hasChanged()) {
            this.parentMat = this.parent.getTransformation();
        }

        return this.parentMat;
    }
}
