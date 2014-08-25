package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class SimpleArrow extends Mesh {

    protected static final Vector3f[] positions = new Vector3f[] {
            /*0*/new Vector3f(0, 0, 0), // BOTTOM
            /*1*/new Vector3f(0, 1, 0), // TOP
            /*2*/new Vector3f(0.5f, 0.5f, 0), // X-side-down
            /*3*/new Vector3f(-0.5f, 0.5f, 0), // neg X-side-down
            /*4*/new Vector3f(0, 0.5f, 0.5f), // Z-side-down
            /*5*/new Vector3f(0, 0.5f, -0.5f) // neg Z-side-down
    };

    public SimpleArrow(int length) {

        Vertex[] vertices = new Vertex[positions.length];
        for (int i = 0; i < positions.length; i++) {
            vertices[i] = new Vertex(positions[i].mul(length));
        }

        setVertices(vertices, new int[] {
                0, 1, 2, 1, 3, 1, 4, 1, 5, 1
        }, false);

        setMode(MeshData.Mode.LINES);


    }

}
