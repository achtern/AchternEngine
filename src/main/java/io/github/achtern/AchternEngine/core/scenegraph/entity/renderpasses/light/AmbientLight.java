package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Ambient;

public class AmbientLight extends Light {

    private Vector3f color;

    public AmbientLight(Vector3f color) {
        this.color = color;

        setShader(Ambient.getInstance());
    }

    public AmbientLight(float r, float g, float b) {
        this(new Vector3f(r, g, b));
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
