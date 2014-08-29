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
     * Scans the given Node
     * This should scan all child nodes.
     * @param node Node to scan
     */
    @Override
    void scan(Node node);

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
