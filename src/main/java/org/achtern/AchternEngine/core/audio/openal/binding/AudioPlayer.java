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

import org.achtern.AchternEngine.core.audio.openal.AudioSource;

/**
 * The AudioPlayer handles basic playback operations for {@link org.achtern.AchternEngine.core.audio.openal.AudioSource}s
 *
 * This should set the state variable <code>state</code> correctly.
 *
 * @see org.achtern.AchternEngine.core.audio.openal.AudioSource#setState(org.achtern.AchternEngine.core.audio.openal.AudioSourceState)
 */
public interface AudioPlayer {

    /**
     * Plays the given AudioSource.
     *
     * If the source is already playing, it will play the same source again from start.
     *
     * If the source is currently paused, it will resume it.
     *
     * Implementation Hint:
     *
     * This should set a timer to set the playing state of the AudioSource back to
     *  {@link org.achtern.AchternEngine.core.audio.openal.AudioSourceState#STOPPED}.
     * @param source AudioSource to play.
     */
    public void play(AudioSource source);

    /**
     * Stops the given AudioSource.
     *
     * If the source is not playing, this method will exit silently.
     *
     * Implementation Hint:
     *
     * This should cancel the timer, set in {@link #play(org.achtern.AchternEngine.core.audio.openal.AudioSource)} and
     *  update the playing state in AudioSource
     * @param source AudioSource to stop.
     */
    public void stop(AudioSource source);

    public void rewind(AudioSource source);

    public void pause(AudioSource source);

    public DataBinder getDataBinder();

}
