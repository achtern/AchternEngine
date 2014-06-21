package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.entity.renderpasses.WireframeDisplay;
import io.github.achtern.AchternEngine.core.input.InputEvent;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.KeyListener;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.SolidDraw;
import io.github.achtern.AchternEngine.core.rendering.drawing.WireframeDraw;

public class GameDebugger implements Updatable, EngineHolder<CoreEngine> {

    protected CoreEngine engine;

    protected Game game;

    protected Color prevClearColor;

    protected DrawStrategy wD = new WireframeDraw();
    protected DrawStrategy sD = new SolidDraw();
    protected Node wireframe = new Node("Wireframe Display").add(new WireframeDisplay(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));

    public GameDebugger(Game game) {
        this.game = game;

        game.getKeyMap().register(Key.Z, new KeyListener() {
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
        }).register(Key.X, new KeyListener() {
            @Override
            public Type getType() {
                return Type.DOWN;
            }

            @Override
            public void onAction(InputEvent event) {
                if (!getGame().isDebug()) return;
                if (getGame().has(wireframe)) {
                    getGame().remove(wireframe);
                } else {
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
