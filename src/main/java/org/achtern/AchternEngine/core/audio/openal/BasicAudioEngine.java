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
import org.achtern.AchternEngine.core.scenegraph.entity.AudioEmitter;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Entity;

public class BasicAudioEngine implements AudioEngine {

    @Getter @Setter protected AudioListener audioListener;

    @Getter @Setter protected AudioPlayer audioPlayer;

    /**
     * Starts to playback all audiofiles for the given scenegraph.
     * This method walks the scenegraph recursively.
     *
     * @param node scenegraph
     */
    @Override
    public void play(Node node) {

        // Cycle through all child nodes
        for (Node n : node.getChildren().values()) {
            this.play(node);
        }

        // Get all Entities
        for (Entity e : node.getEntities()) {
            if (!(e instanceof AudioEmitter)) {
                continue;
            }
            AudioEmitter emitter = (AudioEmitter) e;

            getAudioPlayer().play(emitter.getAudioSource());
        }
    }
}
