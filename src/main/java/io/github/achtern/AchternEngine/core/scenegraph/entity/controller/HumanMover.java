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

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
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

    /**
     * @see io.github.achtern.AchternEngine.core.input.event.listener.KeyListener#onAction(KeyEvent)
     */
    @Override
    public void onAction(KeyEvent event) {

        // Calculate the horizantal axis
        Vector3f horizontal = getTransform().getRotation().getForward().mul(Transform.X_AXIS.add(Transform.Z_AXIS)).normalized();
        float amt = getSpeed() * event.getDelta();

        if (event.getKey().equals(forwardKey)) {
            move(horizontal, amt);
        } else if (event.getKey().equals(backKey)) {
            move(horizontal, -amt);
        } else if (event.getKey().equals(leftKey)) {
            move(getTransform().getRotation().getLeft(), amt);
        } else if (event.getKey().equals(rightKey)) {
            move(getTransform().getRotation().getRight(), amt);
        }

    }
}
