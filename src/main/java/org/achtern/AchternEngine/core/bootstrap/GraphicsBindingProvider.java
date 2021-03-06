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

package org.achtern.AchternEngine.core.bootstrap;

import org.achtern.AchternEngine.core.Window;
import org.achtern.AchternEngine.core.input.adapter.InputAdapter;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.achtern.AchternEngine.core.rendering.state.RenderEngineState;

import java.util.Map;

/**
 * The GraphicsBindingProvider is used to pass graphics binding specific
 *  components from the user-land code into the core engine.
 * <br>
 *
 * This includes: <br>
 * <ul>
 *     <li>{@link org.achtern.AchternEngine.core.Window}</li>
 *     <li>{@link org.achtern.AchternEngine.core.rendering.state.RenderEngineState}</li>
 *     <li>{@link org.achtern.AchternEngine.core.rendering.binding.DataBinder}</li>
 *     <li>{@link org.achtern.AchternEngine.core.input.adapter.InputAdapter}</li>
 *     <li>{@link org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy}</li>
 * </ul>
 */
public interface GraphicsBindingProvider {

    public Window getWindow(Dimension dimension);

    public RenderEngineState getRenderEngineState();

    public DataBinder getDataBinder();

    public InputAdapter getInputAdapter();

    public Map<DrawStrategyFactory.Common, DrawStrategy> getDrawStrategies();

    public void populateDrawStrategyFactory();

}
