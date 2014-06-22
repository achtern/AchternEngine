package io.github.achtern.AchternEngine.core.resource.locations;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.ResourceLocation;

import java.io.InputStream;
import java.net.URL;

public class ClasspathLocation implements ResourceLocation {

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getStream(String)
     */
    @Override
    public InputStream getStream(String name) {
        return ResourceLoader.class.getClassLoader().getResourceAsStream(name);
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getURL(String)
     */
    @Override
    public URL getURL(String name) {
        return ResourceLoader.class.getClassLoader().getResource(name);
    }
}
