/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

package io.github.achtern.AchternEngine.lwjgl.rendering.state;

import io.github.achtern.AchternEngine.core.bootstrap.Native;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.state.*;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;


public class LWJGLRenderEngineState implements RenderEngineState {

    public static final String OPENGL_VERSION = glGetString(GL_VERSION);

    protected final boolean throwUnchanged;

    protected Map<Feature, Boolean> enabled = new HashMap<Feature, Boolean>(Feature.values().length);

    protected DepthFunction depthFunction = DepthFunction.LESS;

    protected CullFace cullFace = CullFace.BACK;

    protected Color clearColor = Color.BLACK;

    protected FrontFaceMethod frontFace = FrontFaceMethod.COUNTER_CLOCKWISE;

    protected boolean depthWrite = true;

    protected BlendFunction[] blendFunction = new BlendFunction[] {
        BlendFunction.ONE,
        BlendFunction.ZERO
    };

    protected FrameBuffer boundFbo;

    protected Texture boundTexture;

    protected Mesh boundMesh;

    protected Shader boundShader;

    public LWJGLRenderEngineState() {
        this(true);
    }

    public LWJGLRenderEngineState(boolean throwUnchanged) {
        this.throwUnchanged = throwUnchanged;
    }

    @Override
    public String getVersion() {
        return OPENGL_VERSION;
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
    public void cullFace(CullFace face) {
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
    public CullFace getCullFace() {
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
    public void setBound(FrameBuffer fbo) {
        if (fbo.getID() == Native.INVALID_ID) {
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
        if (texture.getID() == Native.INVALID_ID) {
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
        if (mesh.getData().getID() == Native.INVALID_ID) {
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
        if (shader.getProgram().getID() == Native.INVALID_ID) {
            throw new IllegalStateException("Given Shader cannot be bound (INVALID_ID)");
        }

        boundShader = shader;
    }

    @Override
    public Shader getBoundShader() {
        return boundShader;
    }

    protected int getGLEnum(Feature feature) {
        switch (feature) {
            case DEPTH_CLAMP:
                return GL_DEPTH_CLAMP;
            case ALPHA_TEST:
                return GL_ALPHA_TEST;
            case AUTO_NORMAL:
                return GL_AUTO_NORMAL;
            case BLEND:
                return GL_BLEND;
            case COLOR_LOGIC_OP:
                return GL_COLOR_LOGIC_OP;
            case COLOR_MATERIAL:
                return GL_COLOR_MATERIAL;
            case COLOR_SUM:
                return GL_COLOR_SUM;
            case CULL_FACE:
                return GL_CULL_FACE;
            case DEPTH_TEST:
                return GL_DEPTH_TEST;
            case DITHER:
                return GL_DITHER;
            case FOG:
                return GL_FOG;
            case INDEX_LOGIC_OP:
                return GL_INDEX_LOGIC_OP;
            case LIGHTING:
                return GL_LIGHTING;
            case LINE_SMOOTH:
                return GL_LINE_SMOOTH;
            case LINE_STIPPLE:
                return GL_LINE_STIPPLE;
            case MAP1_COLOR_4:
                return GL_MAP1_COLOR_4;
            case MAP1_INDEX:
                return GL_MAP1_INDEX;
            case MAP1_NORMAL:
                return GL_MAP1_NORMAL;
            case MAP1_TEXTURE_COORD_1:
                return GL_MAP1_TEXTURE_COORD_1;
            case MAP1_TEXTURE_COORD_2:
                return GL_MAP1_TEXTURE_COORD_2;
            case MAP1_TEXTURE_COORD_3:
                return GL_MAP1_TEXTURE_COORD_3;
            case MAP1_TEXTURE_COORD_4:
                return GL_MAP1_TEXTURE_COORD_4;
            case MAP1_VERTEX_3:
                return GL_MAP1_VERTEX_3;
            case MAP1_VERTEX_4:
                return GL_MAP1_VERTEX_4;
            case MAP2_COLOR_4:
                return GL_MAP2_COLOR_4;
            case MAP2_INDEX:
                return GL_MAP2_INDEX;
            case MAP2_NORMAL:
                return GL_MAP2_NORMAL;
            case MAP2_TEXTURE_COORD_1:
                return GL_MAP2_TEXTURE_COORD_1;
            case MAP2_TEXTURE_COORD_2:
                return GL_MAP2_TEXTURE_COORD_2;
            case MAP2_TEXTURE_COORD_3:
                return GL_MAP2_TEXTURE_COORD_3;
            case MAP2_TEXTURE_COORD_4:
                return GL_MAP2_TEXTURE_COORD_4;
            case MAP2_VERTEX_3:
                return GL_MAP2_VERTEX_3;
            case MAP2_VERTEX_4:
                return GL_MAP2_VERTEX_4;
            case MULTISAMPLE:
                return GL_MULTISAMPLE;
            case NORMALIZE:
                return GL_NORMALIZE;
            case POINT_SMOOTH:
                return GL_POINT_SMOOTH;
            case POINT_SPRITE:
                return GL_POINT_SPRITE;
            case POLYGON_OFFSET_FILL:
                return GL_POLYGON_OFFSET_FILL;
            case POLYGON_OFFSET_LINE:
                return GL_POLYGON_OFFSET_LINE;
            case POLYGON_OFFSET_POINT:
                return GL_POLYGON_OFFSET_POINT;
            case POLYGON_SMOOTH:
                return GL_POLYGON_SMOOTH;
            case POLYGON_STIPPLE:
                return GL_POLYGON_STIPPLE;
            case RESCALE_NORMAL:
                return GL_RESCALE_NORMAL;
            case SAMPLE_ALPHA_TO_COVERAGE:
                return GL_SAMPLE_ALPHA_TO_COVERAGE;
            case SAMPLE_ALPHA_TO_ONE:
                return GL_SAMPLE_ALPHA_TO_ONE;
            case SAMPLE_COVERAGE:
                return GL_SAMPLE_COVERAGE;
            case SCISSOR_TEST:
                return GL_SCISSOR_TEST;
            case STENCIL_TEST:
                return GL_STENCIL_TEST;
            case TEXTURE_1D:
                return GL_TEXTURE_1D;
            case TEXTURE_2D:
                return GL_TEXTURE_2D;
            case TEXTURE_3D:
                return GL_TEXTURE_3D;
            case TEXTURE_CUBE_MAP:
                return GL_TEXTURE_CUBE_MAP;
            case TEXTURE_GEN_Q:
                return GL_TEXTURE_GEN_Q;
            case TEXTURE_GEN_R:
                return GL_TEXTURE_GEN_R;
            case TEXTURE_GEN_S:
                return GL_TEXTURE_GEN_S;
            case TEXTURE_GEN_T:
                return GL_TEXTURE_GEN_T;
            case VERTEX_PROGRAM_POINT_SIZE:
                return GL_VERTEX_PROGRAM_POINT_SIZE;
            case VERTEX_PROGRAM_TWO_SIDE:
                return GL_VERTEX_PROGRAM_TWO_SIDE;
            default:
                throw new UnsupportedOperationException("Feature " + feature + " not supported in LWJGLRenderEngineState");
        }
    }

    protected int getGLEnum(CullFace face) {
        switch (face) {
            case BACK:
                return GL_BACK;
            case FRONT:
                return GL_FRONT;
            case ALL:
                return GL_FRONT_AND_BACK;
            default:
                throw new UnsupportedOperationException("CullFace " + face + " not supported in LWJGLRenderEngineState");
        }
    }

    protected int getGLEnum(DepthFunction function) {
        switch (function) {
            case NEVER:
                return GL_NEVER;
            case LESS:
                return GL_LESS;
            case EQUAL:
                return GL_EQUAL;
            case LESS_OR_EQUAL:
                return GL_LEQUAL;
            case GREATER:
                return GL_GREATER;
            case NOT_EQUAL:
                return GL_NOTEQUAL;
            case GREATER_OR_EQUAL:
                return GL_GEQUAL;
            case ALWAYS:
                return GL_ALWAYS;
            default:
                throw new UnsupportedOperationException("DepthFunction "
                        + function + " not supported in LWJGLRenderEngineState");
        }
    }

    protected int getGLEnum(FrontFaceMethod face) {
        switch (face) {
            case CLOCKWISE:
                return GL_CW;
            case COUNTER_CLOCKWISE:
                return GL_CCW;
            default:
                throw new UnsupportedOperationException("FrontFace "
                        + face + " not supported in LWJGLRenderEngineState");
        }
    }

    protected int getGLEnum(BlendFunction function) {
        switch (function) {
            case ZERO:
                return GL_ZERO;
            case ONE:
                return GL_ONE;
            case SRC_COLOR:
                return GL_SRC_COLOR;
            case ONE_MINUS_SRC_COLOR:
                return GL_ONE_MINUS_SRC_COLOR;
            case DST_COLOR:
                return GL_DST_COLOR;
            case ONE_MINUS_DST_COLOR:
                return GL_ONE_MINUS_DST_COLOR;
            case SRC_ALPHA:
                return GL_SRC_ALPHA;
            case ONE_MINUS_SRC_ALPHA:
                return GL_ONE_MINUS_SRC_ALPHA;
            case DST_ALPHA:
                return GL_DST_ALPHA;
            case ONE_MINUS_DST_ALPHA:
                return GL_ONE_MINUS_DST_ALPHA;
            case CONSTANT_COLOR:
                return GL_CONSTANT_COLOR;
            case ONE_MINUS_CONSTANT_COLOR:
                return GL_ONE_MINUS_CONSTANT_COLOR;
            case CONSTANT_ALPHA:
                return GL_CONSTANT_ALPHA;
            case ONE_MINUS_CONSTANT_ALPHA:
                return GL_ONE_MINUS_CONSTANT_ALPHA;
            case SRC_ALPHA_SATURATE:
                return GL_SRC_ALPHA_SATURATE;
            default:
                throw new UnsupportedOperationException("BlendFunction "
                        + function + " not supported in LWJGLRenderEngineState");
        }
    }


}
