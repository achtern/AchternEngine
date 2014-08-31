/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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
