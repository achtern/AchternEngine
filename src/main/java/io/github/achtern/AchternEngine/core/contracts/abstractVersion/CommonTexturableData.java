package io.github.achtern.AchternEngine.core.contracts.abstractVersion;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import static org.lwjgl.opengl.GL11.*;

public abstract class CommonTexturableData implements TexturableData {

    @Override
    public Texture.Type getType() {
        return Texture.Type.TWO_DIMENSIONAL;
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
