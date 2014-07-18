package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.LWJGLRenderEngine;
import io.github.achtern.AchternEngine.core.rendering.exception.FrameBufferException;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;

public class LWJGLDataBinder implements DataBinder {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataBinder.class);

    protected LWJGLRenderEngine engine;
    protected LWJGLIDGenerator idGen;

    /**
     * An IntBuffer with a size of 16.
     */
    private IntBuffer intBuffer = UBuffer.createIntBuffer(16);

    public LWJGLDataBinder(LWJGLRenderEngine engine) {
        this.engine = engine;
        this.idGen = new LWJGLIDGenerator();
    }

    @Override
    public void bind(Texture texture) {
        bind(texture, 0);
    }

    @Override
    public void bind(Texture texture, int samplerslot) {
        if (samplerslot < 0) {
            throw new IllegalArgumentException("SamplerSlot MUST be a positive integer!");
        }

        if (texture.getID() == -1) {
            getIDGenerator().generate(texture);
            upload(texture);
        }

        glActiveTexture(GL_TEXTURE0 + samplerslot);
        glBindTexture(getGLEnum(texture.getType()), texture.getID());
    }

    @Override
    public void upload(Texture texture) {
        bind(texture);

        int type = getGLEnum(texture.getType());

        glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(type, GL_TEXTURE_MIN_FILTER, texture.getMinFilter());
        glTexParameteri(type, GL_TEXTURE_MAG_FILTER, texture.getMagFilter());


        glTexImage2D(
                type,
                0,
                texture.getInternalFormat(),
                texture.getDimension().getWidth(),
                texture.getDimension().getHeight(),
                0,
                getGLEnum(texture.getFormat()),
                GL_UNSIGNED_BYTE,
                texture.getData()
        );

        glTexParameteri(type, GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(type, GL_TEXTURE_MAX_LEVEL, 0);

    }

    @Override
    public void bindAsRenderTarget(FrameBuffer fbo) {
        if (fbo.getID() == -1) {
            upload(fbo);
        }

        bind(fbo);

        /*
        If we do not have any color targets,
        set the draw buffer to none, otherwise
        artifacts might occur and we waste GPU
        bandwith!
         */
        if (fbo.sizeColorTargets() == 0) {
            glDrawBuffer(GL_NONE);
            glReadBuffer(GL_NONE);
        } else {
            /*
            else set the draw buffers to these color targets
             */

            if (fbo.sizeColorTargets() > 1) {
                intBuffer.clear();
                for (int i = 0; i < fbo.sizeColorTargets(); i++) {
                    intBuffer.put(GL_COLOR_ATTACHMENT0 + i);
                }

                intBuffer.flip();
                glDrawBuffers(intBuffer);
            } else {
                // we only have one
                glDrawBuffer(GL_COLOR_ATTACHMENT0);
            }
        }

        try {
            validateFrameBufferStatus(fbo);
        } catch (FrameBufferException e) {
            LOGGER.error("Error binding FrameBuffer", e);
        }

    }

    @Override
    public void bind(FrameBuffer fbo) {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo.getID());
        glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
    }

    @Override
    public void upload(FrameBuffer fbo) {

        if (fbo.getID() == -1) {
            getIDGenerator().generate(fbo);
        }

        bind(fbo);



        RenderBuffer depthTarget = fbo.getDepthTarget();
        if (depthTarget != null) {
            fboUploadRenderBufferSetup(fbo, depthTarget, GL_DEPTH_COMPONENT, GL_DEPTH_ATTACHMENT);
        }

        for (int i = 0; i < fbo.sizeColorTargets(); i++) {
            RenderBuffer colorTarget = fbo.getColorTarget(i);
            if (colorTarget.getTexture() == null) {
                fboUploadRenderBufferSetup(fbo, colorTarget, GL_RGBA4, GL_COLOR_ATTACHMENT0 + i);
            }
        }

    }

    @Override
    public IDGenerator getIDGenerator() {
        return idGen;
    }

    protected void fboUploadRenderBufferSetup(FrameBuffer fbo, RenderBuffer rbo, int iFormat, int attachment) {
        if (rbo.getTexture() == null) {
            // Generate an ID for the renderbuffer
            if (rbo.getID() == -1) {
                getIDGenerator().generate(rbo);
            }
            // Bind the renderbuffer
            glBindRenderbuffer(GL_RENDERBUFFER, rbo.getID());
            // TODO: allow multisample
            // tell in which format, the buffer should be stored.
            // TODO: use format from rbo.getFormat()
            glRenderbufferStorage(GL_RENDERBUFFER, iFormat, fbo.getWidth(), fbo.getHeight());
            // set the attachment type to the bound fbo
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, rbo.getID());
        } else {

            if (rbo.getTexture().getID() == -1) {
                // upload texture
                upload(rbo.getTexture());
            }

            glFramebufferTexture2D(
                    GL_FRAMEBUFFER,
                    attachment,
                    getGLEnum(rbo.getTexture().getType()),
                    rbo.getTexture().getID(),
                    0
            );

        }
    }

    protected void validateFrameBufferStatus(FrameBuffer fbo) throws FrameBufferException {
        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);

        switch (status) {
            case GL_FRAMEBUFFER_COMPLETE:
                break;
            case GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
                throw new FrameBufferException("Incomplete or corrupt attachments");
            case GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
                throw new FrameBufferException("FrameBuffer doesn't have any RenderBuffers attached");
            case GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
                throw new FrameBufferException("Incomplete DrawBuffer setup");
            case GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
                throw new FrameBufferException("Incomplete Multisample setup");
            case GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
                throw new FrameBufferException("Incomplete ReadBuffer setup");
            default:
                throw new UnsupportedOperationException("Unknown FBO status Code " + status);
        }
    }

    protected static int getGLEnum(Texture.Type type) {
        switch (type) {
            case TWO_DIMENSIONAL:
                return GL_TEXTURE_2D;
            case THREE_DIMENSIONAL:
                return GL_TEXTURE_3D;
            default:
                // TODO: implement the other types
                throw new UnsupportedOperationException("Texture type " + type + " not supported by LWJGL!");
        }
    }


    protected static int getGLEnum(Format format) {
        switch (format) {
            case RGBA:
                return GL_RGBA;
            case RGB:
                return GL_RGB;
            case DEPTH:
                return GL_DEPTH_COMPONENT;

            default:
                throw new UnsupportedOperationException("Format not supported");
        }
    }
}
