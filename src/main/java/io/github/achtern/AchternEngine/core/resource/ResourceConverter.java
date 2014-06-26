package io.github.achtern.AchternEngine.core.resource;

import io.github.achtern.AchternEngine.core.rendering.Dimension;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;

public class ResourceConverter {

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
        ByteBuffer buffer = null;
        BufferedImage texture;
        WritableRaster raster;

        //TODO: Make use of dimension parameter!

        int texW = 2;
        int texH = 2;

        // Powers of 2 only!
        while (texW < image.getWidth()) texW *= 2;
        while (texH < image.getHeight()) texH *= 2;

        boolean alpha = image.getColorModel().hasAlpha();

        BufferedImage texI;

        if (alpha) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texW, texH, 4, null);
            texI = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texW, texH, 3, null);
            texI = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        Graphics2D g = (Graphics2D) texI.getGraphics();

        if (alpha) {
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0, 0, texW, texH);
        }

        g.drawImage(image, 0, 0, null);

        byte[] data = ((DataBufferByte) texI.getRaster().getDataBuffer()).getData();

        buffer = ByteBuffer.allocateDirect(data.length);
        buffer.order(ByteOrder.nativeOrder());
        buffer.put(data, 0, data.length);
        buffer.flip();
        g.dispose();


        return buffer;
    }


}
