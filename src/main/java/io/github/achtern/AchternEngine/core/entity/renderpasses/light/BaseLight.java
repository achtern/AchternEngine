package io.github.achtern.AchternEngine.core.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;

public class BaseLight extends Light {

    private Color color;
    private float intensity;

    public BaseLight(Vector3f color, float intensity) {
        this(new Color(color, 1), intensity);
    }

    public BaseLight(Color color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(Vector3f color) {
        this.setColor(new Color(color, 1));
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
