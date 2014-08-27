package io.github.achtern.AchternEngine.core.bootstrap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;

import java.util.Map;

public interface BindingProvider {

    public RenderEngine getRenderEngine();

    public InputAdapter getInputAdapter();

    public Map<DrawStrategyFactory.Common, DrawStrategy> getDrawStrategies();

}
