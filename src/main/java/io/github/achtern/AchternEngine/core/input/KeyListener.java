package io.github.achtern.AchternEngine.core.input;

public interface KeyListener {

    public enum Type {
        PRESS,
        UP,
        DOWN
    }

    public Type getType();

    public void onAction(InputEvent event);

}
