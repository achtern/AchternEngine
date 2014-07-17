package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

public interface IDGenerator {

    public void generate(Texture texture);

    public void generate(Mesh mesh);

    public void generate(Shader shader);

    public void generate(FrameBuffer fbo);

    public void generate(RenderBuffer rbo);

}
