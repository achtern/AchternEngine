package io.github.achtern.AchternEngine.core.bootstrap;

import io.github.achtern.AchternEngine.core.input.adapter.InputAdapter;
import io.github.achtern.AchternEngine.core.input.adapter.LWJGLInput;
import io.github.achtern.AchternEngine.core.rendering.LWJGLRenderEngine;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl.LWJGLSolidDraw;
import io.github.achtern.AchternEngine.core.rendering.drawing.implementations.lwjgl.LWJGLWireframeDraw;

/**
 * Name might change... (will!)
 */
public class WindowIOBindingManager {



    public enum Binding {
        LWJGL
    }

    private final Binding binding;

    public WindowIOBindingManager(Binding binding) {
        this.binding = binding;
    }
    
    public RenderEngine getRenderEngine() {
        switch (binding) {
            case LWJGL:
                return new LWJGLRenderEngine();
        }

        return null;
    }
    
    public InputAdapter getInputAdapter() {

        switch (binding) {
            case LWJGL:
                return new LWJGLInput();
        }

        return null;
    }

    public void populateDrawStrategyFactory() {

        switch (binding) {
            case LWJGL:
                DrawStrategyFactory.put(DrawStrategyFactory.Common.SOLID, new LWJGLSolidDraw());
                DrawStrategyFactory.put(DrawStrategyFactory.Common.WIREFRAME, new LWJGLWireframeDraw());
        }

    }
    
    
}
