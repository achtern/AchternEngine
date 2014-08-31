/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

package io.github.achtern.AchternEngine.core.rendering.generator;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.texture.ByteImage;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.texture.TexturableData;
import io.github.achtern.AchternEngine.core.util.UBuffer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ImageGenerator {

    public static BufferedImage fromColor(Color color) {
        return fromColor(new Vector2f(1, 1), color);
    }

    public static BufferedImage fromColor(Vector2f dimensions, Color color) {
        BufferedImage image = new BufferedImage((int) dimensions.getX(), (int) dimensions.getY(), BufferedImage.TYPE_INT_RGB);


        Graphics2D g = image.createGraphics();

        g.setPaint(color.toAwt());
        g.fillRect(0, 0, (int) dimensions.getX(), (int) dimensions.getY());


        g.dispose();

        return image;
    }

    public static TexturableData bytesFromColor(Color color) {
        return bytesFromColor(new Dimension(1, 1), color);
    }

    public static TexturableData bytesFromColor(Dimension dimension, Color color) {
        ByteBuffer buffer = UBuffer.createByteBuffer((int) (dimension.getX() * dimension.getY() * 4));

        for(int y = 0; y < dimension.getY(); y++) {
            for(int x = 0; x < dimension.getX(); x++) {


                // Internal Format is RGBA8 by default
                buffer.put((byte) (color.getRed() * 255));
                buffer.put((byte) (color.getGreen() * 255));
                buffer.put((byte) (color.getBlue() * 255));
                buffer.put((byte) (color.getAlpha() * 255));

            }
        }

        buffer.flip();

        return new ByteImage(buffer, true, dimension);
    }

}
