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

package io.github.achtern.AchternEngine.core.rendering.framebuffer;

import io.github.achtern.AchternEngine.core.bootstrap.Native;
import io.github.achtern.AchternEngine.core.rendering.RenderTarget;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FrameBuffer extends Dimension implements RenderTarget, Native {

    public static final Logger LOGGER = LoggerFactory.getLogger(FrameBuffer.class);


    protected int id = INVALID_ID;

    protected int samples = 1;

    protected List<RenderBuffer> colorTargets;

    protected RenderBuffer depthTarget;

    public FrameBuffer(Dimension texture, int samples) {
        super(texture);
        if (samples < 1) {
            throw new IllegalArgumentException("sampler must be greater than 0");
        }
        this.samples = samples;
        this.colorTargets = new ArrayList<RenderBuffer>();
    }

    public FrameBuffer(Dimension dimension) {
        this(dimension, 1);
    }


    public void setDepthTarget(Format format) {
        testModify();
        depthTarget = new RenderBuffer(format);
    }

    public void setDepthTarget(Texture texture) {
        testModify();
        depthTarget = new RenderBuffer(texture);
    }

    public void setColorTarget(Format format) {
        testModify();

        RenderBuffer buffer = new RenderBuffer(format);

        colorTargets.clear();
        colorTargets.add(buffer);
    }

    public void setColorTarget(Texture texture) {
        testModify();

        clearColorTargets();
        addColorTexture(texture);
    }


    public void addColorTexture(Texture texture) {
        testModify();

        RenderBuffer buffer = new RenderBuffer(texture);

        colorTargets.add(buffer);
    }

    public void clearColorTargets() {
        colorTargets.clear();
    }

    public int sizeColorTargets() {
        return colorTargets.size();
    }

    public RenderBuffer getDepthTarget() {
        return depthTarget;
    }

    public RenderBuffer getColorTarget() {
        return getColorTarget(0);
    }

    public RenderBuffer getColorTarget(int i) {
        if (colorTargets.isEmpty()) return null;

        return colorTargets.get(i);
    }

    public List<RenderBuffer> getColorTargets() {
        return colorTargets;
    }

    public int getSamples() {
        return samples;
    }

    /**
     * internal use only
     */
    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void testModify() {
        if (getID() != -1) {
            throw new IllegalStateException("FrameBuffer already generated!");
        }
    }

    /**
     * Binds the object as render target.
     * You should avoid to use this method.
     *
     * @param binder The binder used to set the object
     */
    @Override
    public void bindAsRenderTarget(DataBinder binder) {
        binder.bindAsRenderTarget(this);
    }
}
