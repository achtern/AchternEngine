package io.github.achtern.AchternEngine.core.scenegraph;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
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
public class Node implements EngineHolder<CoreEngine> {

    private Map<String, Node> children;
    private ArrayList<Entity> entities;
    private Transform transform;
    private CoreEngine engine;

    private String name;

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

    public void update(float delta) {

        transform.update();

        for (Entity entity : getEntities()) {
            entity.update(delta);
        }

        for (Node node : getChildren().values()) {
            node.update(delta);
        }
    }

    public void render(Shader shader, RenderEngine renderEngine) {

        for (Entity entity : getEntities()) {
            entity.render(shader, renderEngine);
        }

        for (Node node : getChildren().values()) {
            node.render(shader, renderEngine);
        }
    }

    public Node add(Node node) {
        return add(node, false);
    }

    public Node add(Node node, boolean forceName) {
        if (node.getName() == null && !forceName) {
            node.setName(getName() + " >> Untitled Node " + getChildren().size());
        }

        int i = 1;
        while (getChildren().containsKey(node.getName()) && !forceName) {
            node.setName(getName() + ">> " + node.getName() + " " + i);
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

    public Node add(Entity entity) {
        getEntities().add(entity);
        entity.setParent(this);
        return this;
    }

    public boolean remove(Node node) {
        node.removed();
        Node t = getChildren().remove(node.getName());
        return t != null;
    }

    public boolean remove(String nodeName) {
        Node toBeRemoved = getChildren().get(nodeName);

        if (toBeRemoved == null) return false;

        toBeRemoved.removed();

        getChildren().remove(nodeName);

        return true;
    }

    public boolean remove(Entity entity) {
        entity.removed();
        return getEntities().remove(entity);
    }

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
        return "Node: " + this.name + " (" + getChildren().size() + " children)";
    }

    public Map<String, Node> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Node> children) {
        this.children = children;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    @Override
    public CoreEngine getEngine() {
        return engine;
    }

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
