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

package org.achtern.AchternEngine.lwjgl.audio.openal.binding;

import lombok.AllArgsConstructor;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.audio.openal.binding.DataBinder;

import static org.achtern.AchternEngine.core.bootstrap.Native.INVALID_ID;
import static org.lwjgl.openal.AL10.*;

@AllArgsConstructor
public class LWJGLAudioPlayer implements AudioPlayer {

    protected LWJGLDataBinder dataBinder;

    @Override
    public void play(AudioSource source) {
        getDataBinder().upload(source);
        alSourcePlay(source.getID());
    }

    @Override
    public void stop(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourceStop(source.getID());
    }

    @Override
    public void rewind(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourceRewind(source.getID());
    }

    @Override
    public void pause(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourcePause(source.getID());
    }

    @Override
    public DataBinder getDataBinder() {
        return dataBinder;
    }
}
