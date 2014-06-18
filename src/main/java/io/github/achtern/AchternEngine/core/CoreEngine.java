package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.util.FPS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreEngine implements Runnable, EngineHolder<RenderEngine> {

    public static final Logger LOGGER = LoggerFactory.getLogger(CoreEngine.class);
    private boolean running;

    private Game game;
    private RenderEngine renderEngine;

    private double frameTime;

    private FPS fps;

    /**
     * Creates a new Game Holder and runner
     * @param game The game to run.
     */
    public CoreEngine(Game game) {
        this.game = game;
        this.running = false;
        this.fps = new FPS();
    }

    /**
     * Creates a new Window.
     * @param title The window's title
     * @param width The window's width
     * @param height The window's height
     */
    public void createWindow(String title, int width, int height) {
        Window.create(width, height, title);
        this.renderEngine = new RenderEngine();
        LOGGER.debug("OpenGL Version: {}", RenderEngine.getOpenGLVersion());
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
    public void stop() {
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


                if (Window.isCloseRequested()) {
                    stop();
                }



                game.input((float) frameTime);

                Input.update();

                game.update((float) frameTime);

                fps.display();

            }

            if (render) {
                game.render(renderEngine);
                Window.render();
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
        Window.dispose();
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
}