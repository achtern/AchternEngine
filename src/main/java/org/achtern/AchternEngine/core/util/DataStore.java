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

/**
 * Indicate that the class can store
 * a lot of common data
 */
public interface DataStore {

    /**
     * Adds a Texture
     * @param name key
     * @param texture value
     */
    public void addTexture(String name, Texture texture);

    /**
     * Retrieve a Texture
     * @param name key
     * @return value
     */
    public Texture getTexture(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasTexture(String name);

    /**
     * Adds a Vector
     * @param name key
     * @param vector value
     */
    public void addVector(String name, Vector3f vector);

    /**
     * Retrieve a Vector
     * @param name key
     * @return value
     */
    public Vector3f getVector(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasVector(String name);

    /**
     * Adds a Color
     * @param name key
     * @param color value
     */
    public void addColor(String name, Color color);

    /**
     * Retrieve a Color
     * @param name key
     * @return value
     */
    public Color getColor(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasColor(String name);

    /**
     * Adds a Float
     * @param name key
     * @param f value
     */
    public void addFloat(String name, float f);

    /**
     * Retrieve a Float
     * @param name key
     * @return value
     */
    public float getFloat(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasFloat(String name);

    /**
     * Adds a Integer
     * @param name key
     * @param i value
     */
    public void addInteger(String name, int i);

    /**
     * Retrieve a Integer
     * @param name key
     * @return value
     */
    public int getInteger(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasInteger(String name);

    /**
     * Adds a Matrix4f
     * @param name key
     * @param matrix value
     */
    public void addMatrix(String name, Matrix4f matrix);

    /**
     * Retrieve a Matrix4f
     * @param name key
     * @return value
     */
    public Matrix4f getMatrix(String name);

    /**
     * Whether a value for the given key
     * exists
     * @param name key
     * @return true when exists
     */
    public boolean hasMatrix(String name);
}
