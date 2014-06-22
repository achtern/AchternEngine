package io.github.achtern.AchternEngine.core.input.event.listener;

import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;

public interface MouseClickListener {


    public enum Type {
        PRESS,
        UP,
        DOWN,
        CLICK,
        UP_OR_DOWN,
        ALL
    }

    public Type getClickType();

    public void onAction(MouseEvent event);

}
