/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.bootstrap.BuildInfo;
import io.github.achtern.AchternEngine.core.bootstrap.MainBindingProvider;
import io.github.achtern.AchternEngine.core.rendering.BasicRenderEngine;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.util.FPS;
import io.github.achtern.AchternEngine.core.util.WindowChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The CoreEngine is the main entry point of the
 * AchternEngine.
 * The Engine is running the main loop and manages Game and
 * RenderEngine, as well as the {@link io.github.achtern.AchternEngine.core.bootstrap.MainBindingProvider}.
 */
public class CoreEngine implements Runnable, EngineHolder<RenderEngine> {

    public static final Logger LOGGER = LoggerFactory.getLogger(CoreEngine.class);

    /**
     * Only used during bootstrap to manage the hardware/graphics/native binding
     */
    protected MainBindingProvider bindingManager;

    /**
     * The Main Render Window
     */
    protected Window window;

    protected List<WindowChangeListener> windowChangeListenerList;

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
        this(game, new MainBindingProvider(MainBindingProvider.Bindings.LWJGL));
    }

    /**
     * Creates a new Game Holder and runner
     * @param game The game to run.
     * @param binding Binding Manager
     */
    public CoreEngine(Game game, MainBindingProvider binding) {
        LOGGER.debug(BuildInfo.get());
        this.game = game;
        this.running = false;
        this.fps = new FPS();
        this.bindingManager = binding;
        this.bindingManager.populateDrawStrategyFactory();
        this.windowChangeListenerList = new ArrayList<WindowChangeListener>();
    }

    /**
     * Creates a new Window.
     * @param title The window's title
     * @param dimensions The window's dimensions
     */
    protected void createWindow(String title, Dimension dimensions) {
        window = bindingManager.getWindow(dimensions);
        window.create(title);
        this.renderEngine = new BasicRenderEngine(bindingManager);
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

        LoadingScreen.get().show(this, game.getSplashScreen());

        LOGGER.info("Initializing Game");
        game.preInit(this);
        LOGGER.info("Done initializing Game");

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

            if (window.resized()) {
                for (WindowChangeListener l : windowChangeListenerList) {
                    l.onWindowChange(window);
                }
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

    public void addWindowChangeListener(WindowChangeListener listener) {
        windowChangeListenerList.add(listener);
    }

    public void removeWindowChangeListener(WindowChangeListener listener) {
        windowChangeListenerList.remove(listener);
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
     * @see EngineHolder#getEngine()
     */
    @Override
    public void setEngine(RenderEngine engine) {
        this.renderEngine = engine;
    }

    /**
     * @see EngineHolder#setEngine(Object)
     */
    @Override
    public RenderEngine getEngine() {
        return renderEngine;
    }

    public Game getGame() {
        return game;
    }

    public MainBindingProvider getBindingManager() {
        return bindingManager;
    }

    public void setBindingManager(MainBindingProvider bindingManager) {
        this.bindingManager = bindingManager;
    }

    public Window getWindow() {
        return window;
    }
}
