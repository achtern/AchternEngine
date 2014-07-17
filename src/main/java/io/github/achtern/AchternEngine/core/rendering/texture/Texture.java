package io.github.achtern.AchternEngine.core.rendering.texture;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.binding.LWJGLDataBinder;
import io.github.achtern.AchternEngine.core.rendering.binding.LWJGLIDGenerator;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static io.github.achtern.AchternEngine.core.resource.ResourceConverter.toByteBuffer;
import static org.lwjgl.opengl.GL11.*;

public class Texture extends Dimension implements TexturableData {

    public static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private int id;

    protected int target;

    protected int minFilter;

    protected int magFilter;

    protected int internalFormat;

    protected Format format;

    protected boolean alpha;

    protected ByteBuffer data;


    public Texture(
            Dimension dimension,
            int target,
            int minFilter,
            int magFilter,
            int internalFormat,
            Format format,
            boolean alpha,
            ByteBuffer data) {

        super(dimension);
        this.target = target;
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.internalFormat = internalFormat;
        this.format = format;
        if (this.format == null) {
            this.format = alpha ? Format.RGBA : Format.RGB;
        }
        this.alpha = alpha;
        this.data = data;

        LWJGLIDGenerator.generateIt(this);
        LWJGLDataBinder.uploadIt(this);
    }

    public Texture(Dimension dimension) {
        this(
                dimension,
                GL_TEXTURE_2D,
                GL_NEAREST,
                GL_NEAREST,
                GL_RGBA8,
                Format.RGBA,
                true,
                UBuffer.createByteBuffer(dimension.getWidth() * dimension.getHeight() * 4)
        );
    }

    public Texture(final BufferedImage image) {
        this(
                Dimension.fromBufferedImage(image),
                GL_TEXTURE_2D,
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
                GL_TEXTURE_2D,
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
                data.getTarget(),
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

    public int getTarget() {
        return target;
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
     * Internal use only
     * @param id ID
     */
    public void setID(int id) {
        this.id = id;
    }
}
