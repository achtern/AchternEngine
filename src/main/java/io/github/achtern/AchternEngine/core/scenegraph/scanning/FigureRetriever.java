package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.bounding.BoundingBox;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;

import java.util.ArrayList;
import java.util.List;

public class FigureRetriever extends EntityRetriever {

    protected List<Figure> figures;
    protected List<Mesh> meshes;
    protected List<Material> materials;
    protected BoundingBox bb;

    @Override
    public void scan(Node node) {
        super.scan(node);
        figures = getAll(Figure.class);
        meshes = null;
        meshes = getMeshes();
        bb = getCompleteBoundingBox();
    }

    public List<Figure> get() {
        return figures;
    }

    public List<Mesh> getMeshes() {
        if (meshes != null) return meshes;

        meshes = new ArrayList<Mesh>(get().size());

        for (Figure f : get()) {
            meshes.add(f.getMesh());
        }

        return meshes;
    }

    public List<Material> getMaterials() {
        if (materials != null) return materials;

        materials = new ArrayList<Material>(get().size());

        for (Figure f : get()) {
            materials.add(f.getMaterial());
        }

        return materials;
    }

    public int getTotalVertexCount() {
        List<Mesh> meshList = getMeshes();
        int count = 0;

        for (Mesh m : meshList) {
            count += m.getData().getVertexCount();
        }

        return count;
    }

    public BoundingBox getCompleteBoundingBox() {
        if (bb != null) return bb;

        for (Mesh m : getMeshes()) {
            if (bb == null) {
                bb = m.getBoundingBox();
                continue;
            }

            bb.merge(m.getBoundingBox());
        }

        return bb;


    }

}
