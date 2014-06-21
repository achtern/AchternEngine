package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.input.InputEvent;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.KeyListener;
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
    public HumanMover(float speed, Key forwardKey, Key backKey, Key leftKey, Key rightKey) {
        super(speed, forwardKey, backKey, leftKey, rightKey);
    }

    @Override
    protected void registerListener() {
        getEngine().getGame().getKeyMap().register(forwardKey, new KeyListener() {
            @Override
            public Type getType() {
                return Type.PRESS;
            }

            @Override
            public void onAction(InputEvent event) {
                Vector3f horizontal = getTransform().getRotation().getForward().mul(Transform.X_AXIS.add(Transform.Z_AXIS)).normalized();
                move(horizontal, getSpeed() * event.getDelta());
            }
        }).register(backKey, new KeyListener() {
            @Override
            public Type getType() {
                return Type.PRESS;
            }

            @Override
            public void onAction(InputEvent event) {
                Vector3f horizontal = getTransform().getRotation().getForward().mul(Transform.X_AXIS.add(Transform.Z_AXIS)).normalized();
                move(horizontal, -getSpeed() * event.getDelta());
            }
        }).register(leftKey, new KeyListener() {
            @Override
            public Type getType() {
                return Type.PRESS;
            }

            @Override
            public void onAction(InputEvent event) {
                move(getTransform().getRotation().getLeft(), getSpeed() * event.getDelta());
            }
        }).register(rightKey, new KeyListener() {
            @Override
            public Type getType() {
                return Type.PRESS;
            }

            @Override
            public void onAction(InputEvent event) {
                move(getTransform().getRotation().getRight(), getSpeed() * event.getDelta());
            }
        });
    }

}
