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

import org.achtern.AchternEngine.core.audio.openal.AudioBuffer;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;

import java.io.InputStream;

public class AudioSourceLoader extends BinaryLoader<AudioSource,AudioBuffer> {
    /**
     * This method should construct the object from a cached
     * value provided by the ResourceLoader
     *
     * @param value Cache
     * @return Object
     * @throws Exception could be anything (from loading over parsing to memory errors)
     */
    @Override
    public AudioSource fromCache(AudioBuffer value) throws Exception {
        return new AudioSource(value, null, null, null);
    }

    /**
     * Returns the Type of the Cache data.
     *
     * @return data type
     */
    @Override
    public Class<AudioBuffer> getCacheType() {
        return AudioBuffer.class;
    }

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     *
     * @param name  The name of the original file
     * @param input The input file
     * @throws org.achtern.AchternEngine.core.resource.loader.LoadingException when the loading fails
     */
    @Override
    public void load(String name, InputStream input) throws LoadingException {
        // TODO: loading here.
    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     *
     * @return The new object
     * @throws Exception anything can go wrong ;)
     */
    @Override
    public AudioSource get() throws Exception {
        //TODO: :/
        return new AudioSource(null, null, null, null);
    }
}
