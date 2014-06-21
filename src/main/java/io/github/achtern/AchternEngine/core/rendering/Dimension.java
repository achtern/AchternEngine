package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector2f;

import java.awt.image.BufferedImage;

public class Dimension extends Vector2f {

    public static Dimension fromBufferedImage(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public Dimension(int width, int height) {
        super(width, height);
    }


    public int getHeight() {
        return (int) getY();
    }

    public int getWidth() {
        return (int) getX();
    }
}
