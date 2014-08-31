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

import java.util.ArrayList;
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


    public DepthFirstWalker() {
        visitors = new ArrayList<Visitor>();
    }

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
        }

        for (Node n : node.getChildren().values()) {
            scan(n);
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
