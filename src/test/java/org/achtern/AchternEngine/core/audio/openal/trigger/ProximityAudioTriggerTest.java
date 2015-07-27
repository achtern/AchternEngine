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

import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProximityAudioTriggerTest {


    @Test
    public void testNext() {

        Node target = new Node();
        AudioSource source = new AudioSource(null);


        ProximityAudioTrigger t = new ProximityAudioTrigger(target);

        target.getTransform().setPosition(Vector3f.ZERO.get());
        source.setPosition(Vector3f.ZERO.get());

        assertEquals("Should return PLAYING, if target and source have the same position",
                AudioSourceState.PLAYING,
                t.next(source)
        );

        target.getTransform().setPosition(Vector3f.ZERO.get());
        source.setPosition(new Vector3f(9.9f, 0, 0));

        assertEquals("Should return PLAYING, if target and source are less then maxDistance apart",
                AudioSourceState.PLAYING,
                t.next(source)
        );

        target.getTransform().setPosition(new Vector3f(10, 0, 0));
        source.setPosition(new Vector3f(0, 0, 0));

        assertEquals("Should return PLAYING, if target and source are exactly maxDistance apart",
                AudioSourceState.PLAYING,
                t.next(source)
        );


        target.getTransform().setPosition(new Vector3f(10.1f, 0, 0));
        source.setPosition(new Vector3f(0, 0, 0));

        assertEquals("Should return PAUSED, if target and source too far apart and no onExit operation has been set",
                AudioSourceState.PAUSED,
                t.next(source)
        );


        t = new ProximityAudioTrigger(target, 4, AudioSourceState.STOPPED);

        target.getTransform().setPosition(new Vector3f(4.001f, 0, 0));
        source.setPosition(new Vector3f(0, 0, 0));

        assertEquals("Should return the set onExit operation and a accept a custom maxDistance, if target and source too far apart",
                AudioSourceState.STOPPED,
                t.next(source)
        );


        target.getTransform().setPosition(new Vector3f(5, 0, 0));
        source.setPosition(new Vector3f(0, 0, 0));

        assertEquals("Should return the set onExit operation, if target and source too far apart",
                AudioSourceState.STOPPED,
                t.next(source)
        );

    }

}