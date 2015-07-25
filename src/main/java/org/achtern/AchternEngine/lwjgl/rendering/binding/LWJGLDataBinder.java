/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.achtern.AchternEngine.lwjgl.rendering.binding;

import org.achtern.AchternEngine.core.rendering.Vertex;
import org.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.achtern.AchternEngine.core.rendering.binding.IDGenerator;
import org.achtern.AchternEngine.core.rendering.binding.UniformManager;
import org.achtern.AchternEngine.core.rendering.exception.FrameBufferException;
import org.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import org.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.rendering.mesh.MeshData;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import org.achtern.AchternEngine.core.rendering.texture.Texture;
import org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;
import org.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.achtern.AchternEngine.core.bootstrap.Native.INVALID_ID;
import static org.achtern.AchternEngine.lwjgl.util.GLEnum.getGLEnum;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LWJGLDataBinder implements DataBinder {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataBinder.class);

    protected LWJGLIDGenerator idGen;
    protected LWJGLUniformManager uniformManager;
    protected RenderEngineState state;

    /**
     * An IntBuffer with a size of 16.
     * This buffer is used to set draw buffers,
     *  since glDrawBuffers only accepts an IntBuffer when dealing
     *  with multiple color attachments.
     */
    private IntBuffer intBuffer = UBuffer.createIntBuffer(16);

    public LWJGLDataBinder(RenderEngineState state) {
        this.state = state;
        this.idGen = new LWJGLIDGenerator();
        this.uniformManager = new LWJGLUniformManager();
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

        if (texture.getID() == INVALID_ID) {
            getIDGenerator().generate(texture);
            upload(texture);
        }

        if (state.getBoundTexture() != null && state.getBoundTexture().getID() == texture.getID()) {
            return;
        }

        int type = getGLEnum(texture.getType());

        LOGGER.trace("Calling glActiveTexture({})", GL_TEXTURE0 + samplerslot);
        glActiveTexture(GL_TEXTURE0 + samplerslot);
        LOGGER.trace("Calling glBindTexture({}, {})", type, texture.getID());
        glBindTexture(type, texture.getID());
        state.setBound(texture);
    }

    @Override
    public void upload(Texture texture) {
        bind(texture);

        int type = getGLEnum(texture.getType());

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Calling glTexParamteri() 4 times, CLAMP_TO_EDGE and MIN/MAG Filter {}/{}",
                    getGLEnum(texture.getMinFilter()),
                    getGLEnum(texture.getMagFilter())
            );
        }

        glTexParameteri(type, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(type, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(type, GL_TEXTURE_MIN_FILTER, getGLEnum(texture.getMinFilter()));
        glTexParameteri(type, GL_TEXTURE_MAG_FILTER, getGLEnum(texture.getMagFilter()));


        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Calling glTexImage2D({}, 0, {}, {}, {}, 0, {}, GL_UNSIGNED_BYTE, {})",
                    type,
                    0,
                    getGLEnum(texture.getInternalFormat()),
                    texture.getDimension().getWidth(),
                    texture.getDimension().getHeight(),
                    getGLEnum(texture.getFormat()),
                    texture.getData()
            );
        }

        glTexImage2D(
                type,
                0,
                getGLEnum(texture.getInternalFormat()),
                texture.getDimension().getWidth(),
                texture.getDimension().getHeight(),
                0,
                getGLEnum(texture.getFormat()),
                GL_UNSIGNED_BYTE,
                texture.getData()
        );

        LOGGER.trace("Calling glTexParamteri({}, GL_TEXTURE_BASE_LEVEL, 0)", type);
        glTexParameteri(type, GL_TEXTURE_BASE_LEVEL, 0);
        LOGGER.trace("Calling glTexParamteri({}, GL_TEXTURE_MAX_LEVEL, 0)", type);
        glTexParameteri(type, GL_TEXTURE_MAX_LEVEL, 0);

    }

    @Override
    public void bind(Mesh mesh) {
        if (mesh == null) {
            LOGGER.trace("Calling glBindVertexArray(0)");
            glBindVertexArray(0);
        } else {
            int id = mesh.getData().getID();
            if (state.getBoundMesh() != null && state.getBoundMesh().getData().getID() == id) {
                return;
            }
            LOGGER.trace("Calling glBindVertexArray({})", id);
            glBindVertexArray(id);
        }
        state.setBound(mesh);
    }

    @Override
    public void upload(Mesh mesh) {
        MeshData data = mesh.getData();
        if (data.getID() != INVALID_ID) {
            LOGGER.warn("MeshData already uploaded to context. Re-uploading...");
        }

        getIDGenerator().generate(mesh);

        bind(mesh);

        LOGGER.trace("Calling glBindBuffer(GL_ARRAY_BUFFER, {})", data.getVbo());
        glBindBuffer(GL_ARRAY_BUFFER, data.getVbo());

        LOGGER.trace("Calling glBufferData(GL_ARRAY_BUFFER, <data=vertices>, GL_STATIC_DRAW)");
        glBufferData(GL_ARRAY_BUFFER, (FloatBuffer) UBuffer.create(data.getVertices()).flip(), GL_STATIC_DRAW);

        LOGGER.trace("Calling glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, {})", data.getIbo());
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.getIbo());
        LOGGER.trace("Calling glBufferData(GL_ELEMENT_ARRAY_BUFFER, <data=indices>, GL_STATIC_DRAW)");
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) UBuffer.create(data.getIndices()).flip(), GL_STATIC_DRAW);

        LOGGER.trace("Calling glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4 = {}, 0)", Vertex.SIZE * 4);
        // Position
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        LOGGER.trace("Calling glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4 = {}, 12)", Vertex.SIZE * 4);
        // Texture Coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        LOGGER.trace("Calling glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4 = {}, 20)", Vertex.SIZE * 4);
        // Normals
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        LOGGER.trace("Calling glEnableVertexAttribArray(0)");
        // Position
        glEnableVertexAttribArray(0);
        LOGGER.trace("Calling glEnableVertexAttribArray(1)");
        // Texture Coordinates
        glEnableVertexAttribArray(1);
        LOGGER.trace("Calling glEnableVertexAttribArray(2)");
        // Normals
        glEnableVertexAttribArray(2);

        // Unbind
        bind((Mesh) null);

    }

    @Override
    public void draw(Mesh mesh) {
        if (mesh.getData().getID() == INVALID_ID) {
            upload(mesh);
        }
        bind(mesh);

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Calling glDrawElements({}, {}, GL_UNSIGNED_INT, 0)",
                    getGLEnum(mesh.getData().getMode()),
                    mesh.getData().getSize()
            );
        }

        glDrawElements(getGLEnum(mesh.getData().getMode()), mesh.getData().getSize(), GL_UNSIGNED_INT, 0);
    }

    @Override
    public void bind(Shader shader) {
        if (shader.getProgram().getID() == INVALID_ID) {
            upload(shader);
        }
        if (state.getBoundShader() != null && state.getBoundShader().getProgram().getID() == shader.getProgram().getID()) {
            return;
        }
        LOGGER.trace("Calling glUseProgram({})", shader.getProgram().getID());
        glUseProgram(shader.getProgram().getID());
        state.setBound(shader);
    }

    @Override
    public void upload(Shader shader) {
        GLSLProgram program = shader.getProgram();

        if (program.getID() == INVALID_ID) {
            getIDGenerator().generate(shader);
        }

        // Attach all shader sources
        for (GLSLScript script : program.getScripts()) {

            LOGGER.trace("Calling glShaderSource({}, <data=shader_source>)", script.getID());
            glShaderSource(script.getID(), script.getSource());
            LOGGER.trace("Calling glCompileShader({})", script.getID());
            glCompileShader(script.getID());

            LOGGER.trace("Calling glGetShaderi({}, GL_COMPILE_STATUS)", script.getID());
            if (glGetShaderi(script.getID(), GL_COMPILE_STATUS) == 0) {
                LOGGER.warn(glGetShaderInfoLog(script.getID(), 1024));
            }

            LOGGER.trace("Calling glAttachShader({}, {})", program.getID(), script.getID());
            glAttachShader(program.getID(), script.getID());
        }

        LOGGER.trace("Calling glLinkProgram({})", program.getID());
        // compile
        glLinkProgram(program.getID());

        LOGGER.trace("Calling glGetProgrami({}, GL_LINK_STATUS)", program.getID());
        if (glGetProgrami(program.getID(), GL_LINK_STATUS) == 0) {
            LOGGER.trace("Calling glGetProgramInfoLog({}, 1024)", program.getID());
            LOGGER.warn("Link Status: {} @ {}",
                    glGetProgramInfoLog(program.getID(), 1024),
                    shader.getClass().getSimpleName()
            );
        }

        LOGGER.trace("Calling glValidateProgram({})", program.getID());
        glValidateProgram(program.getID());

        LOGGER.trace("Calling glGetProgrami({}, GL_VALIDATE_STATUS)", program.getID());
        if (glGetProgrami(program.getID(), GL_VALIDATE_STATUS) == 0) {
            LOGGER.trace("Calling glGetProgramInfoLog({}, 1024)", program.getID());
            String error = glGetProgramInfoLog(program.getID(), 1024);
            // This is a hack to prevent error message on every shader load.
            // If we load the shaders before the mesh loading, there are not any
            // VAO loaded. Works fine everytime, so just ignore this case specific error...
            if (!error.contains("Validation Failed: No vertex array object bound")) {
                LOGGER.warn("Validation Status: {} @ {}", error, shader.getClass().getSimpleName());
            }
        }

        // addUniforms
        getUniformManager().addUniforms(shader);

        /*
        We add these attributes just quickly here, no need to designate
        a hole class for this simple step!
         */
        for (GLSLScript script : program.getScripts()) {
            int loc = 0;

            for (Variable attr : script.getAttributes()) {
                LOGGER.debug("{}: attribute {} got added at {}", this.getClass().getSimpleName(), attr.getName(), loc);
                LOGGER.trace("Calling glBindAttribLocation({}, {}, {})", program.getID(), loc, attr.getName());
                glBindAttribLocation(program.getID(), loc, attr.getName());
                loc++;
            }
        }
    }

    @Override
    public void bindAsRenderTarget(FrameBuffer fbo) {
        if (fbo == null) {
            state.setBound((FrameBuffer) null);
            LOGGER.trace("Calling glDrawBuffer(GL_NONE)");
            glDrawBuffer(GL_NONE);
            LOGGER.trace("Calling glReadBuffer(GL_NONE)");
            glReadBuffer(GL_NONE);
            return;
        }
        if (state.getBoundFbo() != null && state.getBoundFbo().getID() == fbo.getID()) {
            return;
        }

        if (fbo.getID() == INVALID_ID) {
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
            LOGGER.trace("Calling glDrawBuffer(GL_NONE)");
            glDrawBuffer(GL_NONE);
            LOGGER.trace("Calling glReadBuffer(GL_NONE)");
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
                LOGGER.trace("Calling glDrawBuffers(<data=color_attachments>)");
                glDrawBuffers(intBuffer);
            } else {
                // we only have one
                LOGGER.trace("Calling glDrawBuffer(GL_COLOR_ATTACHMENT0)");
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
        if (state.getBoundFbo() != null && state.getBoundFbo().getID() == fbo.getID()) {
            return;
        }
        LOGGER.trace("Calling glBindFramebuffer(GL_FRAMEBUFFER, {})", fbo.getID());
        glBindFramebuffer(GL_FRAMEBUFFER, fbo.getID());
        LOGGER.trace("Calling glViewport(0, 0, {}, {}", fbo.getWidth(), fbo.getHeight());
        glViewport(0, 0, fbo.getWidth(), fbo.getHeight());
        state.setBound(fbo);
    }

    @Override
    public void upload(FrameBuffer fbo) {

        if (fbo.getID() == INVALID_ID) {
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

    @Override
    public UniformManager getUniformManager() {
        return uniformManager;
    }

    @Override
    public RenderEngineState getState() {
        return state;
    }

    protected void fboUploadRenderBufferSetup(FrameBuffer fbo, RenderBuffer rbo, int iFormat, int attachment) {
        if (rbo.getTexture() == null) {
            // Generate an ID for the renderbuffer
            if (rbo.getID() == INVALID_ID) {
                getIDGenerator().generate(rbo);
            }
            // Bind the renderbuffer
            LOGGER.trace("Calling glBindRenderbuffer(GL_RENDERBUFFER, {})", rbo.getID());
            glBindRenderbuffer(GL_RENDERBUFFER, rbo.getID());
            // TODO: allow multisample
            // tell in which format, the buffer should be stored.
            // TODO: use format from rbo.getFormat()
            LOGGER.trace("Calling glRenderbufferStorage(GL_RENDERBUFFER, {}, {}, {})", iFormat, fbo.getWidth(), fbo.getHeight());
            glRenderbufferStorage(GL_RENDERBUFFER, iFormat, fbo.getWidth(), fbo.getHeight());
            // set the attachment type to the bound fbo
            LOGGER.trace("Calling glFramebufferRenderbuffer(GL_FRAMEBUFFER, {}, GL_RENDERBUFFER, {})",
                    attachment,
                    rbo.getID()
            );
            glFramebufferRenderbuffer(GL_FRAMEBUFFER, attachment, GL_RENDERBUFFER, rbo.getID());
        } else {

            if (rbo.getTexture().getID() == INVALID_ID) {
                // upload texture
                upload(rbo.getTexture());
            }

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Calling glFramebufferTexture2D(GL_FRAMEBUFFER, {}, {}, {}, {}, 0)",
                        attachment,
                        getGLEnum(rbo.getTexture().getType()),
                        rbo.getTexture().getID()
                );
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
        LOGGER.trace("Calling glCheckFramebufferStatus(GL_FRAMEBUFFER)");
        int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
        LOGGER.trace("-> got {}", status);

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
}
