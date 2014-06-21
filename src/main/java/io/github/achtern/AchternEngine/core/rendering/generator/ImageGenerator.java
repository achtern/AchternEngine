package io.github.achtern.AchternEngine.core.rendering.generator;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Color;
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

    public static ByteBuffer bytesFromColor(Color color) {
        return bytesFromColor(new Vector2f(1, 1), color);
    }

    public static ByteBuffer bytesFromColor(Vector2f dimensions, Color color) {
        ByteBuffer buffer = UBuffer.createByteBuffer((int) (dimensions.getX() * dimensions.getY() * 4));

        for(int y = 0; y < dimensions.getY(); y++) {
            for(int x = 0; x < dimensions.getX(); x++) {

                int pixel = color.toInt();

                buffer.put((byte)((pixel) & 0xFF)); // R
                buffer.put((byte)((pixel >> 8) & 0xFF)); // G
                buffer.put((byte)((pixel >> 16) & 0xFF)); // B
                buffer.put((byte)((pixel >> 24) & 0xFF)); // Alpha

            }
        }

        buffer.flip();


        return buffer;
    }

}
