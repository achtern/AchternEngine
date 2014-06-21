package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.input.InputEvent;
import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.KeyListener;
import io.github.achtern.AchternEngine.core.input.LWJGLInput;
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
        getEngine().getGame().getKeyMap().register(this.unlockKey, new KeyListener() {
            @Override
            public Type getType() {
                return Type.PRESS;
            }

            @Override
            public void onAction(InputEvent event) {
                LWJGLInput.setCursorStatic(true);
                setMouselock(false);
            }
        });
    }

    @Override
    public void update(float delta) {
        Vector2f center = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);


        if (LWJGLInput.getMouseDownStatic(0)) {
            LWJGLInput.setMousePositionStatic(center);
            LWJGLInput.setCursorStatic(false);
            this.mouselock = true;
        }

        if(this.mouselock)
        {
            transform(center);
        }
    }

    public void transform(Vector2f windowCenter) {

        Vector2f deltaPos = LWJGLInput.getMousePositionStatic().sub(windowCenter);

        boolean rotX = deltaPos.getY() != 0;
        boolean rotY = deltaPos.getX() != 0;

        if(rotX) {
            getTransform().rotate(getTransform().getRotation().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
        }

        if(rotY) {
            getTransform().rotate(Transform.Y_AXIS, (float) Math.toRadians(deltaPos.getX() * sensitivity));
        }


        if(rotY || rotX) {
            LWJGLInput.setMousePositionStatic(windowCenter);
        }
    }

    protected boolean isMouselock() {
        return mouselock;
    }

    protected void setMouselock(boolean lock) {
        this.mouselock = lock;
    }

}
