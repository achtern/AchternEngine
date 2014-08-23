package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TextureLoader extends BinaryLoader<Texture, TexturableData> {

    protected BufferedImage image;
    protected Dimension dimension;

    public TextureLoader(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     *
     * @param name  The name of the original file
     * @param input The input file
     * @throws LoadingException when the loading fails
     */
    @Override
    public void load(String name, InputStream input) throws LoadingException {
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            throw new LoadingException("Could not read image", e);
        }
    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     *
     * @return The new object
     * @throws Exception
     */
    @Override
    public Texture get() throws Exception {
        Texture texture;
        if (dimension != null) {
            texture = new Texture(image, dimension);
        } else {
            texture = new Texture(image);
        }

        cache(texture);

        return texture;
    }

    @Override
    public Texture fromCache(TexturableData value) throws Exception {
        return new Texture(value);
    }

    @Override
    public Class<TexturableData> getCacheType() {
        return TexturableData.class;
    }
}
