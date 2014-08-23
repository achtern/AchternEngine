package io.github.achtern.AchternEngine.core.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ResourceLoaderTest {

    @Before
    public void before() {
        ResourceLoader.clearResourceLocations();
    }

    @Test
    public void testClearResourceLocations() throws Exception {

        assertEquals("Size should be null after clearing all ResourceLocations",
                0, ResourceLoader.getResourceLocations().size()
        );

    }

    @Test(expected = IOException.class)
    public void testResourceLocations() throws Exception {

        NullResourceLocation location = new NullResourceLocation();

        ResourceLoader.addResourceLocation(location);

        ResourceLoader.readFile("file");

        assertEquals("getStream should be called once, when loading a file",
                1, location.getStream);

        assertEquals("getURL should not be called, when loading a file",
                0, location.getURL);

    }

    public void testExceptionOnNotFound() throws Exception {
        ResourceLoader.clearResourceLocations();
    }

    static final class NullResourceLocation implements ResourceLocation {

        public int getStream = 0;
        public int getURL = 0;

        /**
         * Returns the Resource as a InputStream
         *
         * @param name The path relative to the location (usally the filename)
         * @return A readable stream for this resource | null if not exists
         */
        @Override
        public InputStream getStream(String name) {
            getStream++;
            return null;
        }

        /**
         * Returns the Resource's URL
         *
         * @param name The path relative to the location (usally the filename)
         * @return The URL for this resource
         */
        @Override
        public URL getURL(String name) {
            getURL++;
            return null;
        }


    }

}