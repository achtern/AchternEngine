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

package org.achtern.AchternEngine.core.audio;

import org.achtern.AchternEngine.core.scenegraph.Updatable;

public interface AudioPlayer extends Updatable {

    /**
     * Initial Setup.
     * Should be called before any other method.
     */
    public void init();

    /**
     * Playback the given Audio till the end
     * @param audio to play
     */
    public void play(Audio audio);

    /**
     * Pauses the playback of the given audio,
     *  use {@link #play(org.achtern.AchternEngine.core.audio.Audio)} to resume
     * @param audio to pause
     */
    public void pause(Audio audio);

    /**
     * Stops the play of the given audio and removes any data from the AudioPlayer about the given audio
     * @param audio to stop
     */
    public void stop(Audio audio);

    /**
     * Pause all audio playbacks
     */
    public void pauseAll();

    /**
     * Stop all audio playbacks
     */
    public void stopAll();

    /**
     * Resume all paused playbacks
     */
    public void playAll();
}
