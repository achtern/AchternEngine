package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.Material;
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
