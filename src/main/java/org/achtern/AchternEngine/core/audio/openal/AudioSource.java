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
import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.bootstrap.NativeObject;
import org.achtern.AchternEngine.core.math.Vector3f;

@Getter
@Setter
@AllArgsConstructor
public class AudioSource extends NativeObject {

    /**
     * Associated {@link org.achtern.AchternEngine.core.audio.openal.AudioBuffer}
     *
     * @param buffer New buffer to use with this AudioSource
     * @return the currently assoc. AudioBuffer
     */
    protected AudioBuffer buffer;

    /**
     * Position of this AudioSource in 3D space.
     *
     * This can be both in absolute or relative coordinates to the
     *  {@link org.achtern.AchternEngine.core.audio.openal.AudioListener}.
     *
     * @see org.achtern.AchternEngine.core.audio.openal.AudioSource#isRelative()
     *
     * @param position Sets the new position
     * @return current position
     */
    protected Vector3f position;

    /**
     * The velocity of the AudioSource, for doppler effects etc.
     *
     * Not needed in most cases.
     *
     * @param velocity new velocity
     * @return current velocity
     */
    protected Vector3f velocity;

    /**
     * Indicates whether this AudioSource should be played in a loop.
     *
     * @param loop if it should be looped
     * @return if it will loop
     */
    protected boolean loop;

    /**
     * If this AudioSource is not relative, {@link #getPosition()} will be interpreted
     *  as absolute world coordiantes, otherwise as offset from the
     *  {@link org.achtern.AchternEngine.core.audio.openal.AudioListener}.
     *
     * @param relative if position is relative
     * @return if position is relative
     */
    protected boolean relative;

    /**
     * Gain of this AudioSource. 0.0f is minimum and 1.0f maximum.
     *
     * Defaults to 1.0f
     *
     * NOTE: the 1.0f maximum is NOT enforced. Some drivers may allow gain over 1.0f
     *
     * @param gain gain of the AudioSource
     * @return current gain of the AudioSource.
     */
    protected float gain;

    public AudioSource(AudioBuffer buffer) {
        this(buffer, null, null);
    }

    public AudioSource(AudioBuffer buffer, Vector3f position, Vector3f velocity) {
        this(buffer, position, velocity, false, false, 1.0f);
    }
}
