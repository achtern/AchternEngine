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
import org.achtern.AchternEngine.core.EngineHolder;
import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.scenegraph.Node;
import lombok.Data;

/**
 * A helper class to create an {@link org.achtern.AchternEngine.core.scenegraph.entity.Entity} easily.
 * Implements all methods in order to override only "needed" methods and handels the following:
 * - Storing the parent node
 * - Storing the coreengine
 * - Storing a name (and setting it)
 */
@Data
public abstract class QuickEntity implements Entity, EngineHolder<CoreEngine> {

    public static final String NAME_UNTITLED_ENTITY = "Untitled Entity";

    protected Node parent;
    protected CoreEngine engine;
    protected String name;

    protected boolean destroy = false;

    /**
     * Create an "Untitled Entity"
     */
    public QuickEntity() {
        this(NAME_UNTITLED_ENTITY);
    }

    /**
     * Create a entity with a specifc name
     * @param name The name
     */
    public QuickEntity(String name) {
        setName(name);
    }

    /**
     * @see Entity#update(float)
     */
    @Override
    public void update(float delta) {

    }

    /**
     * @see Entity#render(org.achtern.AchternEngine.core.rendering.RenderEngine)
     */
    @Override
    public void render(RenderEngine renderEngine) {

    }

    /**
     * Sets the parent to null.
     * @see Entity#removed()
     */
    @Override
    public void removed() {
        this.parent = null;
    }

    /**
     * @see Entity#attached() ()
     */
    @Override
    public void attached() {
    }

    /**
     * Marks this Entity to be no longer needed.
     *  This is just a hint for the various engines operating
     *  with this Entity.
     *
     *
     * The {@link org.achtern.AchternEngine.core.rendering.RenderEngine} for example could decide to delete the
     *  resources for a given Entity from the graphics card's memory, etc.
     */
    @Override
    public void destroy() {
        this.destroy(true);
    }

    /**
     * Set whether or not the resources assoc. with this Entity can be destroyed.
     *
     * @param shouldbeDestroyed whether or not to destroy this Entity
     * @see #destroy()
     */
    @Override
    public void destroy(boolean shouldbeDestroyed) {
        this.destroy = shouldbeDestroyed;
    }

    /**
     * If true, the Entity is no longer needed.
     *
     * @return whether or not to destroy this Entity
     * @see #destroy()
     */
    @Override
    public boolean shouldBeDestroyed() {
        return this.destroy;
    }

    /**
     * Returns a Node containing this Entity.
     * The Name should equal the name of this Entity.
     *
     * @return Node with this Entity
     */
    @Override
    public Node boxed() {
        String name = this.getName();
        if (name == null || name.equals(NAME_UNTITLED_ENTITY)) {
            name = this.getClass().getSimpleName();
        }
        return new Node(name).add(this);
    }


    /**
     * @see Entity#getTransform()
     */
    @Override
    public Transform getTransform() {
        return this.parent.getTransform();
    }
}
