package io.github.achtern.AchternEngine.core.contracts.abstractVersion;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.texture.Filter;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.InternalFormat;
import io.github.achtern.AchternEngine.core.rendering.texture.Type;

public abstract class CommonTexturableData implements TexturableData {

    @Override
    public Type getType() {
        return Type.TWO_DIMENSIONAL;
    }

    @Override
    public Filter getMinFilter() {
        return Filter.NEAREST;
    }

    @Override
    public Filter getMagFilter() {
        return Filter.NEAREST;
    }


    @Override
    public Format getFormat() {
        return null;
    }

    @Override
    public InternalFormat getInternalFormat() {
        return InternalFormat.RGBA8;
    }
}
