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

package io.github.achtern.AchternEngine.core.rendering;

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
