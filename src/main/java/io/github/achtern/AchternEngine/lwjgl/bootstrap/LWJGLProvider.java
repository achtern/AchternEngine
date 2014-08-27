package io.github.achtern.AchternEngine.lwjgl.bootstrap;

import io.github.achtern.AchternEngine.core.bootstrap.BindingProvider;
import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.lwjgl.input.LWJGLInput;
import io.github.achtern.AchternEngine.lwjgl.rendering.LWJGLRenderEngine;
import io.github.achtern.AchternEngine.lwjgl.rendering.LWJGLSolidDraw;
import io.github.achtern.AchternEngine.lwjgl.rendering.LWJGLWireframeDraw;

import java.util.HashMap;
import java.util.Map;

public class LWJGLProvider implements BindingProvider {

    @Override
    public RenderEngine getRenderEngine() {
        return new LWJGLRenderEngine();
    }

    @Override
    public InputAdapter getInputAdapter() {
        return new LWJGLInput();
    }

    @Override
    public Map<DrawStrategyFactory.Common, DrawStrategy> getDrawStrategies() {
        final Map<DrawStrategyFactory.Common, DrawStrategy> r = new HashMap<DrawStrategyFactory.Common, DrawStrategy>(2);

        r.put(DrawStrategyFactory.Common.SOLID, new LWJGLSolidDraw());
        r.put(DrawStrategyFactory.Common.WIREFRAME, new LWJGLWireframeDraw());

        return r;
    }
}
