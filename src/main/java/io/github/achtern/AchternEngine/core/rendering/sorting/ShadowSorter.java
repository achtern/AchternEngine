package io.github.achtern.AchternEngine.core.rendering.sorting;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.RenderPassSorter;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;

/**
 * Sorts a List of RenderPasses for the LWJGLRenderEngine
 * when in shadow use.
 * The ambient light should be rendered first (no shadows)
 * and the others last (with shadow generation)
 */
public class ShadowSorter implements RenderPassSorter {

    @Override
    public int compare(RenderPass o1, RenderPass o2) {
        // Both ambient => equal
        if (o1 instanceof AmbientLight && o2 instanceof AmbientLight) {
            return 0;
        }

        // Only o1 Ambient => o1 greater => 1
        if (o1 instanceof AmbientLight) {
            return 1;
        }

        // Only o2 Ambient => o1 less => -1
        if (o2 instanceof AmbientLight) {
            return -1;
        }

        // o1 nor o2 ambient. doesn't matter => equal => 0
        return 0;
    }
}
