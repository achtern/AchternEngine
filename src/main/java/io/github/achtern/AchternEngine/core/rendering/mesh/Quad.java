package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class Quad extends Mesh {

    protected float width;
    protected float height;

    public Quad(Dimension size) {
        this(size.getWidth(), size.getHeight());
    }

    public Quad(float width, float height) {
        this.width = width;
        this.height = height;
        generate();
    }

    protected void generate() {

        Vertex[] vertices = {
                new Vertex(new Vector3f(0, height, 0), new Vector2f(0, 0), new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(width, height, 0),new Vector2f(1, 0), new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(width, 0, 0), new Vector2f(1, 1), new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 1), new Vector3f(0, 0, 1)),
        };

        setVertices(vertices, new int[] { 0, 1, 2, 0, 2, 3 }, false);

    }

}
