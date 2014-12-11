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

package org.achtern.AchternEngine.core.resource.loader;

import java.io.InputStream;

/**
 * A {@link org.achtern.AchternEngine.core.resource.loader.BinaryLoader} is
 *  able to read an Object from an {@link java.io.InputStream}. Technically,
 *  this could also read a text-file, but in this case the {@link org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader}
 *  is better suited.
 *
 * The cached object should be a data object, otherwise the {@link org.achtern.AchternEngine.core.resource.ResourceLoader}
 *  would turn into a singelton container. Caching is still handled in the ResourceLoader
 *
 * @param <T> Type of object which can be loaded by the Loader
 * @param <C> Type of object that is cached.
 */
public abstract class BinaryLoader<T, C> implements Loader<T, InputStream> {

    /**
     * The cached <strong>value</strong>.
     */
    private C cache;

    /**
     * This method should construct the object from a cached
     * value provided by the ResourceLoader
     * @param value Cache
     * @return Object
     * @throws Exception
     */
    public abstract T fromCache(C value) throws Exception;

    /**
     * Returns the Type of the Cache data.
     * @return data type
     */
    public abstract Class<C> getCacheType();

    /**
     * Returns a generated cache item.
     * This cache value should be set in
     * {@link #get()}
     * @return Cache
     */
    public C getCache() {
        return cache;
    }

    /**
     * Caches the value.
     * Should be called from within {@link #get()}
     * @param cache value to cache
     */
    protected void cache(C cache) {
        this.cache = cache;
    }
}
