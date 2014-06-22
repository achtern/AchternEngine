package io.github.achtern.AchternEngine.core.rendering.generator;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.ByteImage;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.contracts.TexturableData;
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

                int pixel = color.toInt();

                buffer.put((byte)((pixel) & 0xFF)); // R
                buffer.put((byte)((pixel >> 8) & 0xFF)); // G
                buffer.put((byte)((pixel >> 16) & 0xFF)); // B
                buffer.put((byte)((pixel >> 24) & 0xFF)); // Alpha

            }
        }

        buffer.flip();


        return new ByteImage(buffer, true, dimension);
    }

}
