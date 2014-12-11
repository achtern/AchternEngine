/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.achtern.AchternEngine.core.rendering.sorting;

import org.achtern.AchternEngine.core.rendering.RenderPass;
import org.achtern.AchternEngine.core.rendering.RenderPassSorter;
import org.achtern.AchternEngine.core.rendering.shadow.ShadowInfo;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.BaseLight;

/**
 * Sorts a List of RenderPasses for the BasicRenderEngine
 * with lights without {@link org.achtern.AchternEngine.core.rendering.shadow.ShadowInfo} first.
 */
public class NoShadowFirstSorter implements RenderPassSorter {
    @Override
    public int compare(RenderPass o1, RenderPass o2) {
        if (!(o1 instanceof BaseLight) && !(o2 instanceof BaseLight)) {
            return 0; // doesn't matter
        }

        if (o1 instanceof BaseLight && !(o2 instanceof BaseLight)) {
            // o2 CANNOT have shadow info
            return -1;
        }

        if (!(o1 instanceof BaseLight)) {
            // o1 CANNOT have shadow info
            return 1;
        }

        ShadowInfo i1 = ((BaseLight) o1).getShadowInfo();
        ShadowInfo i2 = ((BaseLight) o2).getShadowInfo();

        if (i1 != null && i2 != null) {
            return 0; // doesn't matter
        }

        if (i1 == null && i2 == null) {
            return 0; // doesn't matter
        }

        if (i1 == null) {
            return 1;
        }

        return 0;
    }
}
