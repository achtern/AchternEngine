package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class DrawStrategyLoader extends JsonLoader<DrawStrategy> {
    @Override
    public DrawStrategy get() throws Exception {
        final JSONObject json = getJsonObject();
        // The DrawStrategy
        final DrawStrategy d;
        if (json.has("@")) {
            // We have a Class Name
            String clazz = json.getString("@");

            d = (DrawStrategy) getObject(clazz, null);
        } else if (json.has("name")) {
            // We have a name, to be used in the DrawStrategyFactory
            d = DrawStrategyFactory.get(json.getString("name"));
        } else if (json.has("@E")) {
            // We have a Common Enum for the DrawStrategyFactory
            String clazz = json.getString("@E");
            DrawStrategyFactory.Common key = DrawStrategyFactory.Common.valueOf(clazz);

            d = DrawStrategyFactory.get(key);
        } else {
            throw new JSONException("DrawStrategy body is invalid, no '@', '@E' or 'name' key found!");
        }

        return d;
    }
}
