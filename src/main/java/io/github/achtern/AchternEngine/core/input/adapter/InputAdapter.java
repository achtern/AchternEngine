/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.input.adapter;

import io.github.achtern.AchternEngine.core.input.Key;
import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.math.Vector2f;

/**
 * The InputAdapter is responsible for communicating between
 * the {@link io.github.achtern.AchternEngine.core.input.InputManager}
 * and the Hardware implementation (e.g. LWJGL)
 */
public interface InputAdapter {

    /**
     * The total Number of keyboard keys
     * @return total key count
     */
    public int keysTotal();

    /**
     * Trigger an update.
     * Should poll the hardware and update key states
     */
    public void update();

    /**
     * Whether the key is currently pressed
     * @param key Key to check
     * @return boolean
     */
    public boolean getKey(Key key);

    /**
     * Whether the key is currently pressed.
     * only triggers once, until the key has been
     * released and repressed
     * @param key Key to check
     * @return true once per press
     */
    public boolean getKeyDown(Key key);

    /**
     * Returns true when the key has just been released
     * @param key Key to check
     * @return true on release
     */
    public boolean getKeyUp(Key key);

    /**
     * Whether the button is currently pressed
     * @param mouseButton Button to check
     * @return boolean
     */
    public boolean getMouse(MouseButton mouseButton);

    /**
     * Whether the button is currently pressed.
     * only triggers once, until the button has been
     * released repressed
     * @param mouseButton Button to check
     * @return true once per click/press
     */
    public boolean getMouseDown(MouseButton mouseButton);


    /**
     * Returns true when the button has just been released
     * @param mouseButton Button to check
     * @return true on release
     */
    public boolean getMouseUp(MouseButton mouseButton);

    /**
     * Returns the Mouse Position on the screen.
     * @return Vector2f
     */
    public Vector2f getMousePosition();

    /**
     * Modifies the mouse cursor position
     * @param position New Position
     */
    public void setMousePosition(Vector2f position);

    /**
     * Hides the cursor, when disabled (enabled = false)
     * @param enabled true shows cursor, false hides it
     */
    public void setCursor(boolean enabled);

}
