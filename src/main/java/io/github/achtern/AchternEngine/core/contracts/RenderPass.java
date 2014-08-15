package io.github.achtern.AchternEngine.core.contracts;

import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

/**
 * A RenderPass is basically a wrapper for a shader.
 * It allows to render the scene with a specific pass.
 */
public interface RenderPass {

    /**
     * Returns the shader to get set on draw.
     * @return the shader
     */
    public Shader getShader();
}
