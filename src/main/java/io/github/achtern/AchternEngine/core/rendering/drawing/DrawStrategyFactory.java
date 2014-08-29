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

package io.github.achtern.AchternEngine.core.rendering.drawing;

import java.util.HashMap;
import java.util.Map;

public class DrawStrategyFactory {

    public enum Common {
        WIREFRAME("wireframe"),
        SOLID("solid");

        private String key;

        Common(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }

    public static Map<String, DrawStrategy> strategies = new HashMap<String, DrawStrategy>();

    public static DrawStrategy get(String key) {
        return strategies.get(key);
    }

    public static DrawStrategy put(String key, DrawStrategy value) {
        return strategies.put(key, value);
    }

    public static DrawStrategy get(Common key) {
       return get(key.getKey());
    }

    public static DrawStrategy put(Common key, DrawStrategy value) {
       return put(key.getKey(), value);
    }

    public static DrawStrategy remove(String key) {
        return strategies.remove(key);
    }

    public static void clear() {
        strategies.clear();
    }

    public static boolean containsKey(String key) {
        return strategies.containsKey(key);
    }

    public static boolean containsValue(DrawStrategy value) {
        return strategies.containsValue(value);
    }

    public static int size() {
        return strategies.size();
    }

    public static boolean isEmpty() {
        return strategies.isEmpty();
    }
}
