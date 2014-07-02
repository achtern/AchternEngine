package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityRetriever implements SceneGraphRetriever<Entity> {

    protected List<Node> nodes;
    protected List<Entity> entities;

    @Override
    public void scan(Node node) {
        this.nodes.add(node);

        this.entities.addAll(getAll(node));
    }

    @Override
    public List<Entity> getAll() {
        return entities;
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public boolean contained() {
        // Since this scans for entities only
        // we just need to check if there are any.
        // Easy.
        return getAll().size() > 0;
    }

    @Override
    public <E extends Entity> List<E> getAll(Class<E> filter) {
        List<E> filtered = new ArrayList<E>();

        for (Entity e : getAll()) {
            if (filter.isInstance(e)) {
                filtered.add(filter.cast(e));
            }
        }

        return filtered;
    }

    /**
     * This method flattens a scenegraph,
     * NOTE: this method adds all encountered nodes
     * to the object's nodes list.
     * @param node The node to flatten
     * @return flattened entity list
     */
    protected List<Entity> getAll(Node node) {
        return getAll(node, true);
    }

    /**
     * This method flattens a scenegraph
     * @param node The node to flatten
     * @param addNodes Whether to add encountered nodes to the object's node list
     * @return flattened entity list
     */
    protected List<Entity> getAll(Node node, boolean addNodes) {
        List<Entity> flattenedList = new ArrayList<Entity>();

        // Add all "primary" entities
        flattenedList.addAll(node.getEntities());

        // Get all child nodes
        List<Node> children = (List<Node>) node.getChildren().values();

        // Retrieve all entites from the children
        for (Node n : children) {
            // Also add this node to the nodes list.
            if (addNodes) {
                this.nodes.add(n);
            }

            // Recursion <3
            flattenedList.addAll(getAll(n, addNodes));
        }


        return flattenedList;
    }
}
