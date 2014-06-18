package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Renderable;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract Game.
 */
public abstract class Game implements Updatable, Renderable, EngineHolder<CoreEngine> {

    /**
     * Logger is reserved for this abstract class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    /**
     * The root node.
     * The user may not modify this! (only add child nodes)
     */
    private Node mainNode;
    /**
     * The CoreEngine instance.
     */
    private CoreEngine engine;

    /**
     * A simple Debugger.
     * @see io.github.achtern.AchternEngine.core.GameDebugger
     */
    private GameDebugger debugger;

    /**
     * Delegates to the scenegraph and debugger
     * ALWAYS call this methods in overwritten versions,
     * otherwise the scenegraph won't get inputed!
     * @param delta The delta time
     */
    public void input(float delta) {
        if (isDebug()) debugger.input(delta);
        getSceneGraph().input(delta);
    }

    /**
     * Delegates to the scenegraph and debugger
     * ALWAYS call this methods in overwritten versions,
     * otherwise the scenegraph won't get updated!
     * @param delta The delta time
     */
    public void update(float delta) {
        if (isDebug()) debugger.update(delta);
        getSceneGraph().update(delta);
    }

    /**
     * Injects the engine into game and scenegraph
     * and inits the user's game.
     * @param engine The CoreEngine instance (parent)
     */
    public void preInit(CoreEngine engine) {
        setEngine(engine);
        getSceneGraph().setEngine(engine);

        // And init the user's game
        init(engine);
    }

    /**
     * The Game should load all resources and setup the scene here.
     * A loading screen will get shown during this init process.
     * @param engine The CoreEngine instance (parent)
     */
    public abstract void init(CoreEngine engine);

    /**
     * Renders the scenegraph
     * ALWAYS call this methods in overwritten versions,
     * otherwise the scenegraph won't get rendered!
     * @param renderEngine The active RenderEngine instance
     */
    public void render(RenderEngine renderEngine) {
        LOGGER.trace("Rendering SceneGraph: {}", getSceneGraph());
        renderEngine.render(getSceneGraph());
    }

    /**
     * Adds a node the scenegraph.
     * Keep a reference to it, or the node's name,
     * in order to retrieve the node at a later point easily.
     * @param node The node which should get added.
     */
    public void add(Node node) {
        getSceneGraph().add(node);
        LOGGER.trace("Node added to scene graph: {}", node);
    }

    /**
     * Retrives a node by it's name.
     * Only checks the top level and NOT sup-nodes
     * @param nodeName The node's name
     * @return The node or null if not found
     */
    public Node get(String nodeName) {
        return getSceneGraph().getChildren().get(nodeName);
    }

    /**
     * Checks if a node exists in the scenegraph.
     * Only checks the top level and NOT sup-nodes
     * @param node The node to check
     * @return whether node has been found
     */
    public boolean has(Node node) {
        return getSceneGraph().getChildren().containsValue(node);
    }

    /**
     * Removes a node from the scenegraph.
     * Doesn't search in sub-nodes!
     * @param node The node to remove
     */
    public void remove(Node node) {
        if (!has(node)) {
            throw new IllegalStateException("Node doesn' t exists");
        }

        getSceneGraph().remove(node);

    }

    /**
     * Removes a node from the scenegraph by it's name.
     * Doesn't search in sub-nodes!
     * @param nodeName The node's name
     * @return Whether the removing was successful
     */
    public boolean remove(String nodeName) {

        Node toBeRemoved = getSceneGraph().getChildren().get(nodeName);

        return toBeRemoved != null && getSceneGraph().remove(toBeRemoved);

    }

    /**
     * Creates a new root Node, if it was null before.
     * @return The scenegraph
     */
    private Node getSceneGraph() {

        if (mainNode == null) {
            LOGGER.debug("Scene Graph created");
            mainNode = new Node("Scene Graph");
            mainNode.setEngine(getEngine());
        }

        return mainNode;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#getEngine()
     */
    public CoreEngine getEngine() {
        return engine;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#setEngine(Object)
     */
    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    /**
     * Is this game in debug mode?
     * @return whether debug is enabled
     */
    public boolean isDebug() {
        return debugger != null;
    }

    /**
     * Enabled or disables the GameDebugger.
     * On Disable the debugger will get destroyed
     * and on re-enable a new one will get initiated.
     * @param debug To turn debug mode on or off (true | false)
     */
    public void setDebug(boolean debug) {
        if (debug) {
            debugger = new GameDebugger(this);
            debugger.setEngine(getEngine());
            debugger.enable();
        } else {
            debugger.disable();
            debugger = null;
        }
    }
}