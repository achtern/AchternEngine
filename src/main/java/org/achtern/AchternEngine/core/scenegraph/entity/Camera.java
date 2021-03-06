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

package org.achtern.AchternEngine.core.scenegraph.entity;

import org.achtern.AchternEngine.core.CoreEngine;
import org.achtern.AchternEngine.core.Window;
import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.util.WindowChangeListener;
import lombok.Getter;
import lombok.Setter;

public class Camera extends QuickEntity implements WindowChangeListener {

    @Getter protected float fov;
    @Getter protected float aspect;
    @Getter protected float zNear;
    @Getter protected float zFar;

    @Setter protected Matrix4f projection;


    /**
     * For internal use only
     * (or for smart people out there)
     * @param projection The view camera-projection
     */
    public Camera(Matrix4f projection) {
        setProjection(projection);
    }


    public Camera() {
        this(Window.get());
    }


    public Camera(Dimension screen) {
        this(70, (float) screen.getWidth() / (float) screen.getHeight());
    }

    public Camera(float fov, float aspect) {
        this(fov, aspect, 0.1f, 1000);
    }

    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.fov = fov;
        this.aspect = aspect;
        this.zNear = zNear;
        this.zFar = zFar;
        setMatrix(fov, aspect, zNear, zFar);
    }

    public Matrix4f getViewProjection() {
        Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraRotMat = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
        Matrix4f cameraTransMat = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        return projection.mul(getView());
    }

    public Matrix4f getView() {
        Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraRotMat = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
        Matrix4f cameraTransMat = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        return cameraRotMat.mul(cameraTransMat);
    }

    public void bindAsMain() {
        getEngine().getRenderEngine().setCamera(this);
    }

    @Override
    public void setEngine(CoreEngine engine) {
        super.setEngine(engine);
        if (engine != null) {
            engine.getRenderEngine().setCamera(this);
            engine.addWindowChangeListener(this);
        }
    }

    /**
     * @see org.achtern.AchternEngine.core.scenegraph.entity.Entity#removed()
     */
    @Override
    public void removed() {
        super.removed();
        getEngine().getRenderEngine().setCamera(null);
        getEngine().removeWindowChangeListener(this);
    }

    public void setFov(float fov) {
        this.fov = fov;
        setMatrix(fov, aspect, zNear, zFar);
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        setMatrix(fov, aspect, zNear, zFar);
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
        setMatrix(fov, aspect, zNear, zFar);
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
        setMatrix(fov, aspect, zNear, zFar);
    }

    protected void setMatrix(float fov, float aspect, float zNear, float zFar) {
        if (projection == null) {
            projection = new Matrix4f();
        }
        setProjection(projection.initPerspective((float) Math.toRadians(fov), aspect, zNear, zFar));
    }

    @Override
    public void onWindowChange(Window window) {
        setAspect((float) window.getWidth() / (float) window.getHeight());
    }
}
