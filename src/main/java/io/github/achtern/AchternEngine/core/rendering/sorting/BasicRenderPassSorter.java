package io.github.achtern.AchternEngine.core.rendering.sorting;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.RenderPassSorter;

public class BasicRenderPassSorter implements RenderPassSorter{
    @Override
    public int compare(RenderPass o1, RenderPass o2) {
        return o1.hashCode() - o2.hashCode();
    }
}
