package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Node;
import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

/**
 * A helper class to create an {@link io.github.achtern.AchternEngine.core.entity.Entity} easily.
 * Implements all methods in order to override only "needed" methods and handels the following:
 * - Storing the parent node
 * - Storing the coreengine
 * - Storing a name (and setting it)
 */
public abstract class QuickEntity implements Entity, EngineHolder<CoreEngine> {

    protected Node parent;
    protected CoreEngine engine;
    protected String name;

    /**
     * Create an "Untitled Entity"
     */
    public QuickEntity() {
        this("Untitled Entity");
    }

    /**
     * Create a entity with a specifc name
     * @param name The name
     */
    public QuickEntity(String name) {
        setName(name);
    }

    /**
     * @see Entity#input(float)
     */
    @Override
    public void input(float delta) {

    }

    /**
     * @see Entity#update(float)
     */
    @Override
    public void update(float delta) {

    }

    /**
     * @see Entity#render(io.github.achtern.AchternEngine.core.rendering.shader.Shader, io.github.achtern.AchternEngine.core.RenderEngine)
     */
    @Override
    public void render(Shader shader, RenderEngine renderEngine) {

    }

    /**
     * @see Entity#removed()
     */
    @Override
    public void removed() {
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
     * @see Entity#getTransform()
     */
    @Override
    public Transform getTransform() {
        return this.parent.getTransform();
    }

    /**
     * @see Entity#setParent(io.github.achtern.AchternEngine.core.Node)
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
