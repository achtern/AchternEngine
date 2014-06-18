package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Node;
import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.Inputable;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public interface Entity extends Inputable, Updatable {

    /**
     * Called on render, draw stuff here
     * @param shader The active shader
     * @param renderEngine The active renderEngine (caller)
     */
    public void render(Shader shader, RenderEngine renderEngine);

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
     * Should return the name of the Entits
     * @return The name
     */
    public String getName();

}