package io.github.achtern.AchternEngine.core.input.event.listener.trigger;

import io.github.achtern.AchternEngine.core.input.MouseButton;

public class MouseButtonTrigger implements Trigger<MouseButton> {

    private MouseButton onClick;

    @Override
    public MouseButton get() {
        return onClick;
    }

    @Override
    public boolean accepts(MouseButton button) {
        return onClick.equals(button);
    }
}
