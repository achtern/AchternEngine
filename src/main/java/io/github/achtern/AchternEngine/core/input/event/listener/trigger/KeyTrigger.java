package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

import io.github.achtern.AchternEngine.core.input.Key;

public class KeyTrigger implements Trigger<Key, KeyTrigger.Type> {

    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_OR_DOWN,
        ALL
    }

    private Key onKey;
    private Type type;


    /**
     * Creates a new KeyTrigger, with a key and triggers on
     * key {@link KeyTrigger.Type#DOWN}
     * @param onKey The key which should trigger the event
     */
    public KeyTrigger(Key onKey) {
        this(onKey, Type.DOWN);
    }

    /**
     * Creates a new KeyTrigger, with a key and triggers on
     * the given {@link KeyTrigger.Type}
     * @param onKey The key which should trigger the event
     * @param type Only if the key event matches this type. The event does get fired
     */
    public KeyTrigger(Key onKey, Type type) {
        this.onKey = onKey;
        this.type = type;
    }

    @Override
    public Key get() {
        return onKey;
    }

    @Override
    public Type getType() {
        return type;
    }


}
