package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

import java.util.List;

/**
 * A SceneGraphRetriever should be able to filter out
 * Entities from the SceneGraph.
 * @param <T>
 */
public interface SceneGraphRetriever<T extends Entity> extends NodeScanner {

    /**
     * Return all Entities, which life in the scanned node
     * and it's children
     * @return All Entities of the Type T
     */
    public List<T> getAll();

    /**
     * Return all Entities, which life in the scanned node
     * and it's children. In addition to that you can apply
     * another type filter
     * @param filter Entity Filter
     * @param <E> Filtered type
     * @return List of filtered entities
     */
    public <E extends Entity> List<E> getAll(Class<E> filter);

    /**
     * Returns all nodes found while scanning.
     * @return List of all nodes
     */
    public List<Node> getNodes();

    /**
     * Should return true if at least ONE Entity has been found.
     * @return Whether the Entity type is contained by the scanned node.
     */
    public boolean contained();

}
