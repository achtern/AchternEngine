package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.RenderBuffer;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL30.glGenFramebuffers;
import static org.lwjgl.opengl.GL30.glGenRenderbuffers;

public class LWJGLIDGenerator implements IDGenerator {

    @Override
    public void generate(Texture texture) {
        int id = glGenTextures();
        texture.setID(id);
    }

    @Override
    public void generate(Mesh mesh) {

    }

    @Override
    public void generate(Shader shader) {

    }

    @Override
    public void generate(FrameBuffer fbo) {
        int id = glGenFramebuffers();
        fbo.setID(id);
    }

    @Override
    public void generate(RenderBuffer rbo) {
        int id = glGenRenderbuffers();
        rbo.setID(id);
    }

    @Deprecated
    public static void generateIt(Texture texture) {
        (new LWJGLIDGenerator()).generate(texture);
    }

    @Deprecated
    public static void generateIt(FrameBuffer fbo) {
        (new LWJGLIDGenerator()).generate(fbo);
    }

    @Deprecated
    public static void generateIt(RenderBuffer rbo) {
        (new LWJGLIDGenerator()).generate(rbo);
    }
}
