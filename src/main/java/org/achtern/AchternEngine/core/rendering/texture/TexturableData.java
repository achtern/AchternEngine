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

package org.achtern.AchternEngine.core.rendering.texture;

import org.achtern.AchternEngine.core.rendering.Dimension;

import java.nio.ByteBuffer;

/**
 * TextureableData has enough information to set
 * a {@link org.achtern.AchternEngine.core.rendering.texture.Texture}.
 */
public interface TexturableData {

    /**
     * The Texture Type
     * @return Texture Type
     */
    public Type getType();

    /**
     * MinFilter to use
     * @return Filter
     */
    public Filter getMinFilter();

    /**
     * MagFilter to use
     * @return Filter
     */
    public Filter getMagFilter();

    /**
     * Texture Format
     * @return Texture Format
     */
    public Format getFormat();

    /**
     * Internal Format
     * @return InternalFormat
     */
    public InternalFormat getInternalFormat();

    /**
     * Width &amp; Height
     * @return the dimensions
     */
    public Dimension getDimension();

    /**
     * Has the image Alpha
     * @return Whether alpha components are present in the buffer
     */
    public boolean hasAlpha();

    /**
     * The main image data
     * @return image data
     */
    public ByteBuffer getData();

}
