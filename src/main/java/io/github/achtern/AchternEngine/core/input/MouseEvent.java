package io.github.achtern.AchternEngine.core.input;

import io.github.achtern.AchternEngine.core.math.Vector2f;

public class MouseEvent implements InputEvent {

    protected final MouseController controller;
    protected final MouseButton button;
    protected final float delta;
    protected final Vector2f position;

    public MouseEvent(MouseController controller, MouseButton button, float delta, Vector2f position) {
        this.controller = controller;
        this.button = button;
        this.delta = delta;
        this.position = position;
    }

    public MouseController getMouse() {
        return controller;
    }

    public MouseButton getButton() {
        return button;
    }

    public Vector2f getPosition() {
        return position;
    }

    @Override
    public float getDelta() {
        return delta;
    }
}
