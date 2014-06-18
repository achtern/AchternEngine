package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.math.Vector3f;

/**
 * Moves a node around based on the up vector of the node.
 */
public class SimpleMover extends QuickEntity {
    protected float speed;

    protected int forwardKey;
    protected int backKey;
    protected int leftKey;
    protected int rightKey;

    /**
     * Initialize a SimpleMover with the default key binding
     * @param speed The movement speed
     */
    public SimpleMover(float speed) {
        this(speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D);
    }

    /**
     * Initialize a SimpleMover with a specific key binding
     * @param speed The movement speed
     * @param forwardKey The key to move forward
     * @param backKey The key to move backwards
     * @param leftKey The key to move left
     * @param rightKey The key to move right
     */
    public SimpleMover(float speed, int forwardKey, int backKey, int leftKey, int rightKey) {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.entity.QuickEntity#input(float)
     */
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

    /**
     * Moves this node by amt into dir
     * @param dir The direction to move in
     * @param amt The amount
     */
    protected void move(Vector3f dir, float amt) {
        this.move(dir.mul(amt));
    }

    /**
     * Moves this node by the amount vector
     * @param amount The direction and amount to move in
     */
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
