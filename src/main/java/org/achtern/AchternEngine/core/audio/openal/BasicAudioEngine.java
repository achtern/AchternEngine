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
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter;
import org.achtern.AchternEngine.core.scenegraph.scanning.SingleEntityRetriever;

import java.util.ArrayList;
import java.util.List;

public class BasicAudioEngine implements AudioEngine {

    @Getter @Setter protected AudioListener audioListener;

    @Getter @Setter protected AudioPlayer audioPlayer;

    protected List<AudioEmitter> emitter = new ArrayList<AudioEmitter>();

    /**
     * Add this {@link org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter} to the engine.
     *
     * @param emitter will be added
     */
    @Override
    public void addEmitter(AudioEmitter emitter) {
        this.emitter.add(emitter);
        if (emitter.getAudioSource().isLoop()) {
            // TODO: check if it should start immediately
            getAudioPlayer().play(emitter.getAudioSource());
        }
    }

    /**
     * Add all {@link org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter} containing in the Node
     * (and its children) to the engine.
     *
     * @param node emitter in the node will be added
     */
    @Override
    public void addEmitter(Node node) {
        SingleEntityRetriever ser = new SingleEntityRetriever();

        for (AudioEmitter e : ser.getAll(AudioEmitter.class)) {
            addEmitter(e);
        }
    }

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     *
     * @param delta The delta time
     */
    @Override
    public void update(float delta) {
        getAudioPlayer().getDataBinder().upload(getAudioListener());
        for (AudioEmitter e : this.emitter) {
            getAudioPlayer().getDataBinder().upload(e.getAudioSource());
        }
    }
}
