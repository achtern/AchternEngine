package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class WireBox extends Mesh {


    public WireBox() {
        this(Vector3f.ONE.get());
    }

    public WireBox(Vector3f extents) {

        setMode(MeshData.Mode.LINES);

        Vertex[] vertices = new Vertex[] {
                new Vertex(extents.mul(new Vector3f(-1, -1, 1))),
                new Vertex(extents.mul(new Vector3f(1, -1, 1))),
                new Vertex(extents.mul(new Vector3f(1, 1, 1))),
                new Vertex(extents.mul(new Vector3f(-1, 1, 1))),

                new Vertex(extents.mul(new Vector3f(-1, -1, -1))),
                new Vertex(extents.mul(new Vector3f(1, -1, -1))),
                new Vertex(extents.mul(new Vector3f(1, 1, -1))),
                new Vertex(extents.mul(new Vector3f(-1, 1, -1))),
        };

        int[] indices = new int[] {
                0, 1, 1, 2,
                2, 3, 3, 0,

                4, 5, 5, 6,
                6, 7, 7, 4,

                0, 4,  1, 5,
                2, 6,  3, 7,
        };

        setVertices(vertices, indices, false);

    }
}
