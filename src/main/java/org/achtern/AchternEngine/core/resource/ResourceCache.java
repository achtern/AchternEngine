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

package org.achtern.AchternEngine.core.resource;

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
