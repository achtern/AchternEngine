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

package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic Implementation of the SceneGraphRetriever, valid for all Entities.
 */
public abstract class EntityRetriever implements SceneGraphRetriever<Entity> {

    /**
     * Nodes found while scanning
     */
    protected List<Node> nodes;
    /**
     * Entites found while scanning
     */
    protected List<Entity> entities;

    public EntityRetriever() {
        this.nodes = new ArrayList<Node>();
        this.entities = new ArrayList<Entity>();
    }

    /**
     * Performs the search.
     * @see EntityRetriever#getAll(Node, boolean)
     * @param node Node to scan
     */
    @Override
    public void scan(Node node) {
        // Reset the data
        this.nodes.clear();
        this.entities.clear();

        this.nodes.add(node);
        this.entities.addAll(getAll(node));
    }

    /**
     * Call #scan(Node) first!
     * Returns all Entities
     * @return flattend List of all Entities
     */
    @Override
    public List<Entity> getAll() {
        return entities;
    }

    /**
     * Call #scan(Node) first!
     * Returns all Nodes
     * @return flattend List of all Nodes
     */
    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Should return true if at least ONE Entity has been found.
     *
     * @return Whether the Entity type is contained by the scanned node.
     */
    @Override
    public boolean contained() {
        // Since this scans for entities only
        // we just need to check if there are any.
        // Easy.
        return getAll().size() > 0;
    }

    /**
     * Return all Entities, which life in the scanned node
     * and it's children. In addition to that you can apply
     * another type filter
     *
     * @param filter Entity Filter
     * @return List of filtered entities
     */
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
        List<Node> children = new ArrayList<Node>(node.getChildren().values());

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
