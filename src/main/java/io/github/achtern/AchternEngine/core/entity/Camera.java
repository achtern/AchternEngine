package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;

public class Camera extends QuickEntity {

    private Matrix4f projection;

    public Camera() {
        this((float) Window.getWidth() / (float) Window.getHeight());
    }

    public Camera(float aspect) {
        this((float) Math.toRadians(70), aspect, 0.1f, 1000);
    }

    public Camera(float fov, float aspect, float zNear, float zFar) {
        this.projection = new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
    }

    public Matrix4f getViewProjection() {
        Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraRotMat = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
        Matrix4f cameraTransMat = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        return projection.mul(cameraRotMat.mul(cameraTransMat));
    }

    @Override
    public void setEngine(CoreEngine engine) {
        engine.getRenderEngine().addCamera(this);
    }
}
