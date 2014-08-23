package io.github.achtern.AchternEngine.core.resource.loader;

import java.io.InputStream;

public abstract class BinaryLoader<T, C> implements Loader<T, InputStream> {

    private C cache;

    public abstract T fromCache(C value) throws Exception;

    public abstract Class<C> getCacheType();

    public C getCache() {
        return cache;
    }

    protected void cache(C cache) {
        this.cache = cache;
    }

    protected void setCache(C cache) {
        this.cache = cache;
    }
}
