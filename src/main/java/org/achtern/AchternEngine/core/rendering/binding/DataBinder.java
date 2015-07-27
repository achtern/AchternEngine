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

package org.achtern.AchternEngine.core.rendering.binding;

import org.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.rendering.state.RenderEngineState;
import org.achtern.AchternEngine.core.rendering.texture.Texture;

/**
 * The DataBinder is responsible for binding resources to the context (graphics engine of the graphics card)
 *  and uploading data to it.
 *
 * Calling bind(Resource), when the resource has not been uploaded yet will trigger an upload.
 */
public interface DataBinder {

    /**
     * Binds the given texture to the context.
     * This should default to samplerslot 0.
     * @param texture resource to bind
     */
    public void bind(Texture texture);

    /**
     * Binds the given texture to the context on a specified samplerslot
     * @param texture resource to bind
     * @param samplerslot destination slot of the texture
     */
    public void bind(Texture texture, int samplerslot);

    /**
     * This will bind the texture and then upload it.
     * @param texture resource to upload
     */
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
