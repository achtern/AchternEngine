package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;

import java.util.List;

/**
 * A SceneGraphWalker can be used to walk and scan
 * the given scenegraph. The search/scan/walk
 * algorithm is implementation defined!
 */
public interface SceneGraphWalker extends NodeScanner {

    /**
     * A Visitor gets called with every node found
     * in the scenegraph. When the Visitor is getting
     * called is implementation defined by the
     * {@link SceneGraphWalker}
     */
    public interface Visitor {

        /**
         * On a new node.
         * @param node New node
         */
        public void on(Node node);

    }

    /**
     * Add a visitor, which should be
     * called during the search.
     * @param visitor Visitor
     */
    public void addVisitor(Visitor visitor);

    /**
     * Remove all visitors from the SceneGraphWalker
     */
    public void clearVisitors();

    /**
     * Returns all visitors
     * @return List of all visitors
     */
    public List<Visitor> getVisitors();

}
