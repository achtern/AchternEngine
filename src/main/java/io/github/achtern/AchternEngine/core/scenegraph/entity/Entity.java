package io.github.achtern.AchternEngine.core.scenegraph.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

/**
 * An Entity is the base of all things which live
 * and do stuff in the scenegraph.
 * The need a parent ({@link io.github.achtern.AchternEngine.core.scenegraph.Node}) to
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
