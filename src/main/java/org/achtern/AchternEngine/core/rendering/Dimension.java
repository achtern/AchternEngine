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

package org.achtern.AchternEngine.core.rendering;

import org.achtern.AchternEngine.core.math.Vector2f;

import java.awt.image.BufferedImage;

public class Dimension extends Vector2f {

    public static Dimension fromBufferedImage(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public Dimension(Dimension copy) {
        this(copy.getWidth(), copy.getHeight());
    }

    public Dimension(int width, int height) {
        super(width, height);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Not valid dimension, width|height MUST be greater than 0");
        }
    }

    public Dimension factor2() {
        int w = 2;
        int h = 2;

        while (w < getWidth()) w *= 2;
        while (h < getHeight()) h *= 2;

        return new Dimension(w, h);
    }

    public void setHeight(int height) {
        super.setY((int) height);
    }

    public void setWidth(int width) {
        super.setX((int) width);
    }

    public int getHeight() {
        return (int) getY();
    }

    public int getWidth() {
        return (int) getX();
    }
}