/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.util.CommonDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Material extends CommonDataStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(Material.class);

    private boolean wireframe = false;

    @Override
    public Texture getTexture(String name) {
        Texture r = super.getTexture(name);
        if (r != null) {
            return r;
        }

        try {
            return ResourceLoader.getTexture("missing.jpg");
        } catch (Exception e) {
            // WILL NEVER HAPPEN... But log it and return null.
            LOGGER.error("BREAK IN THE SPACETIME! MISSING BUNDLED TEXTURE!", e);
            return null;
        }
    }


    public void setColor(Color color) {
        addColor("color", color);
    }

    public Color getColor() {
        Color c = getColor("color");
        if (c == null) {
            return Color.WHITE;
        } else {
            return c;
        }
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void asWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
