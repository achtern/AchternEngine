package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.Transform;

public class FlyMover extends SimpleMover {
    private int upKey;
    private int downKey;

    public FlyMover(float speed) {
        this(speed, Input.KEY_SPACE, Input.KEY_LSHIFT);
    }

    public FlyMover(float speed, int upKey, int downKey) {
        super(speed);
        this.upKey = upKey;
        this.downKey = downKey;
    }

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
