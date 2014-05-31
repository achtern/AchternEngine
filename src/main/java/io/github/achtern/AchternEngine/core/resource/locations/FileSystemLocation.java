package io.github.achtern.AchternEngine.core.resource.locations;

import io.github.achtern.AchternEngine.core.resource.ResourceLocation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class FileSystemLocation implements ResourceLocation {

    /**
     * The top most folder to search.
     */
    private File root;

    public FileSystemLocation(String root) {
        this(new File(root));
    }

    public FileSystemLocation(File root) {
        this.root = root;
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getStream(String)
     */
    @Override
    public InputStream getStream(String name) {

        File file = new File(root, name);
        if (!file.exists()) {
            file = new File(name);
        }

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            /*
             We do not log the exception here 'cause
             the way the system is build most times the file
             cannot be found in the Location (only in one!)
              */
            return null;
        }
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLocation#getURL(String)
     */
    @Override
    public URL getURL(String name) {
        File file = new File(root, name);
        if (!file.exists()) {
            file = new File(name);
        }

        if (!file.exists()) {
            return null;
        }

        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            /*
             We do not log the exception here 'cause
             the way the system is build most times the file
             cannot be found in the Location (only in one!)
              */
            return null;
        }
    }
}
