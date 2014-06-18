package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Vector3f;

/**
 * Moves a Node around in horizontal axis only.
 * Best used for the character.
 * (No vertical movement with this alone!)
 */
public class HumanMover extends SimpleMover {

    /**
     * Initialize a HumanMover with the default key binding
     * @param speed The movement speed
     */
    public HumanMover(float speed) {
        super(speed);
    }

    /**
     * Initialize a HumanMover with a specific key binding
     * @param speed The movement speed
     * @param forwardKey The key to move forward
     * @param backKey The key to move backwards
     * @param leftKey The key to move left
     * @param rightKey The key to move right
     */
    public HumanMover(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        super(speed, forwardKey, backKey, leftKey, rightKey);
    }

    /**
     * @see io.github.achtern.AchternEngine.core.entity.QuickEntity#input(float)
     */
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
