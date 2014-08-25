package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.shadow.ShadowInfo;

public class BaseLight extends Light {

    private Color color;
    private float intensity;

    private ShadowInfo shadowInfo;

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

    public ShadowInfo getShadowInfo() {
        return shadowInfo;
    }

    public void setShadowInfo(ShadowInfo shadowInfo) {
        this.shadowInfo = shadowInfo;
    }
}
