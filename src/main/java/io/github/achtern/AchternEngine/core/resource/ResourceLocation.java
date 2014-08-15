package io.github.achtern.AchternEngine.core.resource;

import java.io.InputStream;
import java.net.URL;

/**
 * A ResourceLocation can be used to locate a resource,
 * based on a file name.
 * You can add a ResourceLocation for each "big" folder,
 * for example /models, /shader, /textures, etc.
 * This way you can just load "image.jpg" instead of
 * "/textures/image.jpg".
 */
public interface ResourceLocation {

    /**
     * Returns the Resource as a InputStream
     * @param name The path relative to the location (usally the filename)
     * @return A readable stream for this resource | null if not exists
     */
    public InputStream getStream(String name);

    /**
     * Returns the Resource's URL
     * @param name The path relative to the location (usally the filename)
     * @return The URL for this resource
     */
    public URL getURL(String name);

}
