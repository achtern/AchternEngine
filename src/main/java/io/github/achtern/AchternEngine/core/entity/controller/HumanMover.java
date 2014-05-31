package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Vector3f;

public class HumanMover extends SimpleMover {

    public HumanMover(float speed) {
        super(speed);
    }

    public HumanMover(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        super(speed, forwardKey, backKey, leftKey, rightKey);
    }

    @Override
    public void input(float delta) {

        float amount = speed * delta;
        Vector3f horizontal = getTransform().getRotation().getForward().mul(Transform.X_AXIS.add(Transform.Z_AXIS)).normalized();

        if (Input.getKey(this.forwardKey)) {
            move(horizontal, amount);
        }

        if (Input.getKey(this.backKey)) {
            move(horizontal, -amount);
        }

        if (Input.getKey(this.leftKey)) {
            move(getTransform().getRotation().getLeft(), amount);
        }

        if (Input.getKey(this.rightKey)) {
            move(getTransform().getRotation().getRight(), amount);
        }

    }
}
