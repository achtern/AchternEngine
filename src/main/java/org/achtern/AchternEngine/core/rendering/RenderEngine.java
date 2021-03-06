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

package org.achtern.AchternEngine.core.rendering;

import org.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import org.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Camera;
import org.achtern.AchternEngine.core.scenegraph.entity.GlobalEntity;
import org.achtern.AchternEngine.core.util.DataStore;

public interface RenderEngine extends DataStore {

    public void render(Node node);

    public void render(Node node, boolean clear);


    public RenderEngineState getState();

    public DataBinder getDataBinder();


    public void setRenderTarget(RenderTarget target);

    public RenderTarget getRenderTarget();


    public void addRenderPass(RenderPass pass);

    public void removeRenderPass(RenderPass pass);

    public RenderPass getActiveRenderPass();

    /**
     * internal use only
     * @param active new active RenderPass
     */
    public void setActiveRenderPass(RenderPass active);



    public Camera getCamera();

    public void setCamera(Camera camera);


    public DrawStrategy getDrawStrategy();

    public void setDrawStrategy(DrawStrategy drawStrategy);


    public void addPassFilter(PassFilter filter);

    /**
     * Removes a PassFilter from the RenderEngine
     * @param filter The filter to remove
     * @return Whether the remove was successful.
     */
    public boolean removePassFilter(PassFilter filter);


    public void addGlobal(GlobalEntity entity);

    public void removeGlobal(GlobalEntity entity);

    public <T> GlobalEntity<T> getGlobal(Class<? extends T> type);
}
