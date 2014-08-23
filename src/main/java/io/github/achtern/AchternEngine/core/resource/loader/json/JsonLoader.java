package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.loader.AsciiFileLoader;
import io.github.achtern.AchternEngine.core.resource.loader.LoadingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link io.github.achtern.AchternEngine.core.resource.loader.json.JsonLoader} can be used
 * to load and process json files more easily.
 * @param <T> Type of object which can be loaded by the Loader
 */
public abstract class JsonLoader<T> extends AsciiFileLoader<T> {

    /**
     * The JSONObject.
     */
    protected JSONObject jsonObject;

    /**
     * Constructs the {@link org.json.JSONObject} and parses it.
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     * @param name The name of the original file
     * @param input The input file
     * @throws LoadingException when the loading fails
     */
    @Override
    public void load(String name, String input) throws LoadingException {
        try {
            jsonObject = new JSONObject(input);
        } catch (JSONException e) {
            throw new LoadingException("Failed to parse json.", e);
        }
    }

    /**
     * Returns the parsed JSONOBject
     * @return parsed json object
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Sets the JSONObject
     * @param jsonObject The new JSONObject
     */
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Converts a {@link org.json.JSONArray} into an array of
     * {@link java.lang.Object}s
     * @param array JSONArray
     * @return Object array
     */
    public static Object[] toObjectArray(JSONArray array) {
        Object[] objects = new Object[array.length()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = array.get(i);

        }

        return objects;
    }

    /**
     * Reads a {@link io.github.achtern.AchternEngine.core.rendering.Color} from a JSON
     * declaration.
     * Components not specified will default to <code>1</code>.
     * @param json JSONObject
     * @return Color
     */
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

    /**
     * Reads a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture} from a JSON
     * declaration.
     * Loads it via the {@link io.github.achtern.AchternEngine.core.resource.ResourceLoader}
     * @param json JSONObject
     * @return Texture
     * @throws Exception (from ResourceLoader)
     */
    protected Texture getTexture(JSONObject json) throws Exception {
        final String file = json.getString("file");

        return ResourceLoader.getTexture(file);

    }
}
