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

package org.achtern.AchternEngine.lwjgl.rendering.state;

import org.achtern.AchternEngine.core.bootstrap.Native;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.rendering.state.*;
import org.achtern.AchternEngine.core.rendering.texture.Texture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.achtern.AchternEngine.lwjgl.util.GLEnum.getGLEnum;
import static org.lwjgl.opengl.GL11.*;

/**
 * API to talk to the OpenGL statemachine provided by LWJGL Bindings
 *
 * @see org.achtern.AchternEngine.core.rendering.state.RenderEngineState
 */
public class LWJGLRenderEngineState implements RenderEngineState {

    /**
     * Stores the version string of the OpenGL Version on the system.
     * NOT the version of LWJGL
     */
    protected String version;

    /**
     * Indicates whether or not to throw exceptions,
     * when a state changed is invoked, but the state is
     * already set on the graphics card program.
     * Useful to find useless calls, but not advised to have
     * turned on in production!
     */
    protected final boolean throwUnchanged;

    /**
     * Stores which features are enabled or disabled.
     * @see org.achtern.AchternEngine.core.rendering.state.Feature
     */
    protected Map<Feature, Boolean> enabled = new HashMap<Feature, Boolean>(Feature.values().length);

    /**
     * Stores which {@link org.achtern.AchternEngine.core.rendering.state.DepthFunction}
     * is currently active.
     */
    protected DepthFunction depthFunction = DepthFunction.LESS;

    /**
     * Stores which face is getting culled.
     */
    protected Face cullFace = Face.BACK;

    /**
     * Stores the color to clear the screen.
     */
    protected Color clearColor = Color.BLACK;

    /**
     * Stores how faces are drawn.
     */
    protected FrontFaceMethod frontFace = FrontFaceMethod.COUNTER_CLOCKWISE;

    /**
     * Stores whether or not the engine writes into the depth buffer.
     */
    protected boolean depthWrite = true;

    /**
     * Stores the 2-component blend function which is currently active.
     */
    protected BlendFunction[] blendFunction = new BlendFunction[] {
        BlendFunction.ONE,
        BlendFunction.ZERO
    };

    /**
     * Store how polygons are getting filled.
     */
    protected FillMode polygonMode = FillMode.FILL;

    /**
     * Stores the active color mask (RGBA)
     */
    protected boolean[] colorMask = new boolean[] {true, true, true, true};

    /**
     * Stores the bound framebuffer object.
     */
    protected FrameBuffer boundFbo;

    /**
     * Stores the bound {@link org.achtern.AchternEngine.core.rendering.texture.Texture}
     */
    protected Texture boundTexture;

    /**
     * Stores the bound {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh}
     */
    protected Mesh boundMesh;

    /**
     * Stores the bound {@link org.achtern.AchternEngine.core.rendering.shader.Shader}
     */
    protected Shader boundShader;

    /**
     * Constructs a new LWJGLRenderEngine state.
     *
     * Throws exceptions on unchanged state calls!
     * @see #throwUnchanged
     */
    public LWJGLRenderEngineState() {
        this(true);
    }

    public LWJGLRenderEngineState(boolean throwUnchanged) {
        this.throwUnchanged = throwUnchanged;
    }

    /**
     * Returns the OpenGL Version.
     * Calls {@link org.lwjgl.opengl.GL11#glGetString(int)}.
     * (Caches the value after on first call!)
     * @return OpenGL Version
     */
    @Override
    public String getVersion() {
        if (version == null) {
            version = glGetString(GL_VERSION);
        }
        return version;
    }

    @Override
    public void clear(boolean color, boolean depth, boolean stencil) {
        if (!color && !depth && !stencil) {
            // Do not clear anything?? weired.
            throw new IllegalArgumentException("At least one target has to be cleared" +
                    ", otherwise do not call clear()");
        }

        int mask = 0;

        if (color) {
            mask = GL_COLOR_BUFFER_BIT;
        }

        if (depth) {
            mask |= GL_DEPTH_BUFFER_BIT;
        }

        if (stencil) {
            mask |= GL_STENCIL_BUFFER_BIT;
        }

        glClear(mask);
    }

    @Override
    public void enable(Feature feature) {
        if (isEnabled(feature)) {
            if (throwUnchanged) {
                throw new IllegalStateException(feature + " is already enabled");
            } else {
                return;
            }
        }

        glEnable(getGLEnum(feature));
        enabled.put(feature, true);
    }

    @Override
    public void disable(Feature feature) {
        if (!isEnabled(feature)) {
            if (throwUnchanged) {
                throw new IllegalStateException(feature + " is not enabled");
            } else {
                return;
            }
        }
        glDisable(getGLEnum(feature));
        enabled.put(feature, false);
    }

    @Override
    public boolean isEnabled(Feature feature) {
        if (!enabled.containsKey(feature)) {
            enabled.put(feature, glGetBoolean(getGLEnum(feature)));
        }

        return enabled.get(feature);
    }

    @Override
    public void cullFace(Face face) {
        if (!isEnabled(Feature.CULL_FACE)) {
            throw new IllegalStateException("Cull Face is not enabled!");
        }

        if (face.equals(cullFace)) {
            if (throwUnchanged) {
                throw new IllegalStateException("Cull Face already " + face);
            } else {
                return;
            }
        }

        glCullFace(getGLEnum(face));
        cullFace = face;
    }

    @Override
    public Face getCullFace() {
        if (!isEnabled(Feature.CULL_FACE)) {
            throw new IllegalStateException("Cull Face is not enabled!");
        }
        return cullFace;
    }

    @Override
    public void setFrontFace(FrontFaceMethod face) {
        if (face.equals(frontFace)) {
            if (throwUnchanged) {
                throw new IllegalStateException("FrontFace already " + face);
            } else {
                return;
            }
        }

        glFrontFace(getGLEnum(face));
        frontFace = face;
    }

    @Override
    public FrontFaceMethod getFrontFace() {
        return frontFace;
    }

    @Override
    public void setClearColor(Color color) {
        if (this.clearColor.equals(color)) {
            if (throwUnchanged) {
                throw new IllegalStateException("Clear Color already " + color);
            } else {
                return;
            }
        }
        glClearColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        this.clearColor = color;
    }

    @Override
    public Color getClearColor() {
        return clearColor;
    }

    @Override
    public void setDepthFunction(DepthFunction function) {
        if (this.depthFunction.equals(function)) {
            if (throwUnchanged) {
                throw new IllegalStateException("DepthFunction is already " + function);
            } else {
                return;
            }
        }
        glDepthFunc(getGLEnum(function));
        this.depthFunction = function;
    }

    @Override
    public DepthFunction getDepthFunction() {
        return depthFunction;
    }

    @Override
    public void setBlendFunction(BlendFunction sfactor, BlendFunction dfactor) {
        if (sfactor.equals(blendFunction[0]) && dfactor.equals(blendFunction[1])) {
            if (throwUnchanged) {
                throw new IllegalStateException("BlendFunction is already " + sfactor + "-" + dfactor);
            } else {
                return;
            }
        }

        glBlendFunc(getGLEnum(sfactor), getGLEnum(sfactor));
        blendFunction[0] = sfactor;
        blendFunction[1] = dfactor;
    }

    @Override
    public BlendFunction[] getBlendFunction() {
        return blendFunction;
    }

    @Override
    public void enableDepthWrite(boolean enable) {
        if (this.depthWrite && enable) {
            if (throwUnchanged) {
                throw new IllegalStateException("DepthWriting is already enabled");
            } else {
                return;
            }
        }

        if (!this.depthWrite && !enable) {
            if (throwUnchanged) {
                throw new IllegalStateException("DepthWriting is already disabled");
            } else {
                return;
            }
        }

        glDepthMask(enable);
        depthWrite = enable;
    }

    @Override
    public boolean isDepthWrite() {
        return depthWrite;
    }

    @Override
    public void setPolygonMode(FillMode mode) {
        if (polygonMode.equals(mode)) {
            if (throwUnchanged) {
                throw new IllegalStateException("PolygonMode is already " + mode);
            } else {
                return;
            }
        }
        /*
        Only GL_FRONT_AND_BACK is allowed here!
         */
        glPolygonMode(GL_FRONT_AND_BACK, getGLEnum(mode));
        polygonMode = mode;
    }

    @Override
    public FillMode getPolygonMode() {
        return polygonMode;
    }

    @Override
    public void setColorWrite(boolean r, boolean g, boolean b, boolean a) {
        boolean[] updated = new boolean[] {r,g,b,a};
        if (Arrays.equals(updated, colorMask)) {
            if (throwUnchanged) {
                throw new IllegalStateException("ColorWrite (Mask) already <" + r + g + b + a + ">!");
            } else {
                return;
            }
        }
        glColorMask(r, g, b, a);
        this.colorMask = updated;

    }

    @Override
    public boolean[] isColorWrite() {
        return colorMask;
    }

    @Override
    public void setBound(FrameBuffer fbo) {
        if (fbo != null && fbo.getID() == Native.INVALID_ID) {
            throw new IllegalStateException("Given fbo cannot be bound (INVALID_ID)");
        }
        boundFbo = fbo;
    }

    @Override
    public FrameBuffer getBoundFbo() {
        return boundFbo;
    }

    @Override
    public void setBound(Texture texture) {
        if (texture != null && texture.getID() == Native.INVALID_ID) {
            throw new IllegalStateException("Given Texture cannot be bound (INVALID_ID)");
        }
        boundTexture = texture;
    }

    @Override
    public Texture getBoundTexture() {
        return boundTexture;
    }

    @Override
    public void setBound(Mesh mesh) {
        if (mesh != null && mesh.getData().getID() == Native.INVALID_ID) {
            throw new IllegalStateException("Given Mesh cannot be bound (INVALID_ID)");
        }
        boundMesh = mesh;
    }

    @Override
    public Mesh getBoundMesh() {
        return boundMesh;
    }

    @Override
    public void setBound(Shader shader) {
        if (shader !=null && shader.getProgram().getID() == Native.INVALID_ID) {
            throw new IllegalStateException("Given Shader cannot be bound (INVALID_ID)");
        }

        boundShader = shader;
    }

    @Override
    public Shader getBoundShader() {
        return boundShader;
    }


}
