package io.github.achtern.AchternEngine.core.input.event.listener;

import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;

public interface KeyListener {

    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_OR_DOWN,
        ALL
    }

    public Type getPressType();

    public void onAction(KeyEvent event);
}
