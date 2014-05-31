package io.github.achtern.AchternEngine.core.rendering.light;

import io.github.achtern.AchternEngine.core.math.Vector3f;

/**
 * This is just a wrapper class for the
 * GLSL struct in the phong fragment shader.
 */
public class Attenuation {

    private float constant;
    private float linear;
    private float exponent;

    public Attenuation(Vector3f attenuation) {
        this(attenuation.getX(), attenuation.getY(), attenuation.getZ());
    }

    public Attenuation(float constant, float linear, float exponent) {
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
