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

package org.achtern.AchternEngine.core.scenegraph.entity;

import org.achtern.AchternEngine.core.CoreEngine;
import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.scenegraph.Updatable;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.scenegraph.Node;

/**
 * An Entity is the base of all things which live
 * and do stuff in the scenegraph.
 * The need a parent ({@link org.achtern.AchternEngine.core.scenegraph.Node}) to
 * work properly.
 */
public interface Entity extends Updatable {

    /**
     * Called on render, draw stuff here
     * @param renderEngine The active renderEngine (caller)
     */
    public void render(RenderEngine renderEngine);

    /**
     * Should return the current transform of the Entity
     * @return Current transform
     */
    public Transform getTransform();

    /**
     * Set the parent node
     * @param parent The new parent
     */
    public void setParent(Node parent);

    /**
     * Set the CoreEngine
     * @param engine The new coreengine
     */
    public void setEngine(CoreEngine engine);

    /**
     * Called when an Entity is about to get removed from the active scenegraph
     */
    public void removed();

    /**
     * Called when an Entity gets attached to an scenegraph
     */
    public void attached();

    /**
     * Should return the name of the Entits
     * @return The name
     */
    public String getName();

    /**
     * Returns a Node containing this Entity.
     * The Name should equal the name of this Entity.
     * @return Node with this Entity
     */
    public Node boxed();

}
