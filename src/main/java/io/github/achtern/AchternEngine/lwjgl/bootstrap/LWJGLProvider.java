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

package io.github.achtern.AchternEngine.lwjgl.bootstrap;

import io.github.achtern.AchternEngine.core.Window;
import io.github.achtern.AchternEngine.core.bootstrap.BindingProvider;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import io.github.achtern.AchternEngine.lwjgl.LWJGLWindow;
import io.github.achtern.AchternEngine.lwjgl.input.LWJGLInput;
import io.github.achtern.AchternEngine.lwjgl.rendering.binding.LWJGLDataBinder;
import io.github.achtern.AchternEngine.lwjgl.rendering.state.LWJGLRenderEngineState;

import java.util.HashMap;
import java.util.Map;

public class LWJGLProvider implements BindingProvider {

    protected LWJGLRenderEngineState state;
    protected LWJGLDataBinder dataBinder;
    protected LWJGLInput inputAdapter;

    public LWJGLProvider() {
        this.state = new LWJGLRenderEngineState(false);
        this.dataBinder = new LWJGLDataBinder(state);
        this.inputAdapter = new LWJGLInput();
    }

    @Override
    public Window getWindow(Dimension dimension) {
        return new LWJGLWindow(dimension);
    }

    @Override
    public RenderEngineState getRenderEngineState() {
        return state;
    }

    @Override
    public DataBinder getDataBinder() {
        return dataBinder;
    }

    @Override
    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }

    @Override
    public Map<DrawStrategyFactory.Common, DrawStrategy> getDrawStrategies() {
        /*
        We do not have LWJGL specific DrawStrategies,
        just return an empty map.
         */
        return new HashMap<DrawStrategyFactory.Common, DrawStrategy>(0);
    }
}
