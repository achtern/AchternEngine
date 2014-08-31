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

package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.fog.Fog;

public class FogGenerator extends QuickEntity implements GlobalEntity<Fog> {

    protected Fog fog;

    public FogGenerator() {
        this("Fog");
    }

    /**
     * Create a FogGenerator
     *
     * @param name The name
     */
    public FogGenerator(String name) {
        super(name);
        // we just add all data, to allow a quick change of modes
        this.fog = new Fog(new Color(0.7f, 0.7f, 0.7f), new Vector2f(5, 50), 0.01f, Fog.Mode.EXP);
    }

    public FogGenerator(Color color, float density) {
        this(color, density, Fog.Mode.EXP);
    }

    public FogGenerator(Color color, float density, Fog.Mode mode) {
        this(color, new Vector2f(-1, -1), density, mode);
    }

    public FogGenerator(Color color, Vector2f range) {
        this(color, range, -1, Fog.Mode.LINEAR);
    }

    public FogGenerator(Color color, Vector2f range, float density, Fog.Mode mode) {
        this();
        fog.setColor(color);
        fog.setRange(range);
        fog.setDensity(density);
        fog.setMode(mode);
    }

    public Color getColor() {
        return fog.getColor();
    }

    public void setColor(Color color) {
        fog.setColor(color);
    }

    public void setRange(Vector2f range) {
        fog.setRange(range);
    }

    public float getDensity() {
        return fog.getDensity();
    }

    public Vector2f getRange() {
        return fog.getRange();
    }

    public Fog.Mode getMode() {
        return fog.getMode();
    }

    public void setMode(Fog.Mode mode) {
        fog.setMode(mode);
    }

    public void setDensity(float density) {
        fog.setDensity(density);
    }

    @Override
    public Fog getObject() {
        return fog;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.scenegraph.entity.Entity#attached()
     */
    @Override
    public void attached() {
        super.attached();
        getEngine().getRenderEngine().addGlobal(this);
    }

    /**
     * Sets the parent to null.
     *
     * @see io.github.achtern.AchternEngine.core.scenegraph.entity.Entity#removed()
     */
    @Override
    public void removed() {
        super.removed();
        getEngine().getRenderEngine().removeGlobal(this);
    }
}
