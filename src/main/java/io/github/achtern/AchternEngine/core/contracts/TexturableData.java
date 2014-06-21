package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.Dimension;

import java.nio.ByteBuffer;

/**
 * TextureableData has enough information to bind
 * a {@link io.github.achtern.AchternEngine.core.rendering.Texture}.
 */
public interface TexturableData {

    /**
     * Width & Height
     * @return the dimensions
     */
    public Dimension getDimension();

    /**
     * Has the image Alpha
     * @return Whether alpha components are present in the buffer
     */
    public boolean hasAlpha();

    /**
     * The main image data
     * @return image data
     */
    public ByteBuffer getData();

}
