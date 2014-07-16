package io.github.achtern.AchternEngine.core.contracts.abstractVersion;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

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
    public int getFormat() {
        return -1;
    }

    @Override
    public int getInternalFormat() {
        return GL_RGBA8;
    }
}
