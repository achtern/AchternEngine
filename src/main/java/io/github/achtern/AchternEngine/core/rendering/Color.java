package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;

public class Color extends Vector4f {

    public static final Color BLACK = new Color(0, 0, 0);

    public static final Color WHITE = new Color(1, 1, 1);

    public static final Color RED = new Color(1, 0, 0);

    public static final Color GREEN = new Color(0, 1, 0);

    public static final Color BLUE = new Color(0, 0, 1);

    public static final Color YELLOW = new Color(1, 1, 0);

    public static final Color MAGENTA = new Color(1, 0, 1);

    public static final Color TURQUOISE = new Color(1, 0, 1);

    public static final Color ORANGE = new Color(1, 0.5f, 0);

    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(Vector4f rgb) {
        super(rgb);
    }

    public Color(Vector3f rgb, float a) {
        super(rgb, a);
    }

    /**
     * Returns the raw RGB values, ignoring the alpha
     * @return RBG components
     */
    public Vector3f getRGB() {
        return getColor().div(getAlpha());
    }

    /**
     * Returns the true color, by multiplying the RGB components by alpha
     * @return The true color (RGB * A)
     */
    public Vector3f getColor() {
        return new Vector3f(getRed(), getGreen(), getBlue()).mul(getAlpha());
    }

    public float getRed() {
        return getX();
    }

    public void setRed(float red) {
        setX(red);
    }

    public float getGreen() {
        return getY();
    }

    public void setGreen(float green) {
        setY(green);
    }

    public float getBlue() {
        return getZ();
    }

    public void setBlue(float blue) {
        setZ(blue);
    }

    public float getAlpha() {
        return getW();
    }

    public void setAlpha(float alpha) {
        setW(alpha);
    }
}
