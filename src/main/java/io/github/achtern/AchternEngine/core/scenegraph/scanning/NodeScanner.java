package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;

/**
 * A Node Scanner is able to scan a node.
 * Whatever this means?!
 * No just kidding.
 * To scan a Node can mean everything.
 * From scanning all children and/or entities
 * or using reflection to do whatever.
 */
public interface NodeScanner {

    /**
     * Scans the given Node
     * @param node Node to scan
     */
    public void scan(Node node);

}
