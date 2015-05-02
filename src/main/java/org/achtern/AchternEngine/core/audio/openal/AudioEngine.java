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

package org.achtern.AchternEngine.core.audio.openal;

import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.scenegraph.Node;

/**
 * The AudioEngine handles playback of AudioSources in 3D space.
 *
 * In contrast to the {@link org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer} the engine handles,
 *  AudioSources associated with {@link org.achtern.AchternEngine.core.scenegraph.Node}s and setting
 *  of the {@link org.achtern.AchternEngine.core.audio.openal.AudioListener}.
 *
 * The AudioEngine shouldn't play AudioSources which are too far away to hear anyway and handle environment sound.
 */
public interface AudioEngine {

    /**
     * Starts to playback all audiofiles for the given scenegraph.
     * This method walks the scenegraph recursively.
     * @param node scenegraph
     */
    public void play(Node node);

    /**
     * Sets the {@link org.achtern.AchternEngine.core.audio.openal.AudioListener} to use.
     * This will be required to have 3D audio effects.
     * @param listener the current listener
     */
    public void setAudioListener(AudioListener listener);

    /**
     * Returns the currently associated {@link org.achtern.AchternEngine.core.audio.openal.AudioListener} with this
     *  AudioEngine.
     * @return current listener
     */
    public AudioListener getAudioListener();

    /**
     * Sets the {@link org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer} to use
     * @param audioPlayer this will be used to playback sources
     */
    public void setAudioPlayer(AudioPlayer audioPlayer);

    /**
     * Returns the current {@link org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer}
     * @return currently used AudioPlayer
     */
    public AudioPlayer getAudioPlayer();

}
