package io.github.achtern.AchternEngine.core.contracts.abstractVersion;

import io.github.achtern.AchternEngine.core.contracts.PassFilter;
import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.scenegraph.Node;

public abstract class QuickPassFilter implements PassFilter {

    @Override
    public void init(RenderEngine renderEngine) {

    }

    @Override
    public void pre(Node node, RenderPass pass, RenderEngine renderEngine) {

    }

    @Override
    public void post(Node node, RenderPass pass, RenderEngine renderEngine) {

    }
}
