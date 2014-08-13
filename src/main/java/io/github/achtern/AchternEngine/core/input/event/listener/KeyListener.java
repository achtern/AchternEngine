package io.github.achtern.AchternEngine.core.input.event.listener;

import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;

/**
 * A KeyListener will be notified if the trigger as been
 * triggered
 */
public interface KeyListener {

    /**
     * When the trigger as triggered.
     * @param event The KeyEvent
     */
    public void onAction(KeyEvent event);
}
