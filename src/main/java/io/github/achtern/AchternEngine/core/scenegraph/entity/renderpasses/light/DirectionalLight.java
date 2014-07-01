package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Directional;

public class DirectionalLight extends BaseLight {

    public DirectionalLight(Color color, float intensity) {
        super(color, intensity);

        setShader(Directional.getInstance());
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRotation().getForward();
    }
}
