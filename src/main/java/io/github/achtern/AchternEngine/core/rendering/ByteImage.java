package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;

import java.nio.ByteBuffer;

public class ByteImage implements TexturableData {

    protected ByteBuffer data;
    protected boolean alpha;
    protected Dimension dimension;

    public ByteImage(ByteBuffer data, boolean alpha, Dimension dimension) {
        this.data = data;
        this.alpha = alpha;
        this.dimension = dimension;
    }

    @Override
    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    @Override
    public boolean hasAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
