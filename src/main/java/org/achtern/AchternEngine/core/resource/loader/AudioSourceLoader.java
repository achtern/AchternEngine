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

package org.achtern.AchternEngine.core.resource.loader;

import org.achtern.AchternEngine.core.audio.openal.AudioBuffer;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.Format;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class AudioSourceLoader extends BinaryLoader<AudioSource,AudioBuffer> {


    protected ByteBuffer data;

    protected Format format;

    protected int frequency;

    /**
     * This method should construct the object from a cached
     * value provided by the ResourceLoader
     *
     * @param value Cache
     * @return Object
     * @throws Exception could be anything (from loading over parsing to memory errors)
     */
    @Override
    public AudioSource fromCache(AudioBuffer value) throws Exception {
        return new AudioSource(value, null, null, null);
    }

    /**
     * Returns the Type of the Cache data.
     *
     * @return data type
     */
    @Override
    public Class<AudioBuffer> getCacheType() {
        return AudioBuffer.class;
    }

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     *
     * @param name  The name of the original file
     * @param input The input file
     * @throws org.achtern.AchternEngine.core.resource.loader.LoadingException when the loading fails
     */
    @Override
    public void load(String name, InputStream input) throws LoadingException {

        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(input);
        } catch (UnsupportedAudioFileException e) {
            throw new LoadingException("Unssported Audio format", e);
        } catch (IOException e) {
            throw new LoadingException("Could not read audio", e);
        }

        AudioFormat javaFormat = ais.getFormat();
        int ssib = javaFormat.getSampleSizeInBits();

        if (ssib != 8 && ssib != 16) {
            throw new LoadingException("Illegal SampleSize <" + ssib + ">");
        }

        Format format = null;

        switch (javaFormat.getChannels()) {
            case 1:
                if (ssib == 8) {
                    format = Format.MONO8;
                } else {
                    format = Format.MONO16;
                }
                break;

            case 2:
                if (ssib == 8) {
                    format = Format.STEREO8;
                } else {
                    format = Format.STEREO16;
                }
                break;
            default:
                throw new LoadingException("Support for mono / stereo files only.");
        }

        try {
            int available = ais.available();

            if (available <= 0) {
                available = javaFormat.getChannels() * (int) ais.getFrameLength() * ssib / 8;
            }

            byte[] bbuffer = new byte[available];

            int r, total = 0;

            while ((r = ais.read(bbuffer, total, bbuffer.length - total)) != -1 && total < bbuffer.length) {
                total += r;
            }


            ByteBuffer buffer = ByteBuffer.allocateDirect(bbuffer.length);

            buffer.order(ByteOrder.nativeOrder());

            ByteBuffer src = ByteBuffer.wrap(bbuffer);

            src.order(javaFormat.isBigEndian() ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);


            if (ssib == 16) {
                // Stereo
                ShortBuffer buffer_s = buffer.asShortBuffer();
                ShortBuffer src_s = src.asShortBuffer();

                while (src_s.hasRemaining()) {
                    buffer_s.put(src_s.get());
                }

            } else {
                // Mono
                while (src.hasRemaining()) {
                    buffer.put(src.get());
                }
            }

            buffer.rewind();


            this.data = buffer;
            this.format = format;
            this.frequency = (int) javaFormat.getSampleRate();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     *
     * @return The new object
     * @throws Exception anything can go wrong ;)
     */
    @Override
    public AudioSource get() throws Exception {
        AudioBuffer buffer = new AudioBuffer(data, frequency, format);

        cache(buffer);
        this.data.clear();

        return new AudioSource(buffer, null, null, null);
    }
}
