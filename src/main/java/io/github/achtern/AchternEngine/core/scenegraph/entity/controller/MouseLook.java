package io.github.achtern.AchternEngine.core.scenegraph.entity.controller;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;

public class MouseLook extends QuickEntity implements KeyListener, MouseListener {

    protected float sensitivity;
    protected boolean mouselock = false;
    protected Key unlockKey;

    protected boolean skip = true;

    public MouseLook(float sensitivity) {
        this(sensitivity, Key.ESCAPE);
    }

    public MouseLook(float sensitivity, Key unlockKey) {
        this.sensitivity = sensitivity;
        this.unlockKey = unlockKey;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.scenegraph.entity.Entity#attached()
     */
    @Override
    public void attached() {
        registerListener();
    }

    protected void registerListener() {
        getEngine().getGame().getInputManager().getKeyMap()
                .register(new KeyTrigger(this.unlockKey), this);

        getEngine().getGame().getInputManager().getMouseMap()
                .register(new MouseButtonTrigger(MouseButton.LEFT), this)
                .register(this); // MouseMoveEvent
    }

    @Override
    public void onAction(KeyEvent event) {
        if (!isMouselock()) return;
        event.getInputAdapter().setCursor(true);
        setMouselock(false);
    }

    @Override
    public void onAction(MouseEvent event) {
        if (event.getButton() == null) {
            // MouseMove
            if (!isMouselock()) {
                return;
            }

            if (skip) {
                skip = false;
                return;
            }

            getTransform().rotate(getTransform().getRotation().getRight(), (float) Math.toRadians(-event.getMouseDelta().getY() * sensitivity));

            getTransform().rotate(Transform.Y_AXIS, (float) Math.toRadians(event.getMouseDelta().getX() * sensitivity));

            centerMouse(event.getInputAdapter());
        } else {
            // Mouse Click
            event.getInputAdapter().setCursor(false);
            setMouselock(true);
            centerMouse(event.getInputAdapter());
            skip = true;
        }
    }


    protected void centerMouse(InputAdapter input) {
        input.setMousePosition(getEngine().getWindow().getCenter());
    }

    protected boolean isMouselock() {
        return mouselock;
    }

    protected void setMouselock(boolean lock) {
        this.mouselock = lock;
    }

}