package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

public interface PassFilter {

    public void init(RenderEngine renderEngine);

    public void pre(Node node, RenderPass pass, RenderEngine renderEngine);

    public void post(Node node, RenderPass pass, RenderEngine renderEngine);

}
