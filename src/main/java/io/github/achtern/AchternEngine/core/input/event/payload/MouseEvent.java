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

package io.github.achtern.AchternEngine.core.input.event.payload;

import io.github.achtern.AchternEngine.core.input.MouseButton;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A MouseEvent will be passed to the
 * {@link io.github.achtern.AchternEngine.core.input.event.listener.trigger.MouseButtonTrigger}
 * or {@link io.github.achtern.AchternEngine.core.input.event.listener.MouseListener}, as
 * data payload
 */
@AllArgsConstructor
@Data
public class MouseEvent implements InputEvent {

    protected final InputAdapter inputAdapter;
    protected final MouseButton button;
    protected final float delta;
    protected final Vector2f position;
    protected final Vector2f mouseDelta;

}
