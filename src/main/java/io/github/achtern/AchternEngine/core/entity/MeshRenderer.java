package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public class MeshRenderer extends QuickEntity {

    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this("Untitled Mesh", mesh, material);
    }

    public MeshRenderer(String name, Mesh mesh, Material material) {
        super(name);
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void render(Shader shader, RenderEngine renderEngine) {
        shader.bind();
        shader.updateUniforms(getTransform(), material, renderEngine);
        mesh.draw(renderEngine.getDrawStrategy());
    }
}
