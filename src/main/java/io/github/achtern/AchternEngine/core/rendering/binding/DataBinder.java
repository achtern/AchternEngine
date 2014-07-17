package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

public interface DataBinder {

    public void bind(Texture texture);

    public void bind(Texture texture, int samplerslot);

    public void upload(Texture texture);


    public void bind(FrameBuffer texture);

    public void upload(FrameBuffer texture);

    public IDGenerator getIDGenerator();

}
