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

package org.achtern.AchternEngine.core.input.inputmap;

import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.input.adapter.InputAdapter;
import org.achtern.AchternEngine.core.input.event.listener.MouseListener;
import org.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import org.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MouseMap implements InputMap<MouseButtonTrigger, MouseListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseMap.class);

    @Getter @Setter protected InputAdapter input;

    @Setter protected Map<MouseButtonTrigger, List<MouseListener>> clickListener;

    protected List<MouseListener> moveListener;

    public MouseMap() {
        this.clickListener = new HashMap<MouseButtonTrigger, List<MouseListener>>();
        this.moveListener = new ArrayList<MouseListener>();
    }

    public MouseMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public MouseMap register(MouseButtonTrigger button, MouseListener l) {
        if (!this.clickListener.containsKey(button)) {
            this.clickListener.put(button, new ArrayList<MouseListener>());
        }

        this.clickListener.get(button).add(l);

        return this;
    }

    public MouseMap register(List<MouseButtonTrigger> buttons, MouseListener h) {

        for (MouseButtonTrigger t : buttons) {
            register(t, h);
        }

        return this;
    }

    public MouseMap register(MouseListener l) {
        moveListener.add(l);

        return this;
    }

    public void trigger(float delta) {

        Set<MouseButtonTrigger> keys = this.clickListener.keySet();

        for (MouseButtonTrigger b : keys) {

            if (input.getMouse(b.get()) && b.getType().equals(MouseButtonTrigger.Type.PRESS)) {
                cycle(b, delta);
            }

            if (input.getMouseDown(b.get()) && b.getType().equals(MouseButtonTrigger.Type.DOWN)) {
                cycle(b, delta);
            }

            if (input.getMouseUp(b.get()) && b.getType().equals(MouseButtonTrigger.Type.UP)) {
                cycle(b, delta);
            }

        }

        Vector2f mDelta = input.getMouseDelta();

        if (mDelta.isNullVector()) return;

        for (MouseListener l : this.moveListener) {
            l.onAction(new MouseEvent(input, null, delta, input.getMousePosition(), mDelta));
        }
    }

    protected void cycle(MouseButtonTrigger b, float delta) {
        for (MouseListener l : this.clickListener.get(b)) {
            l.onAction(new MouseEvent(input, b.get(), delta, input.getMousePosition(), null));
        }
    }

    @Override
    public Map<MouseButtonTrigger, List<MouseListener>> getClickListener() {
        return clickListener;
    }
}
