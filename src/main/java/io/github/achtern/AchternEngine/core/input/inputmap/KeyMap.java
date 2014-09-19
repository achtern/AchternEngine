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

package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KeyMap implements InputMap<KeyTrigger, KeyListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyMap.class);

    @Getter @Setter protected InputAdapter input;

    protected Map<KeyTrigger, List<KeyListener>> listener;

    protected Map<KeyTrigger, List<KeyListener>> delayed;

    protected boolean cycle = false;

    public KeyMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public KeyMap() {
        this.listener = new HashMap<KeyTrigger, List<KeyListener>>();
        this.delayed = new HashMap<KeyTrigger, List<KeyListener>>();
    }

    public KeyMap register(KeyTrigger key, KeyListener h) {
        if (cycle) {
            if (!this.delayed.containsKey(key)) {
                this.delayed.put(key, new ArrayList<KeyListener>());
            }

            this.delayed.get(key).add(h);
            return this;
        }

        if (!this.listener.containsKey(key)) {
            this.listener.put(key, new ArrayList<KeyListener>());
        }

        this.listener.get(key).add(h);

        return this;
    }

    public KeyMap register(List<KeyTrigger> keys, KeyListener h) {
        for (KeyTrigger t : keys) {
            register(t, h);
        }

        return this;
    }

    public void trigger(float delta) {

        Set<KeyTrigger> keys = this.listener.keySet();
        cycle = true;
        for (KeyTrigger k : keys) {

            if (input.getKey(k.get()) && k.getType().equals(KeyTrigger.Type.PRESS)) {
                cycle(k, delta);
            }

            if (input.getKeyDown(k.get()) && k.getType().equals(KeyTrigger.Type.DOWN)) {
                cycle(k, delta);
            }

            if (input.getKeyUp(k.get()) && k.getType().equals(KeyTrigger.Type.UP)) {
                cycle(k, delta);
            }
        }
        cycle = false;

        if (!delayed.isEmpty()) {
            this.listener.putAll(delayed);
            delayed.clear();

            assert delayed.size() == 0;
        }

    }

    protected void cycle(KeyTrigger k, float delta) {
        for (KeyListener l : this.listener.get(k)) {
            l.onAction(new KeyEvent(input, k.get(), delta));
        }
    }

    public Map<KeyTrigger, List<KeyListener>> getClickListener() {
        return listener;
    }

    public void setClickListener(Map<KeyTrigger, List<KeyListener>> listener) {
        this.listener = listener;
    }
}
