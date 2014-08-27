package io.github.achtern.AchternEngine.core.rendering.shadow;

import io.github.achtern.AchternEngine.core.math.Matrix4f;

public class ShadowInfo {

    protected Matrix4f matrix;

    public ShadowInfo(Matrix4f matrix) {
        this.matrix = matrix;
    }


    public Matrix4f getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix4f matrix) {
        this.matrix = matrix;
    }
}
