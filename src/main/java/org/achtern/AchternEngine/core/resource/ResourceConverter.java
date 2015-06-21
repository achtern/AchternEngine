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

package org.achtern.AchternEngine.core.resource;

import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.util.UBuffer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

public class ResourceConverter {

    public static ByteBuffer toByteBuffer(BufferedImage image) {
        return toByteBuffer(image, Dimension.fromBufferedImage(image));
    }

    public static ByteBuffer toByteBuffer(BufferedImage image, Dimension dimension) {


        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel = image.getColorModel().hasAlpha();

        // bytes per pixel
        int bpp = 3;
        if (hasAlphaChannel) {
            bpp += 1;
        }

        final ByteBuffer buffer = UBuffer.createByteBuffer(dimension.getHeight() * dimension.getWidth() * bpp);

        if (hasAlphaChannel) {
            for (int i = 0; i < pixels.length; i += bpp) {
                buffer.put(pixels[i + 3]); // red
                buffer.put(pixels[i + 2]); // green
                buffer.put(pixels[i + 1]); // blue
                buffer.put(pixels[i]); // alpha
            }
        } else {
            for (int i = 0; i < pixels.length; i += bpp) {
                buffer.put(pixels[i + 2]); // red
                buffer.put(pixels[i + 1]); // green
                buffer.put(pixels[i]); // blue
            }
        }

        buffer.flip();


        return buffer;
    }
}
