package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.event.listener.KeyListener;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseClickListener;
import io.github.achtern.AchternEngine.core.input.event.listener.MouseMoveListener;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger;
import io.github.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import io.github.achtern.AchternEngine.core.input.event.payload.MouseEvent;
import io.github.achtern.AchternEngine.core.math.Vector2f;

public class MouseLook extends QuickEntity {

    protected float sensitivity;
    protected boolean mouselock = false;
    protected Key unlockKey;

    public MouseLook(float sensitivity) {
        this(sensitivity, Key.ESCAPE);
    }

    public MouseLook(float sensitivity, Key unlockKey) {
        this.sensitivity = sensitivity;
        this.unlockKey = unlockKey;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.entity.Entity#attached()
     */
    @Override
    public void attached() {
        registerListener();
    }

    protected void registerListener() {
        getEngine().getGame().getInputManager().getKeyMap().register(new KeyTrigger(this.unlockKey), new KeyListener() {
            @Override
            public Type getPressType() {
                return Type.DOWN;
            }

            @Override
            public void onAction(KeyEvent event) {
                if (!isMouselock()) return;
                event.getInputAdapter().setCursor(true);
                setMouselock(false);
            }
        });

        getEngine().getGame().getInputManager().getMouseMap().register(new MouseButtonTrigger(MouseButton.LEFT), new MouseClickListener() {
            @Override
            public Type getClickType() {
                return Type.DOWN;
            }

            @Override
            public void onAction(MouseEvent event) {
                event.getInputAdapter().setCursor(false);
                setMouselock(true);
                centerMouse(event.getInputAdapter());
            }
        });

        getEngine().getGame().getInputManager().getMouseMap().register(new MouseMoveListener() {
            @Override
            public void onAction(MouseEvent event) {
                if (!isMouselock()) return;
                getTransform().rotate(getTransform().getRotation().getRight(), (float) Math.toRadians(-event.getMouseDelta().getY() * sensitivity));

                getTransform().rotate(Transform.Y_AXIS, (float) Math.toRadians(event.getMouseDelta().getX() * sensitivity));

                centerMouse(event.getInputAdapter());
            }
        });
    }

    protected void centerMouse(InputAdapter input) {
        Vector2f center = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);
        input.setMousePosition(center);
    }

    protected boolean isMouselock() {
        return mouselock;
    }

    protected void setMouselock(boolean lock) {
        this.mouselock = lock;
    }

}
