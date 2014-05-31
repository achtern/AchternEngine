package io.github.achtern.AchternEngine.core.resource;

import java.util.HashMap;

/**
 * Just a wrapper for a HashMap.
 * API optimised for basic caching operations.
 * @param <T>
 */
public class ResourceCache<T> {

    /**
     * The main cache
     */
    protected HashMap<String,T> cache = new HashMap<String, T>();

    public void add(String name, T resource) {
        cache.put(name, resource);
    }

    public T get(String name) {
        return cache.get(name);
    }

    public void remove(String name) {
        cache.remove(name);
    }

    public boolean has(String name) {
        return cache.containsKey(name);
    }

    public void clear() {
        cache.clear();
    }

    public HashMap<String,T> getAll() {
        return cache;
    }

}
