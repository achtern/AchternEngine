package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Spot;

public class SpotLight extends PointLight {
    private float cutoff;

    public SpotLight(Vector3f color, float intensity, Attenuation attenuation, float cutoff) {
        super(color, intensity, attenuation);
        this.cutoff = cutoff;

        setShader(Spot.getInstance());
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRotation().getForward();
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
