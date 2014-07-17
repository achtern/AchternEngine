package io.github.achtern.AchternEngine.core.contracts.abstractVersion;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;

import static org.lwjgl.opengl.GL11.*;

public abstract class CommonTexturableData implements TexturableData {

    @Override
    public int getTarget() {
        return GL_TEXTURE_2D;
    }

    @Override
    public int getMinFilter() {
        return GL_NEAREST;
    }

    @Override
    public int getMagFilter() {
        return GL_NEAREST;
    }


    @Override
    public Format getFormat() {
        return null;
    }

    @Override
    public int getInternalFormat() {
        return GL_RGBA8;
    }
}
