package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Point;

public class PointLight extends BaseLight {

    private static final int COLOR_DEPTH = 256;

    private Attenuation attenuation;
    private float range;

    public PointLight(Vector3f color, float intensity, Attenuation attenuation) {
        super(color, intensity);
        this.attenuation = attenuation;

        float e = attenuation.getExponent();
        float l = attenuation.getLinear();
        float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * getColor().getColor().max();

        this.range = (float) (-l + Math.sqrt(l * l - 4 * e * c)) / (2 * e);

        setShader(Point.getInstance());
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
