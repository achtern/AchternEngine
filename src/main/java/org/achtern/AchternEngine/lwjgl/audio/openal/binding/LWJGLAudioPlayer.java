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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.audio.openal.binding.DataBinder;
import org.achtern.AchternEngine.core.util.async.AsyncHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import static org.achtern.AchternEngine.core.bootstrap.Native.INVALID_ID;
import static org.lwjgl.openal.AL10.*;

@AllArgsConstructor
public class LWJGLAudioPlayer implements AudioPlayer {

    protected LWJGLDataBinder dataBinder;

    @Getter(AccessLevel.PROTECTED)
    protected Map<AudioSource, ScheduledFuture> futures = new HashMap<AudioSource, ScheduledFuture>();

    @Override
    public void play(final AudioSource source) {
        getDataBinder().upload(source);
        alSourcePlay(source.getID());
        updateState(source);

        addFuture(source, new AsyncHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateState(source);
            }
            /*
            We could just cast to long and use SECONDS as TimeUnit,
            but this way is more precise, since we have 3 more digits.
             */
        }, (long) source.getBuffer().getLengthInSeconds() * 1000));
    }

    @Override
    public void stop(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourceStop(source.getID());
        updateState(source);
        cancelFuture(source);
    }

    @Override
    public void rewind(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourceRewind(source.getID());
        updateState(source);
        cancelFuture(source);
    }

    @Override
    public void pause(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            throw new IllegalArgumentException("Source is not uploaded yet, thus cannot be running");
        }
        alSourcePause(source.getID());
        updateState(source);
        cancelFuture(source);
    }

    @Override
    public DataBinder getDataBinder() {
        return dataBinder;
    }

    protected void updateState(AudioSource source) {
        AudioSourceState state = getState(alGetSourcei(source.getID(), AL_SOURCE_STATE));

        source.setState(state);
    }

    protected AudioSourceState getState(int code) {
        switch (code) {
            case AL_PLAYING:
                return AudioSourceState.PLAYING;
            case AL_STOPPED:
                return AudioSourceState.STOPPED;
            case AL_PAUSED:
                return AudioSourceState.PAUSED;
            default: /* falls through */
            case AL_INITIAL:
                /*
                I know this not the same, but the end user doesn't need to know the implementation details of
                OpenAL.
                 */
                return AudioSourceState.STOPPED;
        }
    }

    protected void addFuture(AudioSource source, ScheduledFuture future) {
        getFutures().put(source, future);
    }

    protected void cancelFuture(AudioSource source) {
        // Cancel any futures there may be.
        if (getFutures().containsKey(source)) {
            getFutures().get(source).cancel(true);
            getFutures().remove(source);
        }
    }
}
