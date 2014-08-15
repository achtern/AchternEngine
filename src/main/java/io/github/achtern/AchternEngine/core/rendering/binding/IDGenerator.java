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
