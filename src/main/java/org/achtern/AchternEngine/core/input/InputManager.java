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

package org.achtern.AchternEngine.core.input;

import org.achtern.AchternEngine.core.input.adapter.InputAdapter;
import org.achtern.AchternEngine.core.input.inputmap.KeyMap;
import org.achtern.AchternEngine.core.input.inputmap.MouseMap;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputManager.class);

    protected InputAdapter input;

    @Getter protected KeyMap keyMap;

    @Getter protected MouseMap mouseMap;

    public InputManager(InputAdapter input) {
        this.input = input;
        this.setMouseMap(new MouseMap());
        this.setKeyMap(new KeyMap());
    }

    public void trigger(float delta) {

        keyMap.trigger(delta);
        mouseMap.trigger(delta);
        input.update();
    }

    public void setKeyMap(KeyMap keyMap) {
        this.keyMap = keyMap;
        this.keyMap.setInput(this.input);
    }

    public void setMouseMap(MouseMap mouseMap) {
        this.mouseMap = mouseMap;
        this.mouseMap.setInput(this.input);
    }
}
