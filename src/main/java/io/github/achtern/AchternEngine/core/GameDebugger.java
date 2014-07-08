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
import io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl.LWJGLSolidDraw;
import io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl.LWJGLWireframeDraw;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.WireframeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameDebugger implements Updatable, EngineHolder<CoreEngine>, KeyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDebugger.class);

    protected CoreEngine engine;

    protected Game game;

    protected Color prevClearColor;

    protected DrawStrategy wD = new LWJGLWireframeDraw();
    protected DrawStrategy sD = new LWJGLSolidDraw();
    protected Node wireframe = new Node("Wireframe Display").add(new WireframeDisplay(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));

    public GameDebugger(Game game) {
        this.game = game;

        game.getInputManager().getKeyMap().register(new KeyTriggerList(Key.Z, Key.X, Key.C), this);
    }

    @Override
    public void onAction(KeyEvent event) {
        if (event.getKey().equals(Key.Z)) {
            if (!getGame().isDebug()) return;
            if (getEngine().getRenderEngine().getDrawStrategy() instanceof LWJGLWireframeDraw) {
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
    }

    public void disable() {
        getEngine().getRenderEngine().setClearColor(prevClearColor);
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
}
