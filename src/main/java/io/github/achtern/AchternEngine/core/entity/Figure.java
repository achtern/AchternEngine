package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public class Figure extends QuickEntity {

    private Mesh mesh;
    private Material material;
    private DrawStrategy drawStrategy;

    public Figure(String name) {
        this(name, null, null);
    }

    public Figure(String name, Mesh mesh) {
        this(name, mesh, null);
    }

    public Figure(String name, Mesh mesh, Material material) {
        super(name);

        if (mesh == null) throw new IllegalArgumentException("Mesh must not be null");

        setMesh(mesh);
        setMaterial(material);
    }

    public Figure(Mesh mesh) {
        this(mesh, null);
    }

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
