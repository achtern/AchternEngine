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

import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.rendering.Dimension;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

public class ResourceConverter {

    public enum PaddingMode {
        H_CENTER,
        V_CENTER,

        LEFT,
        RIGHT,
        CENTER
    }

    private static final ColorModel glAlphaColorModel =
            new ComponentColorModel(
                    ColorSpace.getInstance(ColorSpace.CS_sRGB),
                    new int[] {8, 8, 8, 8},
                    true,
                    false,
                    ComponentColorModel.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE
            );

    private static final  ColorModel glColorModel =
            new ComponentColorModel(ColorSpace.getInstance(
                    ColorSpace.CS_sRGB),
                    new int[] {8, 8, 8, 0},
                    false,
                    false,
                    ComponentColorModel.OPAQUE,
                    DataBuffer.TYPE_BYTE
            );

    public static ByteBuffer toByteBuffer(BufferedImage image) {
        return toByteBuffer(image, Dimension.fromBufferedImage(image));
    }

    public static ByteBuffer toByteBuffer(BufferedImage image, Dimension dimension) {
        return toByteBuffer(image, dimension, PaddingMode.CENTER);
    }

    public static ByteBuffer toByteBuffer(BufferedImage image, Dimension dimension, PaddingMode mode) {
        ByteBuffer buffer = null;
        BufferedImage texture;
        WritableRaster raster;

        boolean scale = true;
        if (image.getWidth() == dimension.getWidth() && image.getHeight() == dimension.getHeight()) {
            scale = false;
        }

        Dimension textureD;
        if (scale) {
            textureD = dimension.factor2();
        } else {
            textureD = Dimension.fromBufferedImage(image).factor2();
        }

        boolean alpha = image.getColorModel().hasAlpha();

        BufferedImage texI;

        if (alpha) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, textureD.getWidth(), textureD.getHeight(), 4, null);
            texI = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, textureD.getWidth(), textureD.getHeight(), 3, null);
            texI = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        Graphics2D g = (Graphics2D) texI.getGraphics();

        if (alpha) {
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0, 0, textureD.getWidth(), textureD.getHeight());
        }

        if (scale) {
            Dimension drawScale = fit(Dimension.fromBufferedImage(image), textureD);

            Vector2f drawPosition = getForPadding(textureD, drawScale, mode);

            g.drawImage(image, (int) drawPosition.getX(), (int) drawPosition.getY(), drawScale.getWidth(), drawScale.getHeight(), null);
        } else {
            g.drawImage(image, 0, 0, null);
        }

        byte[] data = ((DataBufferByte) texI.getRaster().getDataBuffer()).getData();

        buffer = ByteBuffer.allocateDirect(data.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(data, 0, data.length);
        buffer.flip();
        g.dispose();


        return buffer;
    }

    protected static Dimension fit(final Dimension image, final Dimension bounds) {

        // If no scaling is necessary, just use the orginal bounds
        // as "back up solution".
        Dimension fit = new Dimension(image);

        // Scale width?
        if (image.getWidth() > bounds.getWidth()) {
            fit.setWidth(bounds.getWidth());
            // Scale height by same factor (preserv aspect ratio)
            fit.setHeight((fit.getWidth() * image.getHeight()) / image.getWidth());
        }


        // If height is still not within bounds, scale it down even more
        if (image.getHeight() > bounds.getHeight()) {
            fit.setHeight(bounds.getHeight());
            // ... while preserving aspect ratio. Again...
            fit.setWidth((fit.getHeight() * image.getWidth()) / image.getHeight());
        }


        return fit;

    }

    protected static Vector2f getForPadding(Dimension drawSpace, Dimension size, PaddingMode mode) {
        Vector2f drawPosition = new Vector2f(0, 0);

        switch (mode) {
            case CENTER:
                drawPosition.set(drawSpace.getX() / 2, drawPosition.getY() / 2);
                break;
            case V_CENTER: /* Falls through */
            case LEFT:
                drawPosition.setY(drawSpace.getY() / 2);
                break;
            case H_CENTER:
                drawPosition.setX(drawSpace.getX() / 2);
                break;
            case RIGHT:
                drawPosition.setY(drawSpace.getY() / 2);
                drawPosition.setX(drawSpace.getX() - size.getX());
                break;

            default:
                throw new IllegalArgumentException("PaddingMode not supported!");
        }

        return drawPosition;
    }


}
