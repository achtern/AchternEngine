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

package io.github.achtern.AchternEngine.core.rendering.fog;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Color;

public class Fog {

    public static final Fog DISABLED = new Fog(null, 0, Mode.DISABLED);

    public enum Mode {
        DISABLED(-1),
        LINEAR(0),
        EXP(1),
        EXP2(2);


        private int mode;

        Mode(int mode) {
            this.mode = mode;
        }

        public int getID() {
            return mode;
        }
    }

    protected Color color;

    protected Vector2f range;

    protected float density;

    protected Mode mode;

    public Fog(Color color, float density) {
        this(color, density, Mode.EXP);
    }

    public Fog(Color color, float density, Mode mode) {
        this(color, new Vector2f(-1, -1), density, mode);
    }

    public Fog(Color color, Vector2f range) {
        this(color, range, -1, Mode.LINEAR);
    }

    public Fog(Color color, Vector2f range, float density, Mode mode) {
        this.color = color;
        this.range = range;
        this.density = density;
        this.mode = mode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2f getRange() {
        return range;
    }

    public void setRange(Vector2f range) {
        this.range = range;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
