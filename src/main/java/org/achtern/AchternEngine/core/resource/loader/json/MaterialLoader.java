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

package org.achtern.AchternEngine.core.resource.loader.json;

import org.achtern.AchternEngine.core.rendering.Material;
import org.json.JSONObject;

public class MaterialLoader extends JsonLoader<Material> {

    @Override
    public Material get() throws Exception {
        final JSONObject json = getJsonObject();

        final Material material = new Material();

        // Start with the simplest one
        if (json.has("wireframe")) {
            material.asWireframe(json.getBoolean("wireframe"));
        }

        // All Floats
        if (json.has("float")) {
            JSONObject floats = json.getJSONObject("float");

            for (Object oKey : floats.keySet()) {
                String key = (String) oKey;

                material.addFloat(key, (float) floats.getDouble(key));

            }
        }

        // All Colors
        if (json.has("Color")) {
            JSONObject colors = json.getJSONObject("Color");

            for (Object oKey : colors.keySet()) {
                String key = (String) oKey;

                material.addColor(key, getColor(colors.getJSONObject(key)));

            }
        }

        if (json.has("Texture")) {
            // All Textures
            JSONObject textures = json.getJSONObject("Texture");

            for (Object oKey : textures.keySet()) {
                String key = (String) oKey;

                material.addTexture(key, getTexture(textures.getJSONObject(key)));

            }
        }

        return material;
    }
}
