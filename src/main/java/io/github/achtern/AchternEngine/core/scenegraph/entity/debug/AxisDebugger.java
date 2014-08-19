package io.github.achtern.AchternEngine.core.scenegraph.entity.debug;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.generator.ImageGenerator;
import io.github.achtern.AchternEngine.core.rendering.mesh.Arrow;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;

public class AxisDebugger extends Node {

    /**
     * Create a new AxisDebugger.
     *
     * @param name The name of the node/axisdebugger
     */
    public AxisDebugger(String name) {
        super(name);

        pushFigure(new Arrow(), Color.RED, "X-Axis");
        pushFigure(new Arrow(), Color.GREEN, "Y-Axis");
        pushFigure(new Arrow(), Color.BLUE, "Z-Axis");
    }

    /**
     * Creates an AxisDebugger.
     */
    public AxisDebugger() {
        this("AxisDebugger");
    }

    private void pushFigure(Mesh mesh, Color color, String name) {

        Figure f = new Figure("Axis", mesh);
        Material m = new Material();

        m.addTexture("diffuse", new Texture(ImageGenerator.bytesFromColor(color)));

        f.setMaterial(m);

        Node n = new Node("AxisDebugger/" + name);

        n.add(f);
        add(n);

        // Rotate, so they are laying on the corresponding axis
        if (name.equals("Z-Axis")) {
            n.getTransform().rotate(Transform.Z_AXIS, -(float) Math.toRadians(90));
        }
        // Arrow Mesh is oriented on Y already.
        else if (name.equals("X-Axis")) {
            n.getTransform().rotate(Transform.X_AXIS, (float) Math.toRadians(90));
        }

    }
}
