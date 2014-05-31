package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.math.Vector3f;

public class SimpleMover extends QuickEntity {
    protected float speed;

    protected int forwardKey;
    protected int backKey;
    protected int leftKey;
    protected int rightKey;

    public SimpleMover(float speed) {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    public SimpleMover(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    @Override
    public void input(float delta) {

        float amount = speed * delta;

        if (Input.getKey(this.forwardKey)) {
            move(getTransform().getRotation().getForward(), amount);
        }

        if (Input.getKey(this.backKey)) {
            move(getTransform().getRotation().getForward(), -amount);
        }

        if (Input.getKey(this.leftKey)) {
            move(getTransform().getRotation().getLeft(), amount);
        }

        if (Input.getKey(this.rightKey)) {
            move(getTransform().getRotation().getRight(), amount);
        }
    }

    protected void move(Vector3f dir, float amt) {
        this.move(dir.mul(amt));
    }

    protected void move(Vector3f amount) {
        getTransform().setPosition(getTransform().getPosition().add(amount));
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
