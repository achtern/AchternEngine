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

import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.input.Key;
import org.achtern.AchternEngine.core.input.event.listener.KeyListener;
import org.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import org.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import org.achtern.AchternEngine.core.input.inputmap.KeyMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class KeyboardAudioTriggerTest {

    @Mock
    KeyMap keyMap;

    @Mock
    KeyEvent keyEvent;


    KeyboardAudioTrigger t;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        when(keyMap.register((KeyTrigger) any(), (KeyListener) any()))
                .thenReturn(keyMap);

        t = new KeyboardAudioTrigger(keyMap, Key.A, Key.B, Key.C);
    }

    @Test
    public void testConstructor() {
        verify(keyMap, times(3)).register((KeyTrigger) any(), eq(t));
        verifyNoMoreInteractions(keyMap);
    }

    @Test
    public void testNext() {
        testNextInternal(Key.A, Key.B, Key.C);
    }

    @Test
    public void testDefaults() {
        t = new KeyboardAudioTrigger(keyMap);
        testNextInternal(Key.L, Key.K, Key.J);
    }

    protected void testNextInternal(Key play, Key pause, Key stop) {
        assertEquals("Should return STOPPED by default", AudioSourceState.STOPPED, t.next(null));

        when(keyEvent.getKey()).thenReturn(play);
        t.onAction(keyEvent);

        assertEquals("Should return PLAYING after the play key has been pressed", AudioSourceState.PLAYING, t.next(null));

        when(keyEvent.getKey()).thenReturn(pause);
        t.onAction(keyEvent);

        assertEquals("Should return PAUSED after the pause key has been pressed", AudioSourceState.PAUSED, t.next(null));

        when(keyEvent.getKey()).thenReturn(stop);
        t.onAction(keyEvent);

        assertEquals("Should return STOPPED after the stop key has been pressed", AudioSourceState.STOPPED, t.next(null));
    }
}