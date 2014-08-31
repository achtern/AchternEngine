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

package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

/**
 * An ID Generator is responsible for generating
 * IDs for native graphics objects, like Textures, Meshes
 * and the like.
 * The ID Generator should inject the ID by using the setter
 * setID(int) on the object.
 * This should be defined by the graphics API implementation.
 * The IDGenerator should be provided by the implementation
 * {@link io.github.achtern.AchternEngine.core.rendering.binding.DataBinder},
 * which itself is provided by the {@link io.github.achtern.AchternEngine.core.rendering.RenderEngine}.
 */
public interface IDGenerator {

    /**
     * Generate an ID for the Texture.
     * @param texture The texture
     */
    public void generate(Texture texture);

    /**
     * Generate an ID for the Mesh,
     * the ID for a Mesh includes
     * VBO, VAO and IBO
     * @param mesh The mesh
     */
    public void generate(Mesh mesh);

    /**
     * Generate an ID for the Shader
     * @param shader The shader
     */
    public void generate(Shader shader);

    /**
     * Generate an ID for the FrameBuffer
     * @param fbo The framebuffer
     */
    public void generate(FrameBuffer fbo);

    /**
     * Generate an ID for the RenderBuffer
     * @param rbo The renderbuffer
     */
    public void generate(RenderBuffer rbo);

}
