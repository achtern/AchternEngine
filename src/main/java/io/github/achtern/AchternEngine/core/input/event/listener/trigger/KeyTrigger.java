package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

import io.github.achtern.AchternEngine.core.input.Key;

public class KeyTrigger implements Trigger<Key> {

    private Key onKey;


    public KeyTrigger(Key onKey) {
        this.onKey = onKey;
    }

    @Override
    public Key get() {
        return onKey;
    }

    @Override
    public boolean accepts(Key key) {
        return onKey.equals(key);
    }
}
