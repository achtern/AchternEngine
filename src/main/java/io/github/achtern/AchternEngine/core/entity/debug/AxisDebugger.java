package io.github.achtern.AchternEngine.core.entity.debug;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.entity.Figure;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;

import java.io.IOException;

public class AxisDebugger extends Figure {

    public Figure[] axes = new Figure[3];

    public AxisDebugger() {
        super("AxisDebugger");
    }

    public AxisDebugger(String name) {
        super(name);
        init();
    }

    private void init() {
        Arrow arrow;


        arrow = new Arrow();
        pushFigure(arrow, Color.RED, 0);

        arrow = new Arrow();
        pushFigure(arrow, Color.GREEN, 1);


        arrow = new Arrow();
        pushFigure(arrow, Color.BLUE, 2);
    }

    @Override
    public void render(Shader shader, RenderEngine renderEngine) {
        System.out.println("Called");
        for (int i = 0; i < axes.length; i++) {
            Figure f = axes[i];
            f.setParent(getParent());

            if (i == 0) f.getTransform().rotate(Transform.X_AXIS, 90);
            else if (i == 1) f.getTransform().rotate(Transform.Y_AXIS, 90);
            else if (i == 2) f.getTransform().rotate(Transform.Z_AXIS, 90);

            f.render(shader, renderEngine);
        }
    }

    private void pushFigure(Mesh mesh, Color color, int i) {

        Figure f = new Figure("Axis", mesh);
        Material m = new Material();
        try {
            m.addTexture("diffuse", ResourceLoader.getTexture("plane.png"));
        } catch (IOException e) {
            // ignore
        }

        m.addFloat("specularIntensity", 0.1f);
        m.addFloat("specularPower", 55);

        f.setMaterial(m);

        axes[i] = f;
    }
}
