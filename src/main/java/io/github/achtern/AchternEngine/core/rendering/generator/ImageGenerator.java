package io.github.achtern.AchternEngine.core.rendering.generator;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Color;

import java.awt.*;
import java.awt.image.BufferedImage;

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

}
