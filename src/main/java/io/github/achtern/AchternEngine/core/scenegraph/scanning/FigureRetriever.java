package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.bounding.BoundingBox;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;

import java.util.ArrayList;
import java.util.List;

/**
 * A Figure Retriever retrives all Figures from
 * the scenegraph and calculates BoundingBoxes, Vertex Counts
 * and gathers meshes and materials!
 */
public class FigureRetriever extends EntityRetriever {

    /**
     * List of found figures
     */
    protected List<Figure> figures;
    /**
     * Retrieved meshes.
     */
    protected List<Mesh> meshes;
    /**
     * Retrieved materials.
     */
    protected List<Material> materials;
    /**
     * Complete BoundingBox
     */
    protected BoundingBox bb;


    /**
     * Performs the search.
     * Filters out Figures and collects meshes/materials
     * and calculates the BB
     * @param node Node to scan
     * @see EntityRetriever#getAll(Node, boolean)
     */
    @Override
    public void scan(Node node) {
        super.scan(node);
        figures = getAll(Figure.class);
        meshes = null;
        meshes = getMeshes();
        materials = null;
        materials = getMaterials();
        bb = getCompleteBoundingBox();
    }

    /**
     * Getter for all Figures
     * @return figures
     */
    public List<Figure> get() {
        return figures;
    }

    /**
     * Returns retrieved meshes
     * or if not retrieved yet, does the retrieving.
     * @return meshes
     */
    public List<Mesh> getMeshes() {
        if (meshes != null) return meshes;

        meshes = new ArrayList<Mesh>(get().size());

        for (Figure f : get()) {
            meshes.add(f.getMesh());
        }

        return meshes;
    }

    /**
     * Returns retrieved materials
     * or if not retrieved yet, does the retrieving.
     * @return materials
     */
    public List<Material> getMaterials() {
        if (materials != null) return materials;

        materials = new ArrayList<Material>(get().size());

        for (Figure f : get()) {
            materials.add(f.getMaterial());
        }

        return materials;
    }

    /**
     * Returns the total Vertex Count of
     * all retrieved meshes
     * @return Vertex Count
     */
    public int getTotalVertexCount() {
        List<Mesh> meshList = getMeshes();
        int count = 0;

        for (Mesh m : meshList) {
            count += m.getData().getVertexCount();
        }

        return count;
    }

    /**
     * Merges all BoundingBoxes of the meshes.
     * TODO: Include Transforms.
     * @return Complete BoundingBox
     */
    public BoundingBox getCompleteBoundingBox() {
        if (bb != null) return bb;

        for (Mesh m : getMeshes()) {
            if (bb == null) {
                bb = new BoundingBox(m.getBoundingBox());
                continue;
            }

            bb.merge(m.getBoundingBox());
        }

        return bb;


    }

}
