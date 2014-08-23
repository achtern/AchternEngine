package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.loader.AsciiFileLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonLoader<T> extends AsciiFileLoader<T> {

    private JSONObject jsonObject;

    @Override
    public void load(String name, String file) throws JSONException {
        jsonObject = new JSONObject(file);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public static Object[] toObjectArray(JSONArray array) {
        Object[] objects = new Object[array.length()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = array.get(i);

        }

        return objects;
    }

    protected Color getColor(JSONObject json) {
        float r, g, b, a;
        // Default to 1
        r = g = b = a = 1;

        if (json.has("r")) {
            r = (float) json.getDouble("r");
        }
        if (json.has("g")) {
            g = (float) json.getDouble("g");
        }
        if (json.has("b")) {
            b = (float) json.getDouble("b");
        }
        if (json.has("a")) {
            a = (float) json.getDouble("a");
        }

        return new Color(r, g, b, a);
    }

    protected Texture getTexture(JSONObject json) throws Exception {
        final String file = json.getString("file");

        return ResourceLoader.getTexture(file);

    }
}
