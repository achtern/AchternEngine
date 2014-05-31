package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.contracts.Inputable;
import io.github.achtern.AchternEngine.core.contracts.Updatable;
import io.github.achtern.AchternEngine.core.entity.renderpasses.WireframeDisplay;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.SolidDraw;
import io.github.achtern.AchternEngine.core.rendering.drawing.WireframeDraw;

public class GameDebugger implements Updatable, Inputable, EngineHolder<CoreEngine> {

    protected CoreEngine engine;

    protected Game game;

    protected Color prevClearColor;

    public GameDebugger(Game game) {
        this.game = game;
    }



    DrawStrategy wD = new WireframeDraw();
    DrawStrategy sD = new SolidDraw();
    Node wireframe = new Node("Wireframe Display").add(new WireframeDisplay(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0)));

    @Override
    public void input(float delta) {

        if (Input.getKeyDown(Input.KEY_Z)) {
            if (Input.getKey(Input.KEY_LCONTROL)) {
                // Wireframe Overlay

                if (game.has(wireframe)) {
                    game.remove(wireframe);
                } else {
                    game.add(wireframe);
                }

            } else {

                // Just Wireframe Draw

                if (getEngine().getRenderEngine().getDrawStrategy() instanceof WireframeDraw) {
                    getEngine().getRenderEngine().setDrawStrategy(sD);
                } else {
                    getEngine().getRenderEngine().setDrawStrategy(wD);
                }


            }
        }
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
}
