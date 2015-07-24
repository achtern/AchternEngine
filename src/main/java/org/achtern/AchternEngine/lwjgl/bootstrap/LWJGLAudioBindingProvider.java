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

package org.achtern.AchternEngine.lwjgl.bootstrap;

import lombok.Getter;
import org.achtern.AchternEngine.core.audio.openal.binding.AudioPlayer;
import org.achtern.AchternEngine.core.bootstrap.AudioBindingProvider;
import org.achtern.AchternEngine.lwjgl.audio.openal.binding.LWJGLAudioPlayer;
import org.achtern.AchternEngine.lwjgl.audio.openal.binding.LWJGLDataBinder;
import org.achtern.AchternEngine.lwjgl.audio.openal.binding.LWJGLIDGenerator;

public class LWJGLAudioBindingProvider implements AudioBindingProvider {

    @Getter protected final AudioPlayer audioPlayer;

    public LWJGLAudioBindingProvider() {
        this.audioPlayer = new LWJGLAudioPlayer(new LWJGLDataBinder(new LWJGLIDGenerator()));
    }
}
