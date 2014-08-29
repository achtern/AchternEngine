/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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
