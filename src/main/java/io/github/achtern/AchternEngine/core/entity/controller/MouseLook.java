package io.github.achtern.AchternEngine.core.entity.controller;

import io.github.achtern.AchternEngine.core.Input;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.math.Vector2f;

public class MouseLook extends QuickEntity {

    protected float sensitivity;
    protected boolean mouselock = false;
    protected int unlockKey;

    public MouseLook(float sensitivity) {
        this(sensitivity, Input.KEY_ESCAPE);
    }

    public MouseLook(float sensitivity, int unlockKey) {
        this.sensitivity = sensitivity;
        this.unlockKey = unlockKey;
    }

    @Override
    public void input(float delta)
    {
        Vector2f center = new Vector2f(Window.getWidth() / 2, Window.getHeight() / 2);

        if (Input.getKey(this.unlockKey)) {
            Input.setCursor(true);
            this.mouselock = false;
        }

        if (Input.getMouseDown(0)) {
            Input.setMousePosition(center);
            Input.setCursor(false);
            this.mouselock = true;
        }

        if(this.mouselock)
        {
            transform(center);
        }
    }

    public void transform(Vector2f windowCenter) {

        Vector2f deltaPos = Input.getMousePosition().sub(windowCenter);

        boolean rotX = deltaPos.getY() != 0;
        boolean rotY = deltaPos.getX() != 0;

        if(rotX) {
            getTransform().rotate(getTransform().getRotation().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
        }

        if(rotY) {
            getTransform().rotate(Transform.Y_AXIS, (float) Math.toRadians(deltaPos.getX() * sensitivity));
        }


        if(rotY || rotX) {
            Input.setMousePosition(windowCenter);
        }
    }

}
