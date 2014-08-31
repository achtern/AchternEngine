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

package io.github.achtern.AchternEngine.lwjgl.util;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.rendering.state.*;
import io.github.achtern.AchternEngine.core.rendering.texture.Filter;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.InternalFormat;
import io.github.achtern.AchternEngine.core.rendering.texture.Type;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA_SATURATE;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_3D;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class GLEnum {

    // ---
    // DataBinding
    // --

    public static int getGLEnum(Filter minFilter) {
        switch (minFilter) {
            case NEAREST:
                return GL_NEAREST;
            case LINEAR:
                return GL_LINEAR;
            default:
                throw new UnsupportedOperationException("Filter: " + minFilter + " not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(Type type) {
        switch (type) {
            case ONE_DIMENSIONAL:
                return GL_TEXTURE_1D;
            case TWO_DIMENSIONAL:
                return GL_TEXTURE_2D;
            case THREE_DIMENSIONAL:
                return GL_TEXTURE_3D;
            case CUBE_MAP:
                return GL_TEXTURE_CUBE_MAP;
            default:
                // TODO: implement the other types
                throw new UnsupportedOperationException("Texture type " + type + " not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(Format format) {
        switch (format) {
            case RGBA:
                return GL_RGBA;
            case RGB:
                return GL_RGB;
            case DEPTH:
                return GL_DEPTH_COMPONENT;

            default:
                throw new UnsupportedOperationException("Format: " + format + " not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(InternalFormat format) {
        switch (format) {
            case RGBA8:
                return GL_RGBA8;
            case DEPTH_COMPONENT:
                return GL_DEPTH_COMPONENT;
            case DEPTH_COMPONENT16:
                return GL_DEPTH_COMPONENT16;
            case DEPTH_COMPONENT24:
                return GL_DEPTH_COMPONENT24;
            case DEPTH_COMPONENT32:
                return GL_DEPTH_COMPONENT32;

            default:
                throw new UnsupportedOperationException("Format; " + format + " not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(GLSLScript.Type type) {
        switch (type) {
            case VERTEX_SHADER:
                return GL_VERTEX_SHADER;
            case FRAGMENT_SHADER:
                return GL_FRAGMENT_SHADER;
            case GEOMETRY_SHADER:
                return GL_GEOMETRY_SHADER;
            default:
                throw new UnsupportedOperationException("Shader type not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(MeshData.Mode mode) {
        switch (mode) {
            case TRIANGLES:
                return GL_TRIANGLES;
            case LINES:
                return GL_LINES;
            case LINE_LOOP:
                return GL_LINE_LOOP;
            default:
                throw new UnsupportedOperationException("Mode " + mode + " not supported in this LWJGLRenderEngine");
        }
    }

    // ---
    // RenderEngineState
    // --

    public static int getGLEnum(Feature feature) {
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
                throw new UnsupportedOperationException("Feature " + feature + " not supported in this LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(Face face) {
        switch (face) {
            case BACK:
                return GL_BACK;
            case FRONT:
                return GL_FRONT;
            case ALL:
                return GL_FRONT_AND_BACK;
            default:
                throw new UnsupportedOperationException("Face " + face + " not supported in LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(DepthFunction function) {
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
                        + function + " not supported in LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(FrontFaceMethod face) {
        switch (face) {
            case CLOCKWISE:
                return GL_CW;
            case COUNTER_CLOCKWISE:
                return GL_CCW;
            default:
                throw new UnsupportedOperationException("FrontFace "
                        + face + " not supported in LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(BlendFunction function) {
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
                        + function + " not supported in LWJGLRenderEngine");
        }
    }

    public static int getGLEnum(FillMode mode) {
        switch (mode) {
            case POINT:
                return GL_POINT;
            case LINE:
                return GL_LINE;
            case FILL:
                return GL_FILL;
            default:
                throw new UnsupportedOperationException("FillMode " + mode + " not supported in LWJGLRenderEngine");
        }
    }

}
