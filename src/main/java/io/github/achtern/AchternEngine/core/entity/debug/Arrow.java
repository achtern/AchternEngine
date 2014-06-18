package io.github.achtern.AchternEngine.core.entity.debug;

import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.drawing.WireframeDraw;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Arrow extends Mesh {

    public static final Logger LOGGER = LoggerFactory.getLogger(Arrow.class);

//    private static final float[] VERTICES = new float[]{
//            0, 0, 0,
//            0, 0, 1,
//            0.05f, 0, 0.9f,
//            -0.05f, 0, 0.9f,
//            0, 0.05f, 0.9f,
//            0, -0.05f, 0.9f
//    };

    public Arrow() {
        super();
        MeshData data;
        try {
            data = ResourceLoader.getMesh("arrow.obj").getData();
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Arrow OBJ Model.", e);
            data = null;
        }

        this.data = data;
    }

    @Override
    public void draw(DrawStrategy drawStrategy) {
        if (!(drawStrategy instanceof WireframeDraw)) {
            drawStrategy = DrawStrategyFactory.get("wireframe");
        }

        super.draw(drawStrategy);
    }
}
