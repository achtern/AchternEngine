package io.github.achtern.AchternEngine.core.rendering.texture;

import io.github.achtern.AchternEngine.core.bootstrap.Native;
import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static io.github.achtern.AchternEngine.core.resource.ResourceConverter.toByteBuffer;
import static org.lwjgl.opengl.GL11.*;

public class Texture extends Dimension implements TexturableData, Native {

    public static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    public enum Type {
        TWO_DIMENSIONAL,
        THREE_DIMENSIONAL
    }


    protected int id = INVALID_ID;

    protected Type type;

    protected int minFilter;

    protected int magFilter;

    protected int internalFormat;

    protected Format format;

    protected boolean alpha;

    protected ByteBuffer data;


    public Texture(
            Dimension dimension,
            Type type,
            int minFilter,
            int magFilter,
            int internalFormat,
            Format format,
            boolean alpha,
            ByteBuffer data) {

        super(dimension);
        this.type = type;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.internalFormat = internalFormat;
        this.format = format;
        if (this.format == null) {
            this.format = alpha ? Format.RGBA : Format.RGB;
        }
        this.alpha = alpha;
        this.data = data;
    }

    public Texture(Dimension dimension, int minFilter, int magFilter, int internalFormat, Format format, boolean alpha) {
        this(
                dimension,
                Type.TWO_DIMENSIONAL,
                minFilter,
                magFilter,
                internalFormat,
                format,
                alpha,
                UBuffer.createByteBuffer(dimension.getWidth() * dimension.getHeight() * 4)
        );
    }

    public Texture(Dimension dimension) {
        this(
                dimension,
                GL_NEAREST,
                GL_NEAREST,
                GL_RGBA8,
                Format.RGBA,
                true
        );
    }

    public Texture(final BufferedImage image) {
        this(
                Dimension.fromBufferedImage(image),
                Type.TWO_DIMENSIONAL,
                GL_NEAREST,
                GL_NEAREST,
                GL_RGBA8,
                null,
                image.getColorModel().hasAlpha(),
                toByteBuffer(image)
        );
    }

    public Texture(BufferedImage image, Dimension dimension) {
        this(
                Dimension.fromBufferedImage(image),
                Type.TWO_DIMENSIONAL,
                GL_NEAREST,
                GL_NEAREST,
                GL_RGBA8,
                null,
                image.getColorModel().hasAlpha(),
                toByteBuffer(image, dimension)
        );
    }

    public Texture(TexturableData data) {
        this(
                data.getDimension(),
                data.getType(),
                data.getMinFilter(),
                data.getMagFilter(),
                data.getInternalFormat(),
                data.getFormat(),
                data.hasAlpha(),
                data.getData()
        );
    }

    public int getID() {
        return id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int getMinFilter() {
        return minFilter;
    }

    @Override
    public int getMagFilter() {
        return magFilter;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public int getInternalFormat() {
        return internalFormat;
    }

    /**
     * Width & Height
     *
     * @return the dimensions
     */
    @Override
    public Dimension getDimension() {
        return this;
    }

    /**
     * Has the image Alpha
     *
     * @return Whether alpha components are present in the buffer
     */
    @Override
    public boolean hasAlpha() {
        return alpha;
    }

    /**
     * The main image data
     *
     * @return image data
     */
    @Override
    public ByteBuffer getData() {
        return data;
    }

    /**
     * internal use only
     */
    public void setID(int id) {
        this.id = id;
    }
}
