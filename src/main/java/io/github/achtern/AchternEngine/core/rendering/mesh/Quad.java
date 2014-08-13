package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

/**
 * A simple Quad Mesh
 */
public class Quad extends Mesh {

    protected float width;
    protected float height;

    /**
     * Initializes a Quad with 1x1
     */
    public Quad() {
        this(1, 1, false);
    }

    /**
     * Initializes a Quad with the given dimensions
     * @param size Width and height
     */
    public Quad(Dimension size) {
        this(size, false);
    }

    /**
     * Initializes a Quad with the given dimensions,
     * if flip is set to true, the texture coordinates
     * will get flipped
     * @param size Width and height
     * @param flip Whether to flip texture coordinates
     */
    public Quad(Dimension size, boolean flip) {
        this(size.getWidth(), size.getHeight(), flip);
    }

    /**
     * Initializes a Quad with the given dimensions
     * @param width Width
     * @param height Height
     */
    public Quad(float width, float height) {
        this(width, height, false);
    }

    /**
     * Initializes a Quad with the given dimensions
     * if flip is set to true, the texture coordinates
     * will get flipped
     * @param width Width
     * @param height Height
     * @param flip Whether to flip texture coordinates
     */
    public Quad(float width, float height, boolean flip) {
        this.width = width;
        this.height = height;
        generate(flip);
    }

    /**
     * Generates the data
     * @param flip Whether to flip texture coordinates
     */
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
