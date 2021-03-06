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

package org.achtern.AchternEngine.core;

import org.achtern.AchternEngine.core.scenegraph.Updatable;
import org.achtern.AchternEngine.core.input.Key;
import org.achtern.AchternEngine.core.input.event.listener.KeyListener;
import org.achtern.AchternEngine.core.input.event.listener.trigger.util.KeyTriggerList;
import org.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Camera;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.WireframeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameDebugger implements Updatable, EngineHolder<CoreEngine>, KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDebugger.class);

    protected CoreEngine engine;

    protected Game game;

    protected List<DebugStateListener> hooks;

    protected Color prevClearColor;

    protected DrawStrategy wD = DrawStrategyFactory.get(DrawStrategyFactory.Common.WIREFRAME);
    protected DrawStrategy sD = DrawStrategyFactory.get(DrawStrategyFactory.Common.SOLID);
    protected Node wireframe = new Node("Wireframe Display").add(new WireframeDisplay(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));

    public GameDebugger(Game game) {
        this.game = game;
        this.hooks = new ArrayList<DebugStateListener>();

        game.getInputManager().getKeyMap().register(new KeyTriggerList(Key.Z, Key.X, Key.C), this);
    }

    /**
     * Adds an listener without pushing the current state
     * @param l this listener will be registered to this debugger
     */
    public void register(DebugStateListener l) {
        register(l, false);
    }

    /**
     * Adds an listener
     * @param l the listener
     * @param pushState this will call changed() with the current status
     */
    public void register(DebugStateListener l, boolean pushState) {
        hooks.add(l);
        if (pushState) {
            //TODO: actual state detection
            l.changed(true);
        }
    }

    @Override
    public void onAction(KeyEvent event) {
        if (event.getKey().equals(Key.Z)) {
            if (!getGame().isDebug()) return;
            Class<? extends DrawStrategy> current = getEngine().getRenderEngine().getDrawStrategy().getClass();
            Class<? extends DrawStrategy> wireframe = wD.getClass();
            if (current.equals(wireframe)) {
                getEngine().getRenderEngine().setDrawStrategy(sD);
            } else {
                getEngine().getRenderEngine().setDrawStrategy(wD);
            }
        } else if (event.getKey().equals(Key.X)) {
            if (!getGame().isDebug()) return;
            if (getGame().has(wireframe)) {
                LOGGER.trace("Removing {}", wireframe.getName());
                getGame().remove(wireframe);
            } else {
                LOGGER.trace("Adding {}", wireframe.getName());
                getGame().add(wireframe);
            }
        } else if (event.getKey().equals(Key.C)) {
            Camera c = getEngine().getRenderEngine().getCamera();
            LOGGER.info("\nCamera Dump:\n" +
                    "pos={}\n" +
                    "rot={}\n" +
                    "scale={}\n",
                    c.getTransform().getPosition(),
                    c.getTransform().getRotation(),
                    c.getTransform().getScale());
        }
    }

    public void enable() {
        prevClearColor = getEngine().getRenderEngine().getState().getClearColor();
        getEngine().getRenderEngine().getState().setClearColor(new Color(0, 0.3f, 0, 1));
        for (DebugStateListener l : hooks) {
            l.changed(true);
        }
    }

    public void disable() {
        getEngine().getRenderEngine().getState().setClearColor(prevClearColor);
        for (DebugStateListener l : hooks) {
            l.changed(false);
        }
    }

    @Override
    public CoreEngine getEngine() {
        return engine;
    }

    @Override
    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void update(float delta) {
    }

    public interface DebugStateListener {

        public void changed(boolean enabled);

    }

}
