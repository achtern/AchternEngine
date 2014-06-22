package io.github.achtern.AchternEngine.core.input.inputmap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KeyMap implements InputMap<KeyTrigger, KeyListener> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyMap.class);

    private InputAdapter input;

    protected Map<KeyTrigger, List<KeyListener>> listener;

    public KeyMap(InputAdapter input) {
        this();
        this.input = input;
    }

    public KeyMap() {
        this.listener = new HashMap<KeyTrigger, List<KeyListener>>();
    }

    public KeyMap register(KeyTrigger key, KeyListener h) {
        if (!this.listener.containsKey(key)) {
            this.listener.put(key, new ArrayList<KeyListener>());
        }

        this.listener.get(key).add(h);

        return this;
    }

    public void trigger(float delta) {

        Set<KeyTrigger> keys = this.listener.keySet();

        for (KeyTrigger k : keys) {
            if (input.getKey(k.get())) {
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.PRESS)) {
                        l.onAction(new KeyEvent(k.get(), delta));
                    }
                }
            }

            if (input.getKeyDown(k.get())) {
                LOGGER.trace("Key Event DOWN: {}", k.toString());
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.DOWN)) {
                        l.onAction(new KeyEvent(k.get(), delta));
                    }
                }
            }

            if (input.getKeyUp(k.get())) {
                LOGGER.trace("Key Event UP: {}", k.toString());
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.UP)) {
                        l.onAction(new KeyEvent(k.get(), delta));
                    }
                }
            }
        }

    }

    public Map<KeyTrigger, List<KeyListener>> getListener() {
        return listener;
    }

    public void setListener(Map<KeyTrigger, List<KeyListener>> listener) {
        this.listener = listener;
    }

    public InputAdapter getInput() {
        return input;
    }

    public void setInput(InputAdapter input) {
        this.input = input;
    }
}
