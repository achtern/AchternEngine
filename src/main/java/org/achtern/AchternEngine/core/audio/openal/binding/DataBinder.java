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

package org.achtern.AchternEngine.core.audio.openal.binding;

import org.achtern.AchternEngine.core.audio.openal.AudioBuffer;
import org.achtern.AchternEngine.core.audio.openal.AudioListener;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;

/**
 * The DataBinder in OpenAL is used for data upload only.
 */
public interface DataBinder {

    /**
     * Uploads the buffer.
     * @param buffer to be uploaded
     */
    public void upload(AudioBuffer buffer);

    /**
     * Since sources cannot be 'uploaded' this assigns the buffer
     *  in {@link org.achtern.AchternEngine.core.audio.openal.AudioSource} on the OpenAL Engine to the given source
     *  and sets the paramters of the source
     * @param source to be setup on the engine
     */
    public void upload(AudioSource source);

    /**
     * This is not a real upload, but rather a parameter setting, since OpenAL allows one listener only
     * @param listener data to be set
     */
    public void upload(AudioListener listener);

    public IDGenerator getIDGenerator();

}
