package io.github.achtern.AchternEngine.core.resource.locations;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.ResourceLocation;

import java.io.InputStream;
import java.net.URL;

public abstract class BundledResourceLocation implements ResourceLocation {

    private String subfolder;

    private BundledResourceLocation() {
    }

    protected BundledResourceLocation(String subfolder) {
        this.subfolder = subfolder;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getStream(String)
     */
    @Override
    public InputStream getStream(String name) {

        return ResourceLoader.class.getResourceAsStream(subfolder + name);
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getURL(String)
     */
    @Override
    public URL getURL(String name) {
        return ResourceLoader.class.getResource(subfolder + name);
    }

}
