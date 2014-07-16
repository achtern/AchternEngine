package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.contracts.abstractVersion.CommonTexturableData;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static io.github.achtern.AchternEngine.core.resource.ResourceConverter.toByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture extends Dimension {

    public static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private int id;

    protected int target;

    public Texture(final Dimension dimension, final int target, final int filter, final int internalFormat, final int format) {
        this(new TexturableData() {
            @Override
            public int getTarget() {
                return target;
            }

            @Override
            public int getMinFilter() {
                return filter;
            }

            @Override
            public int getMagFilter() {
                return filter;
            }

            @Override
            public int getFormat() {
                return format;
            }

            @Override
            public int getInternalFormat() {
                return internalFormat;
            }

            @Override
            public Dimension getDimension() {
                return dimension;
            }

            @Override
            public boolean hasAlpha() {
                return true;
            }

            @Override
            public ByteBuffer getData() {
                // just black!
                return UBuffer.createByteBuffer(dimension.getWidth() * dimension.getHeight() * 4);
            }
        });
    }

    public Texture(final Dimension dimension) {
        this(new CommonTexturableData() {

            @Override
            public Dimension getDimension() {
                return dimension;
            }

            @Override
            public boolean hasAlpha() {
                return true;
            }

            @Override
            public ByteBuffer getData() {
                // just black!
                return UBuffer.createByteBuffer(dimension.getWidth() * dimension.getHeight() * 4);
            }
        });
    }

    public Texture(final BufferedImage image) {
        this(new CommonTexturableData() {
            @Override
            public Dimension getDimension() {
                return Dimension.fromBufferedImage(image);
            }

            @Override
            public boolean hasAlpha() {
                return image.getColorModel().hasAlpha();
            }

            @Override
            public ByteBuffer getData() {
                return toByteBuffer(image);
            }
        });
    }

    public Texture(final BufferedImage image, final Dimension dimension) {
        this(new CommonTexturableData() {
            @Override
            public Dimension getDimension() {
                return dimension.factor2();
            }

            @Override
            public boolean hasAlpha() {
                return image.getColorModel().hasAlpha();
            }

            @Override
            public ByteBuffer getData() {
                return toByteBuffer(image, dimension);
            }
        });
    }

    public Texture(TexturableData data) {
        super(data.getDimension());
        this.id = genID();

        bind();


        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, data.getMinFilter());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, data.getMagFilter());

        int format = data.getFormat();
        if (format == -1) {
            format = data.hasAlpha() ? GL_RGBA : GL_RGB;
        }

        target = data.getTarget();

        glTexImage2D(
                getTarget(),
                0,
                data.getInternalFormat(),
                data.getDimension().getWidth(),
                data.getDimension().getHeight(),
                0,
                format,
                GL_UNSIGNED_BYTE,
                data.getData()
        );

        glTexParameteri(getTarget(), GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(getTarget(), GL_TEXTURE_MAX_LEVEL, 0);

    }

    public void bind() {
        bind(0);
    }

    public void bind(int samplerslot) {
        if (samplerslot < 0) {
            throw new IllegalArgumentException("SamplerSlot MUST be a positive integer!");
        }

        glActiveTexture(GL_TEXTURE0 + samplerslot);
        glBindTexture(GL_TEXTURE_2D, getID());
    }

    public int getID() {
        return id;
    }

    public int getTarget() {
        return target;
    }

    protected static int genID() {
        return glGenTextures();
    }
}
