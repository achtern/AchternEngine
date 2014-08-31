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

package io.github.achtern.AchternEngine.core.rendering.state;

import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

/**
 * The RenderEngineState should be used to track states of
 * the underlying Graphics Binding,
 * OpenGL in most cases. And also enable or disable states.
 */
public interface RenderEngineState {

    public String getVersion();

    public void clear(boolean color, boolean depth, boolean stencil);

    public void enable(Feature feature);

    public void disable(Feature feature);

    public boolean isEnabled(Feature feature);

    public void cullFace(Face face);

    public Face getCullFace();

    public void setFrontFace(FrontFaceMethod face);

    public FrontFaceMethod getFrontFace();

    public void setClearColor(Color color);

    public Color getClearColor();

    public void setDepthFunction(DepthFunction function);

    public DepthFunction getDepthFunction();

    public void setBlendFunction(BlendFunction sfactor, BlendFunction dfactor);

    public BlendFunction[] getBlendFunction();

    public void enableDepthWrite(boolean enable);

    public boolean isDepthWrite();

    public void setPolygonMode(FillMode mode);

    public FillMode getPolygonMode();

    public void setColorWrite(boolean r, boolean g, boolean b, boolean a);

    public boolean[] isColorWrite();

    public void setBound(FrameBuffer fbo);

    public FrameBuffer getBoundFbo();

    public void setBound(Texture texture);

    public Texture getBoundTexture();

    public void setBound(Mesh mesh);

    public Mesh getBoundMesh();

    public void setBound(Shader shader);

    public Shader getBoundShader();


}
