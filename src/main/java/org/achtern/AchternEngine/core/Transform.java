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

package org.achtern.AchternEngine.core;

import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Quaternion;
import org.achtern.AchternEngine.core.math.Vector3f;

public class Transform {

    public static final Vector3f X_AXIS = new Vector3f(1, 0, 0).lock();
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0).lock();
    public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1).lock();

    private Transform parent;
    private Matrix4f parentMat;

    private Vector3f position;
    private Quaternion rotation;
    private Vector3f scale;


    public Transform() {
        position = new Vector3f(0, 0, 0);
        rotation = new Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);
        parentMat = new Matrix4f().initIdentiy();
    }

    public Matrix4f getTransformation() {
        Matrix4f positionMat = new Matrix4f().initTranslation(position.getX(), position.getY(), position.getZ());
        Matrix4f rotationMat = rotation.toRotationMatrix();
        Matrix4f scaleMat = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        return getParentMatrix().mul(positionMat.mul(rotationMat.mul(scaleMat)));
    }

    /**
     * Rotate around the given axis.
     * @param axis Axis to rotate around
     * @param angle Angle in DEGREE
     */
    public void rotate(Vector3f axis, float angle) {
        setRotation(new Quaternion().initRotation(axis, (float) Math.toRadians(angle)).mul(getRotation()).normalized());
    }

    public void faceAt(Vector3f at, Vector3f up) {
        getRotation().faceAt(at, up);
    }

    public Quaternion getFaceAt(Vector3f at, Vector3f up) {
        return new Quaternion(new Matrix4f().initRotation(at.sub(getPosition()).normalized(), up));
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
        if (this.parent != null) {
            this.parentMat = this.parent.getTransformation();
        }

        return this.parentMat;
    }
}
