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

package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

public interface DataBinder {

    public void bind(Texture texture);

    public void bind(Texture texture, int samplerslot);

    public void upload(Texture texture);

    public void bind(Mesh mesh);

    public void upload(Mesh mesh);

    public void draw(Mesh mesh);

    public void bind(Shader shader);

    public void upload(Shader shader);

    public void bindAsRenderTarget(FrameBuffer fbo);

    public void bind(FrameBuffer fbo);

    public void upload(FrameBuffer fbo);

    public IDGenerator getIDGenerator();

    public UniformManager getUniformManager();

    public RenderEngineState getState();

}
