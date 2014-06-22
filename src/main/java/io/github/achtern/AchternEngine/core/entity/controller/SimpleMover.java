package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.input.KeyEvent;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.KeyListener;
import io.github.achtern.AchternEngine.core.math.Vector3f;

/**
 * Moves a node around based on the up vector of the node.
 */
public class SimpleMover extends QuickEntity implements KeyListener {
    protected float speed;

    protected Key forwardKey;
    protected Key backKey;
    protected Key leftKey;
    protected Key rightKey;

    /**
     * Initialize a SimpleMover with the default key binding
     * @param speed The movement speed
     */
    public SimpleMover(float speed) {
        this(speed, Key.W, Key.S, Key.A, Key.D);
    }

    /**
     * Initialize a SimpleMover with a specific key binding
     * @param speed The movement speed
     * @param forwardKey The key to move forward
     * @param backKey The key to move backwards
     * @param leftKey The key to move left
     * @param rightKey The key to move right
     */
    public SimpleMover(float speed, Key forwardKey, Key backKey, Key leftKey, Key rightKey) {
        this.speed = speed;
        this.forwardKey = forwardKey;
        this.backKey = backKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.entity.Entity#attached()
     */
    @Override
    public void attached() {
        registerListener();
    }

    protected void registerListener() {
        getEngine().getGame().getKeyMap()
                .register(forwardKey, this)
                .register(backKey, this)
                .register(leftKey, this)
                .register(rightKey, this);
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

    @Override
    public Type getType() {
        return Type.PRESS;
    }

    @Override
    public void onAction(KeyEvent event) {

        float amt = getSpeed() * event.getDelta();

        if (event.getKey().equals(forwardKey)) {
            move(getTransform().getRotation().getForward(), amt);
        } else if (event.getKey().equals(backKey)) {
            move(getTransform().getRotation().getForward(), -amt);
        } else if (event.getKey().equals(leftKey)) {
            move(getTransform().getRotation().getLeft(), amt);
        } else if (event.getKey().equals(rightKey)) {
            move(getTransform().getRotation().getRight(), amt);
        }
    }
}
