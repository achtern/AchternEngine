package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

/**
 * A Figure represents a renderable {@link Mesh}.
 * Included in this figure is a mesh, material and a DrawStrategy.
 */
public class Figure extends QuickEntity {

    private Mesh mesh;
    private Material material;

    /**
     * Override the renderEngine supplied drawStrategy
     * if not null
     */
    private DrawStrategy drawStrategy;

    /**
     * Create an empty Figure
     * @param name The name of the empty figure
     */
    public Figure(String name) {
        this(name, null);
    }

    /**
     * Create a Figure with a Mesh only (and empty Material).
     * If the mesh is null, it will throw an <code>IllegalArgumentException</code>.
     * @param name The name of the figure
     * @param mesh The mesh
     */
    public Figure(String name, Mesh mesh) {
        this(name, mesh, new Material());
    }

    /**
     * Create a Figure with a Mesh and Material.
     * @param name The name of the figure
     * @param mesh The mesh (not null)
     * @param material The material
     */
    public Figure(String name, Mesh mesh, Material material) {
        super(name);

        setMesh(mesh);
        setMaterial(material);
    }

    /**
     * Creates an untitled Figure with a mesh only (and empty Material).
     * @param mesh The mesh
     */
    public Figure(Mesh mesh) {
        this(mesh, new Material());
    }

    /**
     * Creates an untitled Figure with a mesh and a material
     * @param mesh The mesh
     */
    public Figure(Mesh mesh, Material material) {
        this("Untitled Figure", mesh, material);
    }

    /**
     * @see QuickEntity#render(Shader, RenderEngine)
     */
    @Override
    public void render(Shader shader, RenderEngine renderEngine) {
        // TODO: bind the shader in the RenderEngine!
        shader.bind();

        Matrix4f worldMat = getTransform().getTransformation();
        Matrix4f projection = renderEngine.getMainCamera().getViewProjection().mul(worldMat);

        shader.updateUniforms(getTransform(), getMaterial(), renderEngine, projection);

        DrawStrategy ds = getDrawStrategy();
        if (ds == null) {
            ds = renderEngine.getDrawStrategy();
        }

        if (getMaterial().isWireframe()) {
            ds = DrawStrategyFactory.get("wireframe");
        }

        mesh.draw(ds);
    }

    /**
     * Returns the vertex count of the underlying mesh
     * @return the vertex count
     *
     * @see Mesh#getVertexCount()
     */
    public int getVertexCount() {
        return getMesh().getVertexCount();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public DrawStrategy getDrawStrategy() {
        return drawStrategy;
    }

    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }
}
