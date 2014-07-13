package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

public class Quad extends Mesh {

    protected float width;
    protected float height;

    public Quad() {
        this(1, 1, false);
    }

    public Quad(Dimension size) {
        this(size, false);
    }

    public Quad(Dimension size, boolean flip) {
        this(size.getWidth(), size.getHeight(), flip);
    }

    public Quad(float width, float height) {
        this(width, height, false);
    }

    public Quad(float width, float height, boolean flip) {
        this.width = width;
        this.height = height;
        generate(flip);
    }

    protected void generate(boolean flip) {

        Vector2f[] texCoords;
        if (flip) {
            texCoords = new Vector2f[]{
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
                    new Vector2f(1, 0),
                    new Vector2f(0, 0)
            };
        } else {
            texCoords = new Vector2f[] {
                    new Vector2f(0, 0),
                    new Vector2f(1, 0),
                    new Vector2f(1, 1),
                    new Vector2f(0, 1)
            };
        }

        Vertex[] vertices = {
                new Vertex(new Vector3f(0, height, 0), texCoords[0], new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(width, height, 0), texCoords[1], new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(width, 0, 0), texCoords[2], new Vector3f(0, 0, 1)),
                new Vertex(new Vector3f(0, 0, 0), texCoords[3], new Vector3f(0, 0, 1)),
        };

        setVertices(vertices, new int[] { 0, 1, 2, 0, 2, 3 }, false);

    }

}
