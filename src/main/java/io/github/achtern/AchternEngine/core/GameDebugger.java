package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.entity.renderpasses.WireframeDisplay;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.InputEvent;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.SolidDraw;
import io.github.achtern.AchternEngine.core.rendering.drawing.WireframeDraw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameDebugger implements Updatable, EngineHolder<CoreEngine> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDebugger.class);

    protected CoreEngine engine;

    protected Game game;

    protected Color prevClearColor;

    protected DrawStrategy wD = new WireframeDraw();
    protected DrawStrategy sD = new SolidDraw();
    protected Node wireframe = new Node("Wireframe Display").add(new WireframeDisplay(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));

    public GameDebugger(Game game) {
        this.game = game;

        game.getInputManager().getKeyMap().register(new KeyTrigger(Key.Z), new KeyListener() {
            @Override
            public Type getType() {
                return Type.DOWN;
            }

            @Override
            public void onAction(InputEvent event) {
                if (!getGame().isDebug()) return;
                if (getEngine().getRenderEngine().getDrawStrategy() instanceof WireframeDraw) {
                    getEngine().getRenderEngine().setDrawStrategy(sD);
                } else {
                    getEngine().getRenderEngine().setDrawStrategy(wD);
                }
            }

        }).register(new KeyTrigger(Key.X), new KeyListener() {
            @Override
            public Type getType() {
                return Type.DOWN;
            }

            @Override
            public void onAction(InputEvent event) {
                if (!getGame().isDebug()) return;
                if (getGame().has(wireframe)) {
                    LOGGER.trace("Removing {}", wireframe.getName());
                    getGame().remove(wireframe);
                } else {
                    LOGGER.trace("Adding {}", wireframe.getName());
                    getGame().add(wireframe);
                }
            }
        });
    }

    @Override
    public void update(float delta) {
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
}
