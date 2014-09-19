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
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class MouseLook extends QuickEntity implements KeyListener, MouseListener {

    protected float sensitivity;
    protected boolean mouselock = false;
    protected Key unlockKey;

    protected boolean skip = true;

    public MouseLook(float sensitivity) {
        this(sensitivity, Key.ESCAPE);
    }

    public MouseLook(float sensitivity, Key unlockKey) {
        this.sensitivity = sensitivity;
        this.unlockKey = unlockKey;
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
                .register(new KeyTrigger(this.unlockKey), this);

        getEngine().getGame().getInputManager().getMouseMap()
                .register(new MouseButtonTrigger(MouseButton.LEFT), this)
                .register(this); // MouseMoveEvent
    }

    @Override
    public void onAction(KeyEvent event) {
        if (!isMouselock()) return;
        event.getInputAdapter().setCursor(true);
        setMouselock(false);
    }

    @Override
    public void onAction(MouseEvent event) {
        if (event.getButton() == null) {
            // MouseMove
            if (!isMouselock()) {
                return;
            }

            if (skip) {
                skip = false;
                return;
            }

            getTransform().rotate(getTransform().getRotation().getRight(), -event.getMouseDelta().getY() * sensitivity);

            getTransform().rotate(Transform.Y_AXIS, event.getMouseDelta().getX() * sensitivity);

            centerMouse(event.getInputAdapter());
        } else {
            // Mouse Click
            event.getInputAdapter().setCursor(false);
            setMouselock(true);
            centerMouse(event.getInputAdapter());
            skip = true;
        }
    }


    protected void centerMouse(InputAdapter input) {
        input.setMousePosition(getEngine().getWindow().getCenter());
    }
}
