package io.github.achtern.AchternEngine.core.input.adapter;

import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.math.Vector2f;

public interface InputAdapter {

    public int keysTotal();

    public void update();

    public boolean getKey(Key key);

    public boolean getKeyDown(Key key);

    public boolean getKeyUp(Key key);

    public boolean getMouse(MouseButton mouseButton);

    public boolean getMouseDown(MouseButton mouseButton);

    public boolean getMouseUp(MouseButton mouseButton);

    public Vector2f getMousePosition();

    public void setMousePosition(Vector2f position);

    public void setCursor(boolean enabled);

}
