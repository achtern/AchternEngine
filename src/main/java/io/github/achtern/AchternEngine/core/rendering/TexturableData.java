package io.github.achtern.AchternEngine.core.rendering;

import java.nio.ByteBuffer;

public interface TexturableData {

    public Dimension getDimension();

    public boolean hasAlpha();

    public ByteBuffer getData();

}
