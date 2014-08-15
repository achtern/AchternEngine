package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Wireframe;

public class WireframeDisplay extends QuickRenderPass {

    private Vector3f wirecolor;
    private Vector3f fillcolor;

    public WireframeDisplay(Vector3f wirecolor, Vector3f fillcolor) {
        this.wirecolor = wirecolor;
        this.fillcolor = fillcolor;
        setShader(Wireframe.getInstance());
    }

    public Vector3f getWireColor() {
        return wirecolor;
    }

    public void setWireColor(Vector3f wirecolor) {
        this.wirecolor = wirecolor;
    }

    public Vector3f getFillColor() {
        return fillcolor;
    }

    public void setFillColor(Vector3f fillcolor) {
        this.fillcolor = fillcolor;
    }
}
