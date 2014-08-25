package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Renderable;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.input.InputManager;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
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

    /**
     * The main InputManager, used to register key/mouse actions
     * This will get used by the Engine as well.
     */
    private InputManager inputManager;

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
        setInputManager(new InputManager(engine.getBindingManager().getInputAdapter()));
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
        preRender(renderEngine);
        LOGGER.trace("Rendering SceneGraph: {}", getSceneGraph());
        renderEngine.render(getSceneGraph());
        render(renderEngine);
        postRender(renderEngine);
    }

    /**
     * This method will get called before each render
     * @param renderEngine The active RenderEngine instance
     */
    public void preRender(RenderEngine renderEngine) {
    }

    /**
     * This method will get called after each render
     * @param renderEngine The active RenderEngine instance
     */
    public void postRender(RenderEngine renderEngine) {
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
     * If you want to get nested nodes,
     * sperate the {@link Node}s names with
     * a slash (<code>/</code>).
     * @param nodeName The node's name
     * @return The node or null if not found
     */
    public Node get(String nodeName) {

        // Example: nodeName = Meshes/Static/Box

        Node r;
        if (nodeName.contains("/")) {
            String[] nodes = nodeName.split("/");
            // -> 0: Meshes, 1: Static, 2: Box
            r = getSceneGraph().getChildren().get(nodes[0]);
            if (r == null) {
                return null;
            }
            for (int i = 1; i < nodes.length; i++) {
                String name = nodes[i]; // 1 = nodes[1] => Static // 2 = nodes[2] => Box
                r = r.getChildren().get(name); // 1 = r = Node(Static) // 2 = r = Node(Box)
            }

        } else {
            r = getSceneGraph().getChildren().get(nodeName);
        }

        return r;
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
        } else if (debugger != null) {
            debugger.disable();
            debugger = null;
        }
    }

    public GameDebugger getDebugger() {
        return debugger;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
}
