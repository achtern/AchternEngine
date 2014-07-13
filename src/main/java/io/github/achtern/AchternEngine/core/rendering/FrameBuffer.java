package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

public class FrameBuffer extends Dimension implements RenderTarget {

    public static final Logger LOGGER = LoggerFactory.getLogger(FrameBuffer.class);

    private static final int TARGET = GL_FRAMEBUFFER;

    protected int id;
    protected int renderBuffer;
    protected Texture texture;

    public FrameBuffer(Texture texture) {
        super(texture);
        this.texture = texture;

        texture.bind();
        id = genID();
        renderBuffer = genRID();

        setup();

    }

    public FrameBuffer(Dimension dimension) {
        this(new Texture(dimension));
    }

    public void bindAsRenderTarget() {
        glViewport(0, 0, getWidth(), getHeight());
        glBindFramebuffer(TARGET, getID());
    }

    public void setup() {
        int attachment = GL_COLOR_ATTACHMENT0;

        glBindFramebuffer(TARGET, getID());
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, getWidth(), getHeight());
        glFramebufferRenderbuffer(TARGET, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);


        glFramebufferTexture2D(TARGET, attachment, texture.getTarget(), texture.getID(), 0);

        int response = glCheckFramebufferStatus(TARGET);

        if (response != GL_FRAMEBUFFER_COMPLETE) {
            LOGGER.warn("FrameBuffer could not be bound successfully, error code {}. unbinding...", response);
            glDeleteFramebuffers(getID());
        }

        glBindFramebuffer(TARGET, 0);

    }

    protected static int genID() {
        return glGenFramebuffers();
    }

    protected static int genRID() {
        return glGenRenderbuffers();
    }


    public int getID() {
        return id;
    }

    public Texture getTexture() {
        return texture;
    }



}
