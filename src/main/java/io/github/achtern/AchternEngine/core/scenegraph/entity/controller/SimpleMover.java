/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.scenegraph.entity.controller;

import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
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
     * @see io.github.achtern.AchternEngine.core.scenegraph.entity.Entity#attached()
     */
    @Override
    public void attached() {
        registerListener();
    }

    protected void registerListener() {
        getEngine().getGame().getInputManager().getKeyMap()
                .register(new KeyTrigger(forwardKey, KeyTrigger.Type.PRESS), this)
                .register(new KeyTrigger(backKey, KeyTrigger.Type.PRESS), this)
                .register(new KeyTrigger(leftKey, KeyTrigger.Type.PRESS), this)
                .register(new KeyTrigger(rightKey, KeyTrigger.Type.PRESS), this);
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
