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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.AudioSourceState;
import org.achtern.AchternEngine.core.input.Key;
import org.achtern.AchternEngine.core.input.event.listener.KeyListener;
import org.achtern.AchternEngine.core.input.event.listener.trigger.KeyTrigger;
import org.achtern.AchternEngine.core.input.event.payload.KeyEvent;
import org.achtern.AchternEngine.core.input.inputmap.KeyMap;

/**
 * This will play, pause and stop AudioSources based on keyboard presses.
 * This trigger will keep the AudioSource stopped until the play key has been pressed.
 * <br>
 * Default keys:
 * <br>
 * - PLAY:  L<br>
 * - PAUSE: K<br>
 * - STOP:  J<br>
 */
@Getter(AccessLevel.PROTECTED)
public class KeyboardAudioTrigger implements AudioTrigger, KeyListener {

    protected Key playKey;

    protected Key pauseKey;

    protected Key stopKey;

    @Setter(AccessLevel.PROTECTED)
    protected AudioSourceState next = AudioSourceState.STOPPED;

    /**
     * Construct a new KeyboardAudioTrigger with custom keys.
     *
     * The {@link org.achtern.AchternEngine.core.input.inputmap.KeyMap} is used in the constructor only for registering.
     * @param keyMap this trigger will register itself on this keymap
     * @param playKey key to indicate a play
     * @param pauseKey key to indicate a pause
     * @param stopKey key to indicate a stop
     */
    public KeyboardAudioTrigger(KeyMap keyMap, Key playKey, Key pauseKey, Key stopKey) {
        this.playKey = playKey;
        this.pauseKey = pauseKey;
        this.stopKey = stopKey;

        keyMap
                .register(new KeyTrigger(getPlayKey()), this)
                .register(new KeyTrigger(getPauseKey()), this)
                .register(new KeyTrigger(getStopKey()), this);
    }

    /**
     * Construct a new KeyboardAudioTrigger with the default keys (J, K, L).
     *
     * The {@link org.achtern.AchternEngine.core.input.inputmap.KeyMap} is used in the constructor only for registering.
     * @param keyMap this trigger will register itself on this keymap
     */
    public KeyboardAudioTrigger(KeyMap keyMap) {
        this(keyMap, Key.L, Key.K, Key.J);
    }

    /**
     * This tells the AudioEngine the desired state for any given AudioSource.
     *
     * @param as AudioSource to test
     * @return desired state
     */
    @Override
    public AudioSourceState next(AudioSource as) {
        return getNext();
    }

    /**
     * This method just sets the internal state, which is returned by
     *  {@link #next(AudioSource)}
     * @param event The KeyEvent
     */
    @Override
    public void onAction(KeyEvent event) {
        Key eKey = event.getKey();

        if (eKey.equals(getPlayKey())) {
            setNext(AudioSourceState.PLAYING);
        } else if (eKey.equals(getPauseKey())) {
            setNext(AudioSourceState.PAUSED);
        } else if (eKey.equals(getStopKey())) {
            setNext(AudioSourceState.STOPPED);
        }
    }
}
