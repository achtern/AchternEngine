package io.github.achtern.AchternEngine.core.rendering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static io.github.achtern.AchternEngine.core.resource.ResourceConverter.toByteBuffer;
import static org.lwjgl.opengl.GL11.*;

public class Texture {

    public static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private int id;


    public Texture(int id) {
        this.id = id;
    }

    public Texture(BufferedImage image) {
        this(toByteBuffer(image), Dimension.fromBufferedImage(image), image.getColorModel().hasAlpha());
    }

    public Texture(TexturableData data) {
        this(data.getData(), data.getDimension(), data.hasAlpha());
    }

    public Texture(ByteBuffer buffer, Dimension dimension, boolean alpha) {

        this.id = genID();

        bind();


        genTexParams();

        glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA8,
                dimension.getWidth(),
                dimension.getHeight(),
                0,
                alpha ? GL_RGBA : GL_RGB,
                GL_UNSIGNED_BYTE,
                buffer
        );
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, getID());
    }

    public int getID() {
        return id;
    }

    protected static int genID() {
        return glGenTextures();
    }

    protected static void genTexParams() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }
}
