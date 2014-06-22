package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseClickListener;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseMoveListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MouseMap implements InputMap<MouseButtonTrigger, MouseClickListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseMap.class);

    private InputAdapter input;

    protected Map<MouseButtonTrigger, List<MouseClickListener>> clickListener;

    protected List<MouseMoveListener> moveListener;

    public MouseMap() {
        this.clickListener = new HashMap<MouseButtonTrigger, List<MouseClickListener>>();
        this.moveListener = new ArrayList<MouseMoveListener>();
    }

    public MouseMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public MouseMap register(MouseButtonTrigger key, MouseClickListener l) {
        if (!this.clickListener.containsKey(key)) {
            this.clickListener.put(key, new ArrayList<MouseClickListener>());
        }

        this.clickListener.get(key).add(l);

        return this;
    }

    public MouseMap register(MouseMoveListener l) {
        moveListener.add(l);

        return this;
    }

    protected Vector2f previousPosition = new Vector2f(0, 0);

    public void trigger(float delta) {

        Set<MouseButtonTrigger> keys = this.clickListener.keySet();

        for (MouseButtonTrigger b : keys) {

            if (input.getMouse(b.get())) {
                cycle(MouseClickListener.Type.PRESS, b, delta);
            }

            if (input.getMouseDown(b.get())) {
                cycle(MouseClickListener.Type.DOWN, b, delta);
            }

            if (input.getMouseUp(b.get())) {
                cycle(MouseClickListener.Type.UP, b, delta);
            }

        }

        Vector2f position = input.getMousePosition();

        if (position.equals(previousPosition)) return;


        for (MouseMoveListener l : this.moveListener) {
            l.onAction(new MouseEvent(input, null, delta, position, position.sub(previousPosition)));
        }


        previousPosition = input.getMousePosition();
    }

    protected void cycle(MouseClickListener.Type type, MouseButtonTrigger b, float delta) {
        for (MouseClickListener l : this.clickListener.get(b)) {
            if (l.getClickType().equals(type)) {
                l.onAction(new MouseEvent(input, b.get(), delta, input.getMousePosition(), null));
            }
        }
    }

    @Override
    public Map<MouseButtonTrigger, List<MouseClickListener>> getClickListener() {
        return clickListener;
    }

    @Override
    public void setClickListener(Map<MouseButtonTrigger, List<MouseClickListener>> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public InputAdapter getInput() {
        return input;
    }

    @Override
    public void setInput(InputAdapter input) {
        this.input = input;
    }
}
