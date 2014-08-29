/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This {@link io.github.achtern.AchternEngine.core.resource.loader.TextureLoader} uses
 * {@link javax.imageio.ImageIO} to load the image and then converts it into
 * a usable {@link java.nio.ByteBuffer} for the {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
 * When <code>null</code> is given as {@link io.github.achtern.AchternEngine.core.rendering.Dimension} the dimensions
 * of the read image are used!
 */
public class TextureLoader extends BinaryLoader<Texture, TexturableData> {

    /**
     * Image read from the InputStream
     */
    protected BufferedImage image;
    /**
     * Supplied dimensions (can be null)
     */
    protected Dimension dimension;

    /**
     * Construct with dimension. Can be null!
     * @param dimension Dimension|null
     */
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

    /**
     * This method should construct the {@link io.github.achtern.AchternEngine.core.contracts.TexturableData} from a cached
     * value provided by the ResourceLoader
     * @param value Cache
     * @return Object {@link io.github.achtern.AchternEngine.core.contracts.TexturableData}
     * @throws Exception
     */
    @Override
    public Texture fromCache(TexturableData value) throws Exception {
        return new Texture(value);
    }

    /**
     * Returns the Type of the Cache data.
     * @return data type
     */
    @Override
    public Class<TexturableData> getCacheType() {
        return TexturableData.class;
    }
}