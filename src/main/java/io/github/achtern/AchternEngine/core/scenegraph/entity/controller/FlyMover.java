/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;

/**
 * Moves an Node up and/or down.
 * This is meant to be used in conjunction with another controller.
 * Only applies changes to the position in the Y axis.
 * Default key binding is space to go up and left shift to go down.
 */
public class FlyMover extends SimpleMover {
    protected Key upKey;
    protected Key downKey;


    /**
     * Initialize a FlyMover with the default key binding
     * @param speed The movement speed on Y
     */
    public FlyMover(float speed) {
        this(speed, Key.SPACE, Key.LSHIFT);
    }

    /**
     * Initialize a FlyMover with a specific key binding
     * @param speed The movement speed on Y
     * @param upKey The key to move in positive Y
     * @param downKey The key to move in negative Y
     */
    public FlyMover(float speed, Key upKey, Key downKey) {
        super(speed);
        this.upKey = upKey;
        this.downKey = downKey;
    }

    @Override
    protected void registerListener() {
        getEngine().getGame().getInputManager().getKeyMap()
                    .register(new KeyTrigger(upKey, KeyTrigger.Type.PRESS), this)
                    .register(new KeyTrigger(downKey, KeyTrigger.Type.PRESS), this);
    }

    @Override
    public void onAction(KeyEvent event) {

        float amt = getSpeed() * event.getDelta();

        if (event.getKey().equals(upKey)) {
            move(Transform.Y_AXIS, amt);
        } else if (event.getKey().equals(downKey)) {
            move(Transform.Y_AXIS, -amt);
        }
    }

    public Key getUpKey() {
        return upKey;
    }

    public void setUpKey(Key upKey) {
        this.upKey = upKey;
    }

    public Key getDownKey() {
        return downKey;
    }

    public void setDownKey(Key downKey) {
        this.downKey = downKey;
    }
}
