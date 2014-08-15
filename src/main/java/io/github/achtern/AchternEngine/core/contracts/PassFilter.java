package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

/**
 * A PassFilter can perform stuff during render time.
 * A PassFilter runs before (pre) and after (post)
 * each forward rendererd pass.
 */
public interface PassFilter {

    /**
     * Once added to the {@link RenderEngine}, this methods gets called.
     * Initialize FrameBuffers etc. in this method.
     * @param renderEngine The active RenderEngine
     */
    public void init(RenderEngine renderEngine);

    /**
     * Called before the pass.
     * (activeRenderPass already set, when calling this method)
     * @param node Node which gets rendered
     * @param pass The active RenderPass
     * @param renderEngine The active RenderEngine
     */
    public void pre(Node node, RenderPass pass, RenderEngine renderEngine);

    /**
     * Called after the pass.
     * @param node Node which got rendered
     * @param pass The "active" RenderPass which was used to render
     * @param renderEngine The active RenderEngine
     */
    public void post(Node node, RenderPass pass, RenderEngine renderEngine);

}
