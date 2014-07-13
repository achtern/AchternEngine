package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;

public class Camera extends QuickEntity {

    protected Matrix4f projection;


    /**
     * For internal use only
     * (or for really smart people out there)
     * @param projection The view camera-projection
     */
    public Camera(Matrix4f projection) {
        setProjection(projection);
    }


    public Camera() {
        this(Window.get());
    }


    public Camera(Dimension screen) {
        this((float) screen.getWidth() / (float) screen.getHeight());
    }

    public Camera(float aspect) {
        this((float) Math.toRadians(70), aspect, 0.1f, 1000);
    }

    public Camera(float fov, float aspect, float zNear, float zFar) {
        setProjection(new Matrix4f().initPerspective(fov, aspect, zNear, zFar));
    }

    public Matrix4f getViewProjection() {
        Vector3f cameraPosition = getTransform().getTransformedPosition().mul(-1);

        Matrix4f cameraRotMat = getTransform().getTransformedRotation().conjugate().toRotationMatrix();
        Matrix4f cameraTransMat = new Matrix4f().initTranslation(cameraPosition.getX(), cameraPosition.getY(), cameraPosition.getZ());

        return projection.mul(cameraRotMat.mul(cameraTransMat));
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }

    public void bindAsMain() {
        getEngine().getRenderEngine().addCamera(this);
    }

    @Override
    public void setEngine(CoreEngine engine) {
        super.setEngine(engine);
        if (engine != null) {
            engine.getRenderEngine().addCamera(this);
        }
    }

    /**
     * @see io.github.achtern.AchternEngine.core.scenegraph.entity.Entity#removed()
     */
    @Override
    public void removed() {
        super.removed();
        getEngine().getRenderEngine().setMainCamera(null);
    }
}
