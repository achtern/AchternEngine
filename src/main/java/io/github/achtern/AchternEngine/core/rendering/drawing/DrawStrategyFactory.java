package io.github.achtern.AchternEngine.core.rendering.drawing;

import java.util.HashMap;
import java.util.Map;

public class DrawStrategyFactory {

    public static Map<String, DrawStrategy> strategies = new HashMap<String, DrawStrategy>();

    static {
        strategies.put("solid", new SolidDraw());
        strategies.put("wireframe", new WireframeDraw());
    }

    public static DrawStrategy get(String key) {
        return strategies.get(key);
    }

    public static DrawStrategy put(String key, DrawStrategy value) {
        return strategies.put(key, value);
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
