/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Christian Gärtner
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

package org.achtern.AchternEngine.core.audio.openal.trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.scenegraph.Node;

/**
 * This will play AudioSources if they are in proximity to the given Node.
 */
@AllArgsConstructor
public class ProximityAudioTrigger implements AudioTrigger {

    @Getter @Setter protected Node target;
    @Getter protected float maxDistance;
    @Getter @Setter protected AudioSourceState onExit = AudioSourceState.PAUSED;


    /**
     * Creates a new ProximityAudioTrigger with a default maxDistance of 10
     * @param target target of the AudioTrigger
     */
    public ProximityAudioTrigger(Node target) {
        this(target, 10, AudioSourceState.PAUSED);
    }

    /**
     * This tells the AudioEngine the desired state for any given AudioSource.
     *
     * @param as AudioSource to test
     * @return desired state
     */
    @Override
    public AudioSourceState next(AudioSource as) {
        Vector3f c = as.getPosition();
        Vector3f t = target.getTransform().getPosition();

        // very rough but fast check
        Vector3f subtracted = c.sub(t);
        if (subtracted.getX() + subtracted.getY() + subtracted.getZ() > maxDistance) {
            // cannot be in distance
            return onExit;
        }

        // perform precise check
        if (Vector3f.distance(c, t) > maxDistance) {
            return onExit;
        }

        // in proximity, play the source!
        return AudioSourceState.PLAYING;
    }
}
