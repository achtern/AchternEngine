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

package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

import io.github.achtern.AchternEngine.core.input.Key;
import lombok.Getter;

public class KeyTrigger implements Trigger<Key, KeyTrigger.Type> {

    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_OR_DOWN,
        ALL
    }

    protected Key onKey;
    @Getter protected Type type;


    /**
     * Creates a new KeyTrigger, with a key and triggers on
     * key {@link KeyTrigger.Type#DOWN}
     * @param onKey The key which should trigger the event
     */
    public KeyTrigger(Key onKey) {
        this(onKey, Type.DOWN);
    }

    /**
     * Creates a new KeyTrigger, with a key and triggers on
     * the given {@link KeyTrigger.Type}
     * @param onKey The key which should trigger the event
     * @param type Only if the key event matches this type. The event does get fired
     */
    public KeyTrigger(Key onKey, Type type) {
        this.onKey = onKey;
        this.type = type;
    }

    @Override
    public Key get() {
        return onKey;
    }

}
