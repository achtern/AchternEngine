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

package org.achtern.AchternEngine.core.scenegraph.entity;

import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.audio.openal.trigger.AudioTrigger;
import org.achtern.AchternEngine.core.math.Vector3f;

public class AudioEmitter extends QuickEntity {


    public static final String NAME_UNTITLED_AUDIO_ENTITY = "Untitled AudioEmitter";

    @Getter @Setter protected AudioTrigger trigger;

    /**
     * The AudioSource to playback
     *
     * @param audioSource assoc. AudioSource
     * @return assoc. AudioSource
     */
    @Getter @Setter protected AudioSource audioSource;

    @Getter @Setter protected AudioPlayer audioPlayer;

    /**
     * Create an "Untitled AudioEmitter"
     */
    public AudioEmitter() {
        super(NAME_UNTITLED_AUDIO_ENTITY);
    }

    /**
     * @see org.achtern.AchternEngine.core.scenegraph.entity.Entity#attached() ()
     */
    @Override
    public void attached() {
        this.audioSource.setPosition(getTransform().getPosition());
        this.audioSource.setVelocity(Vector3f.ZERO);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // update the position of the audioSource
        this.audioSource.setPosition(getTransform().getPosition());

        AudioSourceState next = getTrigger().next(getAudioSource());

        if (!next.equals(getAudioSource().getState())) {

            switch (next) {
                case PLAYING:
                    getAudioPlayer().play(getAudioSource());
                    break;
                case PAUSED:
                    getAudioPlayer().pause(getAudioSource());
                    break;
                case STOPPED:
                    getAudioPlayer().stop(getAudioSource());
                    break;
            }

        }

        // upload data from playing sources (position, etc)
        if (getAudioSource().getState().equals(AudioSourceState.PLAYING)) {
            getAudioPlayer().getDataBinder().upload(getAudioSource());
        }

    }
}
