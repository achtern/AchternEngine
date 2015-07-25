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

package org.achtern.AchternEngine.core.scenegraph.entity.controller;

import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.input.Key;
import org.achtern.AchternEngine.core.input.MouseButton;
import org.achtern.AchternEngine.core.input.adapter.InputAdapter;
import org.achtern.AchternEngine.core.input.event.listener.KeyListener;
import org.achtern.AchternEngine.core.input.event.listener.MouseListener;
import org.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import org.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import org.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import org.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EqualsAndHashCode(callSuper = false)
@Data
public class MouseLook extends QuickEntity implements KeyListener, MouseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseLook.class);

    protected float sensitivity;
    protected boolean mouselock = false;
    protected Key unlockKey;

    public MouseLook(float sensitivity) {
        this(sensitivity, Key.ESCAPE);
    }

    public MouseLook(float sensitivity, Key unlockKey) {
        this.sensitivity = sensitivity;
        this.unlockKey = unlockKey;
    }

    /**
     * @see org.achtern.AchternEngine.core.scenegraph.entity.Entity#attached()
     */
    @Override
    public void attached() {
        registerListener();
    }

    protected void registerListener() {
        getEngine().getGame().getInputManager().getKeyMap()
                .register(new KeyTrigger(this.unlockKey), this);

        getEngine().getGame().getInputManager().getMouseMap()
                .register(new MouseButtonTrigger(MouseButton.LEFT), this)
                .register(this); // MouseMoveEvent
    }

    @Override
    public void onAction(KeyEvent event) {
        LOGGER.trace("unlockKey pressed <{}>; mouselock={}", unlockKey, isMouselock());
        if (!isMouselock()) return;
        event.getInputAdapter().setCursor(true);
        setMouselock(false);
        LOGGER.trace("Cursor enabled; mouselock={}", isMouselock());
    }

    @Override
    public void onAction(MouseEvent event) {
        if (event.getButton() == null) {
            // MouseMove
            if (!isMouselock()) {
                /*
                I honestly do not know why this is needed here,
                but it seems to fix a bug, where the cursor wouldn't get reenabled after unlocking.
                this action handler gets called and mouseLock gets updated, but the single call to
                event.getInputAdapter().setCursor(true); is not enough for some reason,
                maybe the second one fixes it, because... I don't know...
                Sorry.
                 */
                event.getInputAdapter().setCursor(true);
                return;
            }


            Vector2f factor = getDeltaMoveFactor(getSensitivity(), event.getMouseDelta()).abs();

            getTransform().rotate(getTransform().getRotation().getRight(), -event.getMouseDelta().getY() * factor.getX());

            getTransform().rotate(Transform.Y_AXIS, event.getMouseDelta().getX() * factor.getY());

            centerMouse(event.getInputAdapter());
        } else {
            // Mouse Click
            event.getInputAdapter().setCursor(false);
            setMouselock(true);
            centerMouse(event.getInputAdapter());
        }
    }

    /**
     * This returns the sensitivity aka delta move factor.
     *
     * This returns the sensitivity based on the delta.
     *
     * This is a logistic function, meaning that it tends to 0 for small values and to 1 for big.
     *
     * I this case it tends to {@link #getSensitivity()} for high values and if the delta is 0,
     *  this function returns 0.25.
     * This allows to move the mouse accurately up to a 1/4 of a pixel!
     *
     *
     * Only the value will be taken, on the returned vector will {@link org.achtern.AchternEngine.core.math.Vector2f#abs()}
     *  be called.
     *
     * @param sensitivity sensitivity, supplied by the user
     * @param delta mouse move event delta {@link org.achtern.AchternEngine.core.input.event.payload.MouseEvent}
     * @return move factor for X and Y axis
     */
    protected Vector2f getDeltaMoveFactor(float sensitivity, Vector2f delta) {
        float f0 = 0.25f; // This is the y-axis value at x = 0
        float k = 0.7f; // k determines how steep/fast the sensitivity increases.

        // We have to take absoulute values, otherwise the sensitivity would be different for
        // downwards motions or upwards motions.
        delta = delta.abs();

        float x = sensitivity / (
                (float) (1 + Math.exp(-k * delta.getX()))
                        * ((1 / f0) - 1)
        );

        float y = sensitivity / (
                (float) (1 + Math.exp(-k * delta.getY()))
                        * ((1 / f0) - 1)
        );

        // This function should return values in the range of 0 and <sensitivity> only.
        assert 0 <= x && x <= sensitivity;
        assert 0 <= y && y <= sensitivity;


        return new Vector2f(x, y);
    }


    protected void centerMouse(InputAdapter input) {
        input.setMousePosition(getEngine().getWindow().getCenter());
    }
}
