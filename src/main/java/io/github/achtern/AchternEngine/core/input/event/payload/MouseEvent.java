package io.github.achtern.AchternEngine.core.input.event.payload;

import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.math.Vector2f;

public class MouseEvent implements InputEvent {

    protected final InputAdapter input;
    protected final MouseButton button;
    protected final float delta;
    protected final Vector2f position;

    public MouseEvent(InputAdapter input, MouseButton button, float delta, Vector2f position) {
        this.input = input;
        this.button = button;
        this.delta = delta;
        this.position = position;
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

    @Override
    public InputAdapter getInputAdapter() {
        return input;
    }


}
