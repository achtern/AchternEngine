package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.Transform;

/**
 * Moves an Node up and/or down.
 * This is meant to be used in conjunction with another controller.
 * Only applies changes to the position in the Y axis.
 * Default key binding is space to go up and left shift to go down.
 */
public class FlyMover extends SimpleMover {
    private int upKey;
    private int downKey;

    /**
     * Initialize a FlyMover with the default key binding
     * @param speed The movement speed on Y
     */
    public FlyMover(float speed) {
        this(speed, Input.KEY_SPACE, Input.KEY_LSHIFT);
    }

    /**
     * Initialize a FlyMover with a specific key binding
     * @param speed The movement speed on Y
     * @param upKey The key to move in positive Y
     * @param downKey The key to move in negative Y
     */
    public FlyMover(float speed, int upKey, int downKey) {
        super(speed);
        this.upKey = upKey;
        this.downKey = downKey;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.entity.controller.SimpleMover#input(float)
     */
    @Override
    public void input(float delta) {

        float amount = speed * delta;

        if (Input.getKey(upKey)) {
            move(Transform.Y_AXIS, amount);
        }

        if (Input.getKey(downKey)) {
            move(Transform.Y_AXIS, -amount);
        }
    }

    public int getUpKey() {
        return upKey;
    }

    public void setUpKey(int upKey) {
        this.upKey = upKey;
    }

    public int getDownKey() {
        return downKey;
    }

    public void setDownKey(int downKey) {
        this.downKey = downKey;
    }
}
