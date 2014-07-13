package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.bootstrap.WindowIOBindingManager;
import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.util.FPS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreEngine implements Runnable, EngineHolder<RenderEngine> {

    public static final Logger LOGGER = LoggerFactory.getLogger(CoreEngine.class);

    protected WindowIOBindingManager bindingManager;

    protected Window window;

    private static boolean stopRequest = false;

    private boolean running;

    private Game game;
    private RenderEngine renderEngine;

    private double frameTime;

    private FPS fps;

    /**
     * Request a force stop of the engine
     */
    public static void requestStop() {
        stopRequest = true;
    }

    /**
     * Whether a force stop was request via {@link CoreEngine#requestStop()}
     * @return stopRequest
     */
    public static boolean stopRequested() {
        return stopRequest;
    }

    /**
     * Creates a new Game Holder and runner
     * @param game The game to run.
     */
    public CoreEngine(Game game) {
        this(game, WindowIOBindingManager.Binding.LWJGL);
    }

    /**
     * Creates a new Game Holder and runner
     * @param game The game to run.
     */
    public CoreEngine(Game game, WindowIOBindingManager.Binding binding) {
        this.game = game;
        this.running = false;
        this.fps = new FPS();
        this.bindingManager = new WindowIOBindingManager(binding);
        this.bindingManager.populateDrawStrategyFactory();
    }

    /**
     * Creates a new Window.
     * @param title The window's title
     * @param dimensions The window's dimensions
     */
    protected void createWindow(String title, Dimension dimensions) {
        window = new Window(dimensions);
        window.create(title);
        this.renderEngine = bindingManager.getRenderEngine();
        LOGGER.debug("OpenGL Version: {}", this.renderEngine.getOpenGLVersion());
    }

    /**
     * Starts the game, limiting it to a given framerate (fps)
     * @param framerate The max. framerate (fps)
     */
    public void start(double framerate) {
        this.frameTime = 1 / framerate;
        if (running) {
            return;
        }

        run();
    }

    /**
     * Stops the game.
     */
    protected void stop() {
        if (!running) {
            return;
        }

        running = false;
    }

    /**
     * Runs the game.
     * First it inits it. This will show a loading screen.
     * And then update, input and renders it.
     */
    @Override
    public void run() {


        running = true;

        createWindow(game.getWindowTitle(), game.getWindowDimensions());

        LoadingScreen.show(this, game.getSplashScreen());

        game.preInit(this);

        double lastTime = Time.getTime();
        double unprocessedTime = 0;




        while (running) {

            boolean render = false;

            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;


            unprocessedTime += passedTime;
            fps.passed(passedTime);

            while (unprocessedTime > frameTime) {

                render = true;
                unprocessedTime -= frameTime;


                if (window.isCloseRequested() || stopRequested()) {
                    stop();
                }


                game.updateSceneGraph((float) frameTime);

                game.getInputManager().trigger((float) frameTime);

                fps.display();

            }

            if (render) {
                game.renderSceneGraph(renderEngine);
                window.render();
                fps.rendered();
            }


        }

        cleanUp();


    }

    /**
     * Disposes the window.
     * Destroing Mouse and Keyboard.
     */
    public void cleanUp() {
        window.dispose();
    }

    /**
     * Returns the FPS counter
     * @return the FPS counter
     */
    public FPS getFps() {
        return fps;
    }

    /**
     * A nice name for getEngine(). Nothing more.
     * But will return the associated render engine.
     * @return The main render engine
     */
    public RenderEngine getRenderEngine() {
        return getEngine();
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#getEngine()
     */
    @Override
    public void setEngine(RenderEngine engine) {
        this.renderEngine = engine;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.contracts.EngineHolder#setEngine(Object)
     */
    @Override
    public RenderEngine getEngine() {
        return renderEngine;
    }

    public Game getGame() {
        return game;
    }

    public WindowIOBindingManager getBindingManager() {
        return bindingManager;
    }

    public void setBindingManager(WindowIOBindingManager bindingManager) {
        this.bindingManager = bindingManager;
    }

    public Window getWindow() {
        return window;
    }
}
