package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;

import java.util.List;

/**
 * DepthFirstTraversal Implementation of the
 * SceneGraphWalker
 */
public class DepthFirstWalker implements SceneGraphWalker {

    /**
     * List of all visitors
     */
    protected List<Visitor> visitors;


    /**
     * Scans the given Node
     * This should scan all child nodes.
     * DepthFirst in this case
     * @param node Node to scan
     */
    @Override
    public void scan(Node node) {
        for (Visitor v : visitors) {
            v.on(node);

            for (Node n : node.getChildren().values()) {
                scan(node);
            }
        }
    }

    /**
     * Add a visitor, which should be
     * called during the search.
     *
     * @param visitor Visitor
     */
    @Override
    public void addVisitor(Visitor visitor) {
        visitors.add(visitor);
    }

    /**
     * Remove all visitors from the SceneGraphWalker
     */
    @Override
    public void clearVisitors() {
        visitors.clear();
    }

    /**
     * Returns all visitors
     *
     * @return List of all visitors
     */
    @Override
    public List<Visitor> getVisitors() {
        return visitors;
    }
}
