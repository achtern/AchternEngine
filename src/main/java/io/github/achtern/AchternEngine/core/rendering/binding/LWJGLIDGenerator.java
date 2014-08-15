package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;
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
        data.setIDs(vbo, ibo, vao, 0);
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
