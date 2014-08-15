package io.github.achtern.AchternEngine.core.scenegraph.entity.debug;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Quaternion;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
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

        pushFigure(new Arrow(), Color.RED, 0);
        pushFigure(new Arrow(), Color.GREEN, 1);
        pushFigure(new Arrow(), Color.BLUE, 2);
    }

    /**
     * Creates an AxisDebugger.
     */
    public AxisDebugger() {
        this("AxisDebugger");
    }

    @Override
    public void render(RenderEngine renderEngine) {
        super.render(renderEngine);
    }

    private void pushFigure(Mesh mesh, Color color, int i) {

        Figure f = new Figure("Axis", mesh);
        Material m = new Material();

        m.addTexture("diffuse", new Texture(ImageGenerator.bytesFromColor(color)));

        f.setMaterial(m);

        String name = "";

        if (i == 0) name = "X-Axis";
        else if (i == 1) name = "Y-Axis";
        else if (i == 2) name = "Z-Axis";

        Node n = new Node("AxisDebugger/" + name);

        n.add(f);

        add(n);

        // Rotate, so they are laying on the corresponding axis
        if (i == 0)      n.getTransform().setRotation(new Quaternion(Transform.Z_AXIS, (float) Math.toRadians(90)));
        else if (i == 1) n.getTransform().setRotation(new Quaternion(Transform.Y_AXIS, (float) Math.toRadians(90)));
        else if (i == 2) n.getTransform().setRotation(new Quaternion(Transform.X_AXIS, (float) Math.toRadians(90)));

    }
}
