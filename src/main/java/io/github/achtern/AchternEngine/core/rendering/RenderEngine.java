/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.util.DataStore;

public interface RenderEngine extends DataStore {

    public void render(Node node);

    public void render(Node node, boolean clear);

    public RenderEngineState getState();

    public void setRenderTarget(RenderTarget target);

    public RenderTarget getRenderTarget();

    public void addRenderPass(RenderPass pass);

    public void removeRenderPass(RenderPass pass);

    public void addCamera(Camera camera);

    public RenderPass getActiveRenderPass();

    /**
     * internal use only
     */
    public void setActiveRenderPass(RenderPass active);

    public String getOpenGLVersion();

    public Camera getMainCamera();

    public void setMainCamera(Camera mainCamera);

    public DrawStrategy getDrawStrategy();

    public void setDrawStrategy(DrawStrategy drawStrategy);

    public int getSamplerSlot(String name);

    public DataBinder getDataBinder();

    public void addPassFilter(PassFilter filter);

    /**
     * Removes a PassFilter from the RenderEngine
     * @param filter The filter to remove
     * @return Whether the remove was successful.
     */
    public boolean removePassFilter(PassFilter filter);
}
