package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

/**
 * A Figure represents a renderable {@link Mesh}.
 * Included in this figure is a mesh, material and a DrawStrategy.
 */
public class Figure extends QuickEntity {

    private Mesh mesh;
    private Material material;
    private DrawStrategy drawStrategy;
    private boolean useLights;

    /**
     * Create an empty Figure
     * @param name The name of the empty figure
     */
    public Figure(String name) {
        this(name, null, null);
    }

    /**
     * Create a Figure with a Mesh only.
     * If the mesh is null, it will throw an <code>IllegalArgumentException</code>.
     * @param name The name of the figure
     * @param mesh The mesh
     */
    public Figure(String name, Mesh mesh) {
        this(name, mesh, null);
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
        setUseLights(true);
    }

    /**
     * Creates an untitled Figure with a mesh only.
     * @param mesh The mesh
     */
    public Figure(Mesh mesh) {
        this(mesh, null);
    }

    /**
     * Creates an untitled Figure with a mesh and a material
     * @param mesh The mesh
     */
    public Figure(Mesh mesh, Material material) {
        this("Untitled Figure", mesh, material);
    }

    @Override
    public void render(Shader shader, RenderEngine renderEngine) {
        shader.bind();
        shader.updateUniforms(getTransform(), getMaterial(), renderEngine);

        DrawStrategy ds = getDrawStrategy();
        if (ds == null) {
            ds = renderEngine.getDrawStrategy();
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

    public boolean isUseLights() {
        return useLights;
    }

    public void setUseLights(boolean useLights) {
        this.useLights = useLights;
    }
}
