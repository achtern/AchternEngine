package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MouseMap implements InputMap<MouseButtonTrigger, MouseListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseMap.class);

    private InputAdapter input;

    protected Map<MouseButtonTrigger, List<MouseListener>> clickListener;

    protected List<MouseListener> moveListener;

    public MouseMap() {
        this.clickListener = new HashMap<MouseButtonTrigger, List<MouseListener>>();
        this.moveListener = new ArrayList<MouseListener>();
    }

    public MouseMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public MouseMap register(MouseButtonTrigger button, MouseListener l) {
        if (!this.clickListener.containsKey(button)) {
            this.clickListener.put(button, new ArrayList<MouseListener>());
        }

        this.clickListener.get(button).add(l);

        return this;
    }

    public MouseMap register(List<MouseButtonTrigger> buttons, MouseListener h) {

        for (MouseButtonTrigger t : buttons) {
            register(t, h);
        }

        return this;
    }

    public MouseMap register(MouseListener l) {
        moveListener.add(l);

        return this;
    }

    protected Vector2f previousPosition = new Vector2f(0, 0);

    public void trigger(float delta) {

        Set<MouseButtonTrigger> keys = this.clickListener.keySet();

        for (MouseButtonTrigger b : keys) {

            if (input.getMouse(b.get()) && b.getType().equals(MouseButtonTrigger.Type.PRESS)) {
                cycle(b, delta);
            }

            if (input.getMouseDown(b.get()) && b.getType().equals(MouseButtonTrigger.Type.DOWN)) {
                cycle(b, delta);
            }

            if (input.getMouseUp(b.get()) && b.getType().equals(MouseButtonTrigger.Type.UP)) {
                cycle(b, delta);
            }

        }


        Vector2f position = input.getMousePosition();

        if (position.equals(previousPosition)) return;

        for (MouseListener l : this.moveListener) {
            l.onAction(new MouseEvent(input, null, delta, position, position.sub(previousPosition)));
        }


        previousPosition = input.getMousePosition();
    }

    protected void cycle(MouseButtonTrigger b, float delta) {
        for (MouseListener l : this.clickListener.get(b)) {
            l.onAction(new MouseEvent(input, b.get(), delta, input.getMousePosition(), null));
        }
    }

    @Override
    public Map<MouseButtonTrigger, List<MouseListener>> getClickListener() {
        return clickListener;
    }

    @Override
    public void setClickListener(Map<MouseButtonTrigger, List<MouseListener>> clickListener) {
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
