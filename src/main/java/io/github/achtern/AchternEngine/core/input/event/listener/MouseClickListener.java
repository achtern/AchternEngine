package io.github.achtern.AchternEngine.core.input.event.listener;

public interface MouseClickListener extends EventListener {


    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_AND_DOWN,
        ALL
    }

    public Type getType();

}
