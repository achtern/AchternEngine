package io.github.achtern.AchternEngine.core.rendering.framebuffer;

import io.github.achtern.AchternEngine.core.bootstrap.Native;
import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
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


    protected int id = -1;

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
     * @param binder The binder used to bind the object
     */
    @Override
    public void bindAsRenderTarget(DataBinder binder) {
        binder.bindAsRenderTarget(this);
    }
}
