package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static io.github.achtern.AchternEngine.core.resource.ResourceConverter.toByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;

public class Texture implements RenderTarget {

    public static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);

    private int[] id;

    private int fbo = -1;

    public Texture(int id) {
        this(id, 1);
    }

    public Texture(int id, int texturesCount) {
        this(id, texturesCount, new int[] {GL_NONE});
    }

    public Texture(int id, int texturesCount, int[] attachments) {
        this.id = new int[texturesCount];
        this.id[0] = id;
        initRenderTarget(attachments);
    }

    public Texture(BufferedImage image) {
        this(image, 1, new int[] {GL_NONE});
    }

    public Texture(BufferedImage image, int texturesCount, int[] attachments) {

        ByteBuffer buffer = toByteBuffer(image);

        this.id = new int[texturesCount];
        this.id[0] = genID();

        bind();

        initRenderTarget(attachments);

        int colormode = image.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;



        genTexParams();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, colormode, GL_UNSIGNED_BYTE, buffer);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, getID());
    }

    public void bindAsRenderTarget() {

        glBindFramebuffer(GL_DRAW_BUFFER, fbo);
        //TODO: Get Width and Height
        glViewport(0, 0, 0, 0);

    }

    public void initRenderTarget(int[] attachments) {

        if (attachments == null) return;

        int[] drawBuffers = new int[this.id.length];

        for (int i = 0; i < this.id.length; i++) {

            if (attachments.length > i || attachments[i] == GL_NONE) continue;

            if (attachments[i] == GL_DEPTH_ATTACHMENT || attachments[i] == GL_STENCIL_ATTACHMENT) {
                drawBuffers[i] = GL_NONE;
            } else {
                drawBuffers[i] = attachments[i];
            }


            if (this.fbo == -1) {
                this.fbo = genFBO();
                glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
            }


            glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, attachments[i], GL_TEXTURE_2D, this.id[i], 0);
        }

        if (this.fbo == -1) return;

        glDrawBuffers(UBuffer.create(drawBuffers.length));

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            LOGGER.warn("Error creating framebuffers");
        }



    }

    public int getID() {
        return id[0];
    }

    protected static int genID() {
        return glGenTextures();
    }

    protected static int genFBO() {
        return glGenFramebuffers();
    }

    protected static void genTexParams() {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }
}
