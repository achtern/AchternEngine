package io.github.achtern.AchternEngine.core.input;

import java.util.*;

public class KeyMap {

    private InputAdapter input;

    protected Map<Key, List<KeyListener>> listener;

    public KeyMap(InputAdapter input) {
        this.input = input;
        this.listener = new HashMap<Key, List<KeyListener>>(input.keysTotal());
    }

    public KeyMap register(Key key, KeyListener h) {
        if (!this.listener.containsKey(key)) {
            this.listener.put(key, new ArrayList<KeyListener>());
        }

        this.listener.get(key).add(h);

        return this;
    }

    public void trigger(float delta) {

        input.update();

        Set<Key> keys = this.listener.keySet();

        for (Key k : keys) {
            if (input.getKey(k)) {
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.PRESS)) {
                        l.onAction(new InputEvent(k, delta));
                    }
                }
            }

            if (input.getKeyDown(k)) {
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.DOWN)) {
                        l.onAction(new InputEvent(k, delta));
                    }
                }
            }

            if (input.getKeyUp(k)) {
                for (KeyListener l : this.listener.get(k)) {
                    if (l.getType().equals(KeyListener.Type.UP)) {
                        l.onAction(new InputEvent(k, delta));
                    }
                }
            }
        }

    }

    public Map<Key, List<KeyListener>> getListener() {
        return listener;
    }

    public void setListener(Map<Key, List<KeyListener>> listener) {
        this.listener = listener;
    }

    public InputAdapter getInput() {
        return input;
    }

    public void setInput(InputAdapter input) {
        this.input = input;
    }
}