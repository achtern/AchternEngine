package io.github.achtern.AchternEngine.core.bootstrap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.lwjgl.bootstrap.LWJGLProvider;

import java.util.Map;

public class MainBindingProvider implements BindingProvider {

    public enum Bindings {
        LWJGL
    }

    protected BindingProvider binding;

    public MainBindingProvider(Bindings binding) {
        switch (binding) {
            case LWJGL:
                this.binding = new LWJGLProvider();
        }
    }

    public MainBindingProvider(BindingProvider binding) {
        this.binding = binding;
    }

    public void populateDrawStrategyFactory() {
        for (DrawStrategyFactory.Common k : getDrawStrategies().keySet()) {
            DrawStrategyFactory.put(k, getDrawStrategies().get(k));
        }
    }

    @Override
    public RenderEngine getRenderEngine() {
        return binding.getRenderEngine();
    }

    @Override
    public InputAdapter getInputAdapter() {
        return binding.getInputAdapter();
    }

    @Override
    public Map<DrawStrategyFactory.Common, DrawStrategy> getDrawStrategies() {
        return binding.getDrawStrategies();
    }
}
