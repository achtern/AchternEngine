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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;

/**
 * The AudioListener represents the 'ears' of the 'camera'.
 *
 * There can only be one tied to the OpenAL engine at all times.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class AudioListener extends QuickEntity implements Audio {

    /**
     * The velocity of the listener, for doppler effects etc.
     *
     * Not needed in most cases.
     *
     * @return current velocity
     * @param velocity new velocity
     */
    protected Vector3f velocity;

    protected AudioPlayer audioPlayer;

    /**
     * Creates an AudioListener with no velocity
     */
    public AudioListener() {
        this(Vector3f.ZERO.get(), null);
    }

    @Override
    public void update(float delta) {
        getAudioPlayer().getDataBinder().upload(this);
    }

    public Vector3f getUp() {
        return getTransform().getTransformedRotation().getUp();
    }

    public Vector3f getForward() {
        return getTransform().getTransformedRotation().getForward();
    }

    public Vector3f getPosition() {
        return getTransform().getTransformedPosition();
    }
}
