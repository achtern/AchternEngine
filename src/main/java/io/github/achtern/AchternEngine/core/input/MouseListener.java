package io.github.achtern.AchternEngine.core.input;

public interface MouseListener {


    public enum Type {
        PRESS,
        UP,
        DOWN,
        UP_AND_DOWN,
        ALL
    }

    public Type getType();

    public void onClick(MouseEvent event);

    public void onMove(MouseEvent event);

}
