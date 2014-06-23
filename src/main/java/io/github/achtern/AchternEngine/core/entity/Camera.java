package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;

public class Camera extends QuickEntity {

    public enum Projection {
        PERSPECTIVE,
        ORTHOGRAPHIC
    }

    protected Matrix4f projection;
    protected Projection projectionType;

    public Camera() {
        this((float) Window.getWidth() / (float) Window.getHeight());
    }

    public Camera(float aspect) {
        this((float) Math.toRadians(70), aspect, 0.1f, 1000);
    }

    public Camera(float fov, float aspect, float zNear, float zFar) {
        setProjection(new Matrix4f().initPerspective(fov, aspect, zNear, zFar), Projection.PERSPECTIVE);
    }

    public Camera(Dimension screen) {
        setProjection(new Matrix4f().initOrthographic(0, screen.getHeight(), screen.getWidth(), 0, -1, 1), Projection.ORTHOGRAPHIC);
    }

    public Matrix4f getViewProjection() {
        Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraRotMat = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
        Matrix4f cameraTransMat = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        return projection.mul(cameraRotMat.mul(cameraTransMat));
    }

    public void setProjection(Projection p) {
        switch (p) {
            case PERSPECTIVE:
                setProjection(new Matrix4f().initPerspective(
                        (float) Math.toRadians(70),
                        Window.getWidth() / Window.getHeight(),
                        0.1f, 1000),
                        p
                );
                break;
            case ORTHOGRAPHIC:
                setProjection(new Matrix4f().initOrthographic(
                        0,
                        Window.getHeight(),
                        Window.getWidth(),
                        0,
                        -1, 1),
                        p
                );
                break;
            default:
                throw new RuntimeException("Unknow Projection Type");
        }


    }

    public void setProjection(Matrix4f projection, Projection type) {
        this.projection = projection;
        this.projectionType = type;
    }

    public Projection getProjectionType() {
        return projectionType;
    }

    @Override
    public void setEngine(CoreEngine engine) {
        engine.getRenderEngine().addCamera(this);
    }
}
