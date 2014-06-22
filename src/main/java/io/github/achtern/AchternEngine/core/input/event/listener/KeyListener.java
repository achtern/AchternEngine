package io.github.achtern.AchternEngine.core.input.event.listener;

public interface KeyListener extends EventListener {

    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_AND_DOWN,
        ALL
    }

    public Type getType();

}
