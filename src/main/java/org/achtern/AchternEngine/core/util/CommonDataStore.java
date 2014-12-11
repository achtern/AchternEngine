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

package org.achtern.AchternEngine.core.util;

import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.texture.Texture;

import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of a DataStore
 */
public class CommonDataStore implements DataStore {

    protected Map<String, Texture> textures = new HashMap<String, Texture>();
    protected Map<String, Vector3f> vectors = new HashMap<String, Vector3f>();
    protected Map<String, Color> colors = new HashMap<String, Color>();
    protected Map<String, Float> floats = new HashMap<String, Float>();
    protected Map<String, Integer> integers = new HashMap<String, Integer>();
    protected Map<String, Matrix4f> matrices = new HashMap<String, Matrix4f>();


    /**
     * @see DataStore#addTexture(String, Texture)
     */
    @Override
    public void addTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    /**
     * @see DataStore#getTexture(String)
     */
    @Override
    public Texture getTexture(String name) {
        return textures.get(name);
    }

    /**
     * @see DataStore#hasTexture(String)
     */
    @Override
    public boolean hasTexture(String name) {
        return textures.containsKey(name);
    }

    /**
     * @see DataStore#addVector(String, Vector3f)
     */
    @Override
    public void addVector(String name, Vector3f vector) {
        vectors.put(name, vector);
    }

    /**
     * @see DataStore#getVector(String)
     */
    @Override
    public Vector3f getVector(String name) {
        Vector3f r = vectors.get(name);
        if (r != null) {
            return r;
        }

        return new Vector3f(0, 0, 0);
    }

    /**
     * @see DataStore#hasVector(String)
     */
    @Override
    public boolean hasVector(String name) {
        return vectors.containsKey(name);
    }

    /**
     * @see DataStore#addColor(String, Color)
     */
    @Override
    public void addColor(String name, Color color) {
        colors.put(name, color);
    }

    /**
     * @see DataStore#getColor(String)
     */
    @Override
    public Color getColor(String name) {
        return colors.get(name);
    }

    /**
     * @see DataStore#hasColor(String)
     */
    @Override
    public boolean hasColor(String name) {
        return colors.containsKey(name);
    }

    /**
     * @see DataStore#addInteger(String, int)
     */
    @Override
    public void addInteger(String name, int i) {
        integers.put(name, i);
    }

    /**
     * @see DataStore#getInteger(String)
     */
    @Override
    public int getInteger(String name) {
        Integer r = integers.get(name);
        if (r != null) {
            return r;
        }

        return 0;
    }

    /**
     * @see DataStore#hasInteger(String)
     */
    @Override
    public boolean hasInteger(String name) {
        return integers.containsKey(name);
    }

    /**
     * @see DataStore#addFloat(String, float)
     */
    @Override
    public void addFloat(String name, float f) {
        floats.put(name, f);
    }

    /**
     * @see DataStore#getFloat(String)
     */
    @Override
    public float getFloat(String name) {
        Float r = floats.get(name);
        if (r != null) {
            return r;
        }

        return 0;
    }

    /**
     * @see DataStore#hasFloat(String)
     */
    @Override
    public boolean hasFloat(String name) {
        return floats.containsKey(name);
    }

    /**
     * @see DataStore#addMatrix(String, Matrix4f)
     */
    @Override
    public void addMatrix(String name, Matrix4f matrix) {
        matrices.put(name, matrix);
    }

    /**
     * @see DataStore#getMatrix(String)
     */
    @Override
    public Matrix4f getMatrix(String name) {
        return matrices.get(name);
    }

    /**
     * @see DataStore#hasMatrix(String)
     */
    @Override
    public boolean hasMatrix(String name) {
        return matrices.containsKey(name);
    }

}
