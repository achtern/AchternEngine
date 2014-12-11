/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package org.achtern.AchternEngine.core.scenegraph.entity;

import org.achtern.AchternEngine.core.rendering.Material;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.scenegraph.Node;
import lombok.Getter;
import lombok.Setter;

/**
 * A Figure represents a renderable {@link Mesh}.
 * Included in this figure is a mesh, material and a DrawStrategy.
 */
public class Figure extends QuickEntity {

    @Getter @Setter protected Mesh mesh;
    @Getter @Setter protected Material material;

    /**
     * Override the renderEngine supplied drawStrategy
     * if not null
     */
    @Getter @Setter protected DrawStrategy drawStrategy;

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



        renderEngine.getActiveRenderPass().getShader().updateUniforms(renderEngine, this);

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
     * Returns a Node with 1 {@link org.achtern.AchternEngine.core.scenegraph.entity.Entity} (this Figure)
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
}
