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

package io.github.achtern.AchternEngine.lwjgl.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.binding.IDGenerator;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;

import static io.github.achtern.AchternEngine.lwjgl.util.GLEnum.getGLEnum;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Generates data IDs for Objects through the LWJGL Binding
 */
public class LWJGLIDGenerator implements IDGenerator {

    /**
     * Generate an ID for the Texture.
     *
     * @param texture The texture
     */
    @Override
    public void generate(Texture texture) {
        int id = glGenTextures();
        texture.setID(id);
    }

    /**
     * Generate an ID for the Mesh
     * the ID for a Mesh includes
     * VBO, VAO and IBO
     *
     * @param mesh The mesh
     */
    @Override
    public void generate(Mesh mesh) {
        MeshData data = mesh.getData();
        int vbo = glGenBuffers();
        int ibo = glGenBuffers();
        int vao = glGenVertexArrays();
        data.setBufferIDs(vbo, ibo);
        data.setID(vao);
    }

    /**
     * Generate an ID for the Shader
     *
     * @param shader The shader
     */
    @Override
    public void generate(Shader shader) {
        int id = glCreateProgram();
        shader.getProgram().setID(id);

        for (GLSLScript script : shader.getProgram().getScripts()) {
            int sId = glCreateShader(getGLEnum(script.getType()));
            script.setID(sId);
        }

    }


    /**
     * Generate an ID for the FrameBuffer
     *
     * @param fbo The framebuffer
     */
    @Override
    public void generate(FrameBuffer fbo) {
        int id = glGenFramebuffers();
        fbo.setID(id);
    }

    /**
     * Generate an ID for the RenderBuffer
     *
     * @param rbo The renderbuffer
     */
    @Override
    public void generate(RenderBuffer rbo) {
        int id = glGenRenderbuffers();
        rbo.setID(id);
    }
}
