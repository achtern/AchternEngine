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

package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * {@link io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy}s are easy
 * to define and if not, no override will be set.
 * In fact to not specify a DrawStrategy in the {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Figure} is
 * the most common approach!
 *
 * In the DrawStrategy Declaration should only be one field.
 * Either '@', 'name' or '@E'.
 * If two are specified at the same time, the last one
 * will get used.
 *
 * The values for the three types of keys can be in the following
 * format:
 *
 * <code>@</code> specifies a classname. The classname should be fully qualified Class name.
 *
 * <code>name</code> specifies the key, which will be used in to retrieve a stored DrawStrategy
 * from the {@link io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory}.
 * The method {@link io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory#get(String)} will
 * be used.
 *
 * <code>@E</code> specifies the name of one of the {@link io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategyFactory.Common}
 * keys to use.
 * This should just be the name in uppercase letters (e.g. <code>SOLID</code>)
 *
 */
public class DrawStrategyLoader extends JsonLoader<DrawStrategy> {

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * @return The new object
     * @throws Exception
     */
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
