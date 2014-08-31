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
