package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.LWJGLRenderEngine;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.*;

public class LWJGLDataBinder implements DataBinder {

    protected LWJGLRenderEngine engine;
    protected LWJGLIDGenerator idGen;

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

        glActiveTexture(GL_TEXTURE0 + samplerslot);
        glBindTexture(GL_TEXTURE_2D, texture.getID());
    }

    @Override
    public void upload(Texture texture) {
        bind(texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, texture.getMinFilter());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, texture.getMagFilter());


        glTexImage2D(
                texture.getTarget(),
                0,
                texture.getInternalFormat(),
                texture.getDimension().getWidth(),
                texture.getDimension().getHeight(),
                0,
                getGLEnum(texture.getFormat()),
                GL_UNSIGNED_BYTE,
                texture.getData()
        );

        glTexParameteri(texture.getTarget(), GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(texture.getTarget(), GL_TEXTURE_MAX_LEVEL, 0);

    }

    @Override
    public void bind(FrameBuffer fbo) {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo.getID());
    }

    @Override
    public void upload(FrameBuffer fbo) {
        bind(fbo);

        RenderBuffer depthTarget = fbo.getDepthTarget();
        if (depthTarget != null) {
            fboUploadRenderBufferSetup(fbo, depthTarget, GL_DEPTH_COMPONENT, GL_DEPTH_ATTACHMENT);
        }

        for (int i = 0; i < fbo.sizeColorTargets(); i++) {
            RenderBuffer colorTarget = fbo.getColorTarget(i);
            if (colorTarget.getTexture() == null) {
                fboUploadRenderBufferSetup(fbo, depthTarget, 0, GL_COLOR_ATTACHMENT0 + i);
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
            getIDGenerator().generate(rbo);
            // Bind the renderbuffer
            glBindRenderbuffer(GL_RENDERBUFFER, rbo.getID());
            // TODO: allow multisample
            // tell in which format, the buffer should be stored.
            // TODO: use format from rbo.getFormat()
            glRenderbufferStorage(GL_RENDERBUFFER, iFormat, fbo.getWidth(), fbo.getHeight());
            // set the attachment type to the bound fbo
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, rbo.getID());
        } else {

            glFramebufferTexture2D(
                    GL_FRAMEBUFFER,
                    attachment,
                    rbo.getTexture().getTarget(),
                    rbo.getTexture().getID(),
                    0
            );

        }
    }

    @Deprecated
    public static void bindIt(Texture texture, int samplerslot) {
        (new LWJGLDataBinder(null)).bind(texture, samplerslot);
    }

    @Deprecated
    public static void uploadIt(Texture texture) {
        (new LWJGLDataBinder(null)).upload(texture);
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
