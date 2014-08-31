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

package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;

public class Color extends Vector4f {

    public static final Color BLACK = new Color(0, 0, 0, 1);

    public static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 0);

    public static final Color WHITE = new Color(1, 1, 1, 1);

    public static final Color RED = new Color(1, 0, 0, 1);

    public static final Color GREEN = new Color(0, 1, 0, 1);

    public static final Color BLUE = new Color(0, 0, 1, 1);

    public static final Color YELLOW = new Color(1, 1, 0, 1);

    public static final Color MAGENTA = new Color(1, 0, 1, 1);

    public static final Color TURQUOISE = new Color(1, 0, 1, 1);

    public static final Color ORANGE = new Color(1, 0.5f, 0, 1);

    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(Vector4f rgba) {
        super(rgba);
    }

    public Color(Vector3f rgb, float a) {
        super(rgb, a);
    }

    public java.awt.Color toAwt() {
        return new java.awt.Color(getRed(), getGreen(), getBlue(), getAlpha());
    }

    /**
     * Returns the raw RGB values, ignoring the alpha
     * @return RBG components
     */
    public Vector3f getRGB() {
        return new Vector3f(getRed(), getGreen(), getBlue());
    }

    /**
     * Returns the true color, by multiplying the RGB components by alpha
     * @return The true color (RGB * A)
     */
    public Vector3f getColor() {
        return new Vector3f(getRed(), getGreen(), getBlue()).mul(getAlpha());
    }

    public float getRed() {
        return getX();
    }

    public void setRed(float red) {
        setX(red);
    }

    public float getGreen() {
        return getY();
    }

    public void setGreen(float green) {
        setY(green);
    }

    public float getBlue() {
        return getZ();
    }

    public void setBlue(float blue) {
        setZ(blue);
    }

    public float getAlpha() {
        return getW();
    }

    public void setAlpha(float alpha) {
        setW(alpha);
    }
}
