/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

/**
 * A Figure represents a renderable {@link Mesh}.
 * Included in this figure is a mesh, material and a DrawStrategy.
 */
public class Figure extends QuickEntity {

    protected Mesh mesh;
    protected Material material;

    /**
     * Override the renderEngine supplied drawStrategy
     * if not null
     */
    protected DrawStrategy drawStrategy;

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
     * @see QuickEntity#render(RenderEngine)
     */
    @Override
    public void render(RenderEngine renderEngine) {

        Matrix4f worldMat = getTransform().getTransformation();
        Matrix4f projection = renderEngine.getMainCamera().getViewProjection().mul(worldMat);

        renderEngine.getActiveRenderPass().getShader().updateUniforms(getTransform(), getMaterial(), renderEngine, projection);

        DrawStrategy ds = getDrawStrategy();
        if (ds == null) {
            ds = renderEngine.getDrawStrategy();
        }

        if (getMaterial().isWireframe()) {
            ds = DrawStrategyFactory.get("wireframe");
        }

        ds.draw(renderEngine.getDataBinder(), this.mesh);
    }

    /**
     * Returns a Node with 1 {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Entity} (this Figure)
     * @return Node with this Figure
     */
    public Node boxed() {
        return new Node(this.getName()).add(this);
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
