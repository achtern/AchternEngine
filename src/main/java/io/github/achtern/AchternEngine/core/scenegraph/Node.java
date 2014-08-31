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

package io.github.achtern.AchternEngine.core.scenegraph;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.EngineHolder;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Renderable;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Node represents a part of the scenegraph.
 * This Node manages a its subnodes (children) and set
 * of {@link Entity} (entites).
 */
public class Node implements EngineHolder<CoreEngine>, Updatable, Renderable {

    /**
     * Maps children's names to children.
     */
    protected Map<String, Node> children;
    /**
     * List of Entities, attached to this Node
     */
    protected ArrayList<Entity> entities;
    /**
     * Transform of this Node and all the attached
     * Entities
     */
    protected Transform transform;
    /**
     * The active CoreEngine
     */
    protected CoreEngine engine;

    /**
     * Node's name
     */
    protected String name;

    /**
     * Create a new Node.
     * @param name The name of the node
     */
    public Node(String name) {
        this.children = new HashMap<String, Node>();
        this.entities = new ArrayList<Entity>();
        this.transform = new Transform();
        this.name = name;
    }

    /**
     * Creates and untitled Node.
     * The name will be <code>NULL</code> until its
     * getting added as a child to another node.
     * (Then name will be "PARENT-NODE >> Untitled Node X" where X is
     * the number of already existing subnodes.
     */
    public Node() {
        this(null);
    }

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     *
     * @param delta The delta time
     */
    @Override
    public void update(float delta) {
        transform.update();

        for (Entity entity : getEntities()) {
            entity.update(delta);
        }

        for (Node node : getChildren().values()) {
            node.update(delta);
        }
    }

    /**
     * Trigger an render.
     * Do rendering releated stuff here.
     *
     * @param renderEngine The active RenderEngine
     */
    @Override
    public void render(RenderEngine renderEngine) {
        for (Entity entity : getEntities()) {
            entity.render(renderEngine);
        }

        for (Node node : getChildren().values()) {
            node.render(renderEngine);
        }
    }

    /**
     * Adds Node as a children.
     * If a node with the same node exists,
     * or the node's name is null a name will
     * be auto generated.
     * @param node New child
     * @return this
     */
    public Node add(Node node) {
        return add(node, false);
    }

    /**
     *
     * Adds Node as a children.
     * If a node with the same node exists,
     * or the node's name is null a name will
     * be auto generated only if forceName=true
     * @param node New child
     * @param forceName Whether to prevent auto-generation of names
     * @return this
     */
    public Node add(Node node, boolean forceName) {
        if (node.getName() == null && !forceName) {
            node.setName(getName() + " >> Untitled Node " + getChildren().size());
        }

        int i = 1;
        final String orgName = node.getName();
        while (getChildren().containsKey(node.getName()) && !forceName) {
            node.setName(getName() + ">> " + orgName + " " + i);
            i++;
        }

        getChildren().put(node.getName(), node);
        node.setEngine(getEngine());
        node.getTransform().setParent(this.getTransform());
        for (Entity e : node.getEntities()) {
            e.setEngine(node.getEngine());
            e.setParent(node);
            e.attached();
        }
        return this;
    }

    /**
     * Add an Entity to this Node.
     * All Entites attached to this node, will share
     * the same {@link io.github.achtern.AchternEngine.core.Transform}.
     * @param entity New Entity to add
     * @return this
     */
    public Node add(Entity entity) {
        getEntities().add(entity);
        entity.setParent(this);
        return this;
    }

    /**
     * Removes child Node from this Node.
     * @param node Node to remove
     * @return Whether the remove was successful (false if Node did not exist in children List)
     */
    public boolean remove(Node node) {
        node.removed();
        Node t = getChildren().remove(node.getName());
        return t != null;
    }

    /**
     * Remove Node by it's name
     * @param nodeName Node's name
     * @return Whether the remove was successful (false if Node did not exist in children List)
     */
    public boolean remove(String nodeName) {
        Node toBeRemoved = getChildren().get(nodeName);

        if (toBeRemoved == null) return false;

        toBeRemoved.removed();

        getChildren().remove(nodeName);

        return true;
    }

    /**
     * Remove Entity from this Node.
     * The Entity may still have this node as it's parent though.
     * {@link io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity} handles this problems
     * and sets parent to null on removal.
     * This will call {@link Entity#removed()} on the given Entity
     * @param entity The Entity to remove
     * @return Whether the remove was successful (false if Entity did not exist in List)
     */
    public boolean remove(Entity entity) {
        entity.removed();
        return getEntities().remove(entity);
    }

    /**
     * When this Node has been removed from it's parent, this method
     * will get called and notifies all children and child-entites.
     */
    public void removed() {
        for (Node child : getChildren().values()) {
            child.removed();
        }

        for (Entity entity : getEntities()) {
            entity.removed();
        }

        // Remove the engine at the very end,
        // to allow the entites to perform tasks on the engine
        // (e.g. remove themself as a render pass
        setEngine(null);
    }

    @Override
    public String toString() {
        return "Node: " + this.name + " (" + getChildren().size() + " children; " + getEntities().size() + " entities)";
    }

    /**
     * Returns List of child-Nodes
     * @return children
     */
    public Map<String, Node> getChildren() {
        return children;
    }

    /**
     * Replaces children. Be careful!
     * removed() method won't get called.
     * @param children New children
     */
    public void setChildren(Map<String, Node> children) {
        this.children = children;
    }

    /**
     * Returns List of Entities
     * @return entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Replaces Entities. Be careful!
     * removed() method won't get called.
     * @param entities New Entities
     */
    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Returns the Transform associated with this Node
     * and all it's Child-Entities.
     * @return Transform
     */
    public Transform getTransform() {
        return transform;
    }

    /**
     * Replaces Transform
     * @param transform New Transform
     */
    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    /**
     * Retrieves the stored engine
     * CoreEngine
     * @return The stored engine
     */
    @Override
    public CoreEngine getEngine() {
        return engine;
    }

    /**
     * Inject the engine
     * CoreEngine.
     * Passes Engine to the children/entities
     * if diffrent from currently stored.
     * @param engine The engine to store
     */
    @Override
    public void setEngine(CoreEngine engine) {
        if (this.engine != engine) {
            this.engine = engine;
            for (Node child : children.values()) {
                child.setEngine(engine);
            }

            for (Entity e : entities) {
                e.setEngine(engine);
            }
        }
    }

    /**
     * The name of this Node
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this Node
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }
}
