package io.github.achtern.AchternEngine.core.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MouseMap implements InputMap<MouseButton, MouseListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseMap.class);

    private InputAdapter input;

    protected Map<MouseButton, List<MouseListener>> listener;


    public MouseMap(InputAdapter input) {
        this.input = input;
    }

    public MouseMap register(MouseButton key, MouseListener h) {
        if (!this.listener.containsKey(key)) {
            this.listener.put(key, new ArrayList<MouseListener>());
        }

        this.listener.get(key).add(h);

        return this;
    }


    public void trigger(float delta) {

        Set<MouseButton> keys = this.listener.keySet();

    }

    @Override
    public Map<MouseButton, List<MouseListener>> getListener() {
        return listener;
    }

    @Override
    public void setListener(Map<MouseButton, List<MouseListener>> listener) {
        this.listener = listener;
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
