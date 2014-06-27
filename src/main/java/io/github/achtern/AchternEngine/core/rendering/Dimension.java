package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector2f;

import java.awt.image.BufferedImage;

public class Dimension extends Vector2f {

    public static Dimension fromBufferedImage(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public Dimension(Dimension copy) {
        this(copy.getWidth(), copy.getHeight());
    }

    public Dimension(int width, int height) {
        super(width, height);
    }

    public Dimension factor2() {
        int w = 2;
        int h = 2;

        while (w < getWidth()) w *= 2;
        while (h < getHeight()) h *= 2;

        return new Dimension(w, h);
    }

    public void setHeight(int height) {
        super.setX((int) height);
    }

    public void setWidth(int width) {
        super.setY((int) width);
    }

    public int getHeight() {
        return (int) getY();
    }

    public int getWidth() {
        return (int) getX();
    }
}
