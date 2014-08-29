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

package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

/**
 * A helper class to create an {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Entity} easily.
 * Implements all methods in order to override only "needed" methods and handels the following:
 * - Storing the parent node
 * - Storing the coreengine
 * - Storing a name (and setting it)
 */
public abstract class QuickEntity implements Entity, EngineHolder<CoreEngine> {

    public static final String NAME_UNTITLED_ENTITY = "Untitled Entity";

    protected Node parent;
    protected CoreEngine engine;
    protected String name;

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
     * @see Entity#render(io.github.achtern.AchternEngine.core.rendering.RenderEngine)
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

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see Entity#getName()
     */
    @Override
    public String getName() {
        return this.name;
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

    /**
     * @see Entity#setParent(io.github.achtern.AchternEngine.core.scenegraph.Node)
     */
    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    protected Node getParent() {
        return parent;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#setEngine(Object)
     */
    @Override
    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#getEngine()
     */
    @Override
    public CoreEngine getEngine() {
        return engine;
    }
}
