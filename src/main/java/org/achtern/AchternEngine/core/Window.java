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

package org.achtern.AchternEngine.core;

import org.achtern.AchternEngine.core.rendering.RenderTarget;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Window extends Dimension implements RenderTarget {

    public static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public static Window get() {
        return instance;
    }

    protected static Window instance;

    public Window(Dimension copy) {
        super(copy);
    }

    public Window(int width, int height) {
        super(width, height);
    }

    public Vector2f getCenter() {
        return new Vector2f(getWidth() / 2, getHeight() / 2);
    }

    public abstract void create(String title);

    public abstract void enableResize(boolean enable);

    public abstract void render();

    public abstract boolean isCloseRequested();

    /**
     * This should set the width/height of the class
     * as well!
     * @return when the Window has been re-sized true
     */
    public abstract boolean resized();

    public abstract void dispose();

    public abstract String getTitle();

    public abstract void setTitle(String title);

}
