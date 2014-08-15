package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;

public class OrthographicCamera extends Camera {


    public OrthographicCamera() {
        this(Window.get());
    }

    public OrthographicCamera(Dimension screen) {
        this((float) screen.getWidth() / (float) screen.getHeight());
    }

    public OrthographicCamera(float aspect) {
        this(-aspect, aspect, -1, 1, 0.1f, 1000);
    }

    public OrthographicCamera(float left, float right, float bottom, float top, float near, float far) {
        super(new Matrix4f().initOrthographic(left, right, bottom, top, near, far));
    }
}
