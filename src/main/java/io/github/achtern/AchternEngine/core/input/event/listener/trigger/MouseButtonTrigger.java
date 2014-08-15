package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

import io.github.achtern.AchternEngine.core.input.MouseButton;

public class MouseButtonTrigger implements Trigger<MouseButton, MouseButtonTrigger.Type> {

    public enum Type {
        PRESS,
        UP,
        DOWN,
        CLICK,
        UP_OR_DOWN,
        ALL
    }

    private MouseButton onClick;
    private Type type;

    public MouseButtonTrigger(MouseButton onClick) {
        this(onClick, Type.DOWN);
    }

    public MouseButtonTrigger(MouseButton onClick, Type type) {
        this.onClick = onClick;
        this.type = type;
    }

    @Override
    public MouseButton get() {
        return onClick;
    }

    @Override
    public Type getType() {
        return type;
    }
}
