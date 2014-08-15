package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Ambient;

public class AmbientLight extends Light {

    private Color color;

    public AmbientLight(Color color) {
        this.color = color;

        setShader(Ambient.getInstance());
    }

    public AmbientLight(float r, float g, float b) {
        this(new Color(r, g, b));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
