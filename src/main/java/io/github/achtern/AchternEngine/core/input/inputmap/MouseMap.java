package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseClickListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MouseMap implements InputMap<MouseButtonTrigger, MouseClickListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MouseMap.class);

    private InputAdapter input;

    protected Map<MouseButtonTrigger, List<MouseClickListener>> listener;

    public MouseMap() {
        this.listener = new HashMap<MouseButtonTrigger, List<MouseClickListener>>();
    }

    public MouseMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public MouseMap register(MouseButtonTrigger key, MouseClickListener h) {
        if (!this.listener.containsKey(key)) {
            this.listener.put(key, new ArrayList<MouseClickListener>());
        }

        this.listener.get(key).add(h);

        return this;
    }


    public void trigger(float delta) {

        Set<MouseButtonTrigger> keys = this.listener.keySet();

    }

    @Override
    public Map<MouseButtonTrigger, List<MouseClickListener>> getListener() {
        return listener;
    }

    @Override
    public void setListener(Map<MouseButtonTrigger, List<MouseClickListener>> listener) {
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
