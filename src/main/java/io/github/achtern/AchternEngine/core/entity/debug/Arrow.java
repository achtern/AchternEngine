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


//    public Arrow(Vector3f arrow) {
//
//        float length = arrow.length();
//        Vector3f direction = arrow.normalized();
//
//        Quaternion q = new Quaternion(new Matrix4f().initRotation(direction, Transform.Y_AXIS));
//        q.faceAt(direction, Transform.Y_AXIS);
//        q.normalize();
//
//        Vector3f tmp = new Vector3f(0, 0, 0);
//
//        float[] uVertices = new float[VERTICES.length];
//
//        for (int i = 0; i < VERTICES.length; i += 3) {
//            Vector3f v = tmp.set(VERTICES[i], VERTICES[i + 1], VERTICES[i + 2]);
//
//            v.multLocal(length);
//
//            v = q.mult(v);
//
//            uVertices[i] = v.getX();
//            uVertices[i + 1] = v.getY();
//            uVertices[i + 2] = v.getZ();
//        }
//
//        setVertices(Vertex.toArray(uVertices), new int[] {0, 1, 1, 2, 1, 3, 1, 4, 1, 5}, false);
//
//    }

    @Override
    public void draw(DrawStrategy drawStrategy) {
        if (!(drawStrategy instanceof WireframeDraw)) {
            drawStrategy = DrawStrategyFactory.get("wireframe");
        }

        super.draw(drawStrategy);
    }
}
