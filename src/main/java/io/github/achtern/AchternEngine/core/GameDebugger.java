package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.util.KeyTriggerList;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.WireframeDisplay;
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
     * Adds an listener
     * @param l
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
        l.changed(true);
    }

    @Override
    public void onAction(KeyEvent event) {
        if (event.getKey().equals(Key.Z)) {
            if (!getGame().isDebug()) return;
            Class<? extends DrawStrategy> current = getEngine().getRenderEngine().getDrawStrategy().getClass();
            Class<? extends DrawStrategy> wireframe = DrawStrategyFactory.get(DrawStrategyFactory.Common.WIREFRAME).getClass();
            if (current.isAssignableFrom(wireframe)) {
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
            Camera c = getEngine().getRenderEngine().getMainCamera();
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
        prevClearColor = getEngine().getRenderEngine().getClearColor();
        getEngine().getRenderEngine().setClearColor(new Color(0, 0.3f, 0, 1));
        for (DebugStateListener l : hooks) {
            l.changed(true);
        }
    }

    public void disable() {
        getEngine().getRenderEngine().setClearColor(prevClearColor);
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
