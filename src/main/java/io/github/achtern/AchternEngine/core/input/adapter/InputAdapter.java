package io.github.achtern.AchternEngine.core.input.adapter;

import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.math.Vector2f;

public interface InputAdapter {

    public int keysTotal();

    public void update();

    public boolean getKey(Key key);

    public boolean getKeyDown(Key key);

    public boolean getKeyUp(Key key);

    public boolean getMouse(int mouseButton);

    public boolean getMouseDown(int mouseButton);

    public boolean getMouseUp(int mouseButton);

    public Vector2f getMousePosition();

    public void setMousePosition(Vector2f position);

    public void setCursor(boolean enabled);

}
