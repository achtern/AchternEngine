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

import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.audio.openal.trigger.AudioTrigger;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter;
import org.achtern.AchternEngine.core.scenegraph.scanning.SingleEntityRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAudioEngine implements AudioEngine {

    @Getter @Setter protected AudioListener audioListener;

    @Getter @Setter protected AudioPlayer audioPlayer;

    protected Map<AudioTrigger, List<AudioEmitter>> emitter = new HashMap<AudioTrigger, List<AudioEmitter>>();

    /**
     * Add this {@link org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter} to the engine under the given
     * trigger.
     *
     * @param trigger will trigger playback of the emitter
     * @param emitter the emitter to add
     */
    @Override
    public void addEmitter(AudioTrigger trigger, AudioEmitter emitter) {
        if (this.emitter.get(trigger) == null) {
            this.emitter.put(trigger, new ArrayList<AudioEmitter>());
        }
        this.emitter.get(trigger).add(emitter);
    }

    /**
     * Add all emitter in a given node (first level only) to the engine
     *
     * @param trigger will trigger playback of the emitter
     * @param node    all emitter in the node will be added
     * @see #addEmitter(org.achtern.AchternEngine.core.audio.openal.trigger.AudioTrigger, org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter)
     */
    @Override
    public void addEmitter(AudioTrigger trigger, Node node) {
        SingleEntityRetriever ser = new SingleEntityRetriever();

        for (AudioEmitter e : ser.getAll(AudioEmitter.class)) {
            addEmitter(trigger, e);
        }
    }

    /**
     * Add this {@link org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter} to the engine with a
     *  {@link org.achtern.AchternEngine.core.audio.openal.trigger.PlayAudioTrigger}
     *
     * @param emitter will be added
     */
    @Override
    public void addEmitter(AudioEmitter emitter) {
        addEmitter(PLAY_AUDIO_TRIGGER, emitter);
    }

    /**
     * Add all {@link org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter} containing in the Node
     *  (and its children) to the engine with a  {@link org.achtern.AchternEngine.core.audio.openal.trigger.PlayAudioTrigger}
     *
     * @param node emitter in the node will be added
     */
    @Override
    public void addEmitter(Node node) {
        addEmitter(PLAY_AUDIO_TRIGGER, node);
    }

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     *
     * @param delta The delta time
     */
    @Override
    public void update(float delta) {
        // upload the new data
        getAudioPlayer().getDataBinder().upload(getAudioListener());
        for (Map.Entry<AudioTrigger, List<AudioEmitter>> entry : this.emitter.entrySet()) {
            for (AudioEmitter e : entry.getValue()) {
                AudioSourceState next = entry.getKey().next(e.getAudioSource());

                // ignore it the state is unchanged
                if (e.getAudioSource().getState() == next) {
                    continue;
                }

                switch (next) {
                    case PLAYING:
                        getAudioPlayer().play(e.getAudioSource());
                        break;
                    case PAUSED:
                        getAudioPlayer().pause(e.getAudioSource());
                        break;
                    case STOPPED:
                        getAudioPlayer().stop(e.getAudioSource());
                        break;
                }

                // upload data from playing sources (position, etc)
                if (isPlaying(e)) {
                    getAudioPlayer().getDataBinder().upload(e.getAudioSource());
                }
            }
        }
    }

    public boolean isPlaying(AudioEmitter emitter) {
        return emitter.getAudioSource().getState() == AudioSourceState.PLAYING;
    }
}
