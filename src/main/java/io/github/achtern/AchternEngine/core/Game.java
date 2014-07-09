package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Renderable;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.input.InputManager;
import io.github.achtern.AchternEngine.core.input.adapter.LWJGLInput;
import io.github.achtern.AchternEngine.core.input.inputmap.KeyMap;
import io.github.achtern.AchternEngine.core.input.inputmap.MouseMap;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Texture;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
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

    private InputManager inputManager;

    public Game() {
        this(new InputManager(new LWJGLInput()));
    }

    public Game(InputManager inputManager) {
        this.inputManager = inputManager;
        this.inputManager.setMouseMap(new MouseMap());
        this.inputManager.setKeyMap(new KeyMap());
    }


    /**
     * Returns the dimensions of the window.
     * @return The new window dimensions
     */
    public abstract Dimension getWindowDimensions();

    /**
     * Returns the title of the window.
     * @return The window title
     */
    public abstract String getWindowTitle();


    /**
     * Delegates to the scenegraph and debugger
     * @param delta The delta time
     */
    public void updateSceneGraph(float delta) {
        if (isDebug()) debugger.update(delta);
        getSceneGraph().update(delta);
        update(delta);
    }

    /**
     * Injects the engine into game and scenegraph
     * and inits the user's game.
     * @param engine The CoreEngine instance (parent)
     */
    public final void preInit(CoreEngine engine) {
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
     * @param renderEngine The active RenderEngine instance
     */
    public final void renderSceneGraph(RenderEngine renderEngine) {
        LOGGER.trace("Rendering SceneGraph: {}", getSceneGraph());
        renderEngine.render(getSceneGraph());
        render(renderEngine);
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
     * Adds a node the scenegraph.
     * Keep a reference to it, or the node's name,
     * in order to retrieve the node at a later point easily.
     * @param node The node which should get added.
     */
    public void add(Node node, boolean forceName) {
        getSceneGraph().add(node, forceName);
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
     * Returns the loading/splash image.
     * Should only do minimal stuff!
     * FAST FAST FAST
     * If the texture is null, the engine's
     * default image will get shown.
     * @return A texture | null
     */
    public Texture getSplashScreen() {
        return null;
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

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}
