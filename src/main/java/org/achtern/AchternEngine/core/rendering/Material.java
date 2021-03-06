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

package org.achtern.AchternEngine.core.rendering;

import lombok.Setter;
import org.achtern.AchternEngine.core.rendering.shader.forward.ShaderSuit;
import org.achtern.AchternEngine.core.rendering.shader.forward.suits.phong.PhongShaderSuit;
import org.achtern.AchternEngine.core.rendering.texture.Texture;
import org.achtern.AchternEngine.core.resource.ResourceLoader;
import org.achtern.AchternEngine.core.util.CommonDataStore;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Material extends CommonDataStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(Material.class);

    public static Texture DEFAULT_NORMAL;

    public static Texture DEFAULT_DISPLACEMENT;

    public static Texture MISSING_TEXTURE;

    static {
        try {
            DEFAULT_NORMAL = ResourceLoader.getTexture("default_normal.jpg");
            DEFAULT_DISPLACEMENT = ResourceLoader.getTexture("default_displacement.png");
            MISSING_TEXTURE = ResourceLoader.getTexture("missing.jpg");
        } catch (Exception e) {
            LOGGER.error("BREAK IN THE SPACETIME! MISSING BUNDLED TEXTURE!", e);
        }
    }

    @Getter protected boolean wireframe = false;

    @Getter @Setter protected ShaderSuit shader;

    /**
     * Creates a Material with a given ShaderSuit
     * Use the parameter less constructor to get the default ShaderSuit.
     * @param shader ShaderSuit to render objects with
     */
    public Material(ShaderSuit shader) {
        this.shader = shader;

        // load default normalMap && displacementMap
        addTexture("normalMap", DEFAULT_NORMAL);
        addTexture("displacementMap", DEFAULT_DISPLACEMENT);

        // set default displacement Values
        addFloat("displacementAmount", 0.04f);
        addFloat("displacementOffset", 0);
    }

    /**
     * Creates a Material with the default PhongShaderSuit
     */
    public Material() {
        this(PhongShaderSuit.get());
    }

    @Override
    public Texture getTexture(String name) {
        Texture r = super.getTexture(name);
        if (r != null) {
            return r;
        }

        addTexture(name, MISSING_TEXTURE);
        return MISSING_TEXTURE;
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

    public void asWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
