package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;

public interface Renderable {

    /**
     * Trigger an render.
     * Do rendering releated stuff here.
     * @param renderEngine The active RenderEngine
     */
    public void render(RenderEngine renderEngine);

}
