package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import java.nio.ByteBuffer;

/**
 * TextureableData has enough information to bind
 * a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
 */
public interface TexturableData {

    /**
     * The Texture Type
     * @return Texture Type
     */
    public Texture.Type getType();

    /**
     * MinFilter to use
     * @return OpenGL int atm.
     */
    public int getMinFilter();

    /**
     * MagFilter to use
     * @return OpenGL int atm.
     */
    public int getMagFilter();

    /**
     * Texture Format
     * @return Texture Format
     */
    public Format getFormat();

    /**
     * Internal Format
     * @return OpenGL int atm.
     */
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
