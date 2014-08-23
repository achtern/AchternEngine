package io.github.achtern.AchternEngine.core.resource.loader;

import java.io.InputStream;

/**
 * A {@link io.github.achtern.AchternEngine.core.resource.loader.BinaryLoader} is
 *  able to read an Object from an {@link java.io.InputStream}. Technically,
 *  this could also read a text-file, but in this case the {@link io.github.achtern.AchternEngine.core.resource.loader.AsciiFileLoader}
 *  is better suited.
 *
 * The cached object should be a data object, otherwise the {@link io.github.achtern.AchternEngine.core.resource.ResourceLoader}
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
