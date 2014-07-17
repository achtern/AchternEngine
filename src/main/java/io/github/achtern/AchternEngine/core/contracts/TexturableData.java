package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;

import java.nio.ByteBuffer;

/**
 * TextureableData has enough information to bind
 * a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
 */
public interface TexturableData {

    public int getTarget();

    public int getMinFilter();

    public int getMagFilter();

    public Format getFormat();

    public int getInternalFormat();

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
