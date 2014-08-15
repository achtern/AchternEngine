package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

public interface DataBinder {

    public void bind(Texture texture);

    public void bind(Texture texture, int samplerslot);

    public void upload(Texture texture);

    public void bind(Mesh mesh);

    public void upload(Mesh mesh);

    public void bind(Shader shader);

    public void upload(Shader shader);

    public void bindAsRenderTarget(FrameBuffer fbo);

    public void bind(FrameBuffer fbo);

    public void upload(FrameBuffer fbo);

    public IDGenerator getIDGenerator();

}
