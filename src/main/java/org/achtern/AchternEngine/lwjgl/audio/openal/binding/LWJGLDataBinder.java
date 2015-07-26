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

package org.achtern.AchternEngine.lwjgl.audio.openal.binding;

import lombok.AllArgsConstructor;
import org.achtern.AchternEngine.core.audio.openal.AudioBuffer;
import org.achtern.AchternEngine.core.audio.openal.AudioListener;
import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.audio.openal.binding.DataBinder;
import org.achtern.AchternEngine.core.audio.openal.binding.IDGenerator;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.util.UBuffer;

import java.nio.FloatBuffer;

import static org.achtern.AchternEngine.core.bootstrap.Native.INVALID_ID;
import static org.achtern.AchternEngine.lwjgl.util.GLEnum.getGLEnum;
import static org.lwjgl.openal.AL10.*;

@AllArgsConstructor
public class LWJGLDataBinder implements DataBinder {

    protected LWJGLIDGenerator idGen;

    /**
     * Uploads the buffer.
     *
     * @param buffer to be uploaded
     */
    @Override
    public void upload(AudioBuffer buffer) {
        if (buffer.getID() == INVALID_ID) {
            idGen.generate(buffer);
        }

        alBufferData(
                buffer.getID(),
                getGLEnum(buffer.getFormat()),
                buffer.getData(),
                buffer.getFrequency()
        );
    }

    /**
     * Since sources cannot be 'uploaded' this assigns the buffer
     * in {@link org.achtern.AchternEngine.core.audio.openal.AudioSource} on the OpenAL Engine to the given source
     * and sets the paramters of the source
     *
     * @param source to be setup on the engine
     */
    @Override
    public void upload(AudioSource source) {
        if (source.getID() == INVALID_ID) {
            idGen.generate(source);
        }

        uploadIf(source.getBuffer());

        int id = source.getID();

        alSourcei(id, AL_BUFFER, source.getBuffer().getID());

        boolean relative = source.isRelative();
        alSourcei(id, AL_SOURCE_RELATIVE, relative ? AL_TRUE : AL_FALSE);
        alSourcei(id, AL_SOURCE_ABSOLUTE, relative ? AL_FALSE : AL_TRUE);

        Vector3f pos = source.getPosition();
        alSource3f(id, AL_POSITION, pos.getX(), pos.getY(), pos.getZ());

        Vector3f vel = source.getVelocity();
        alSource3f(id, AL_VELOCITY, vel.getX(), vel.getY(), vel.getZ());

        boolean loop = source.isLoop();
        alSourcei(id, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);

        alSourcef(id, AL_GAIN, source.getGain());
    }

    /**
     * This is not a real upload, but rather a parameter setting, since OpenAL allows one listener only
     *
     * @param listener data to be set
     */
    @Override
    public void upload(AudioListener listener) {
        alListener3f(AL_POSITION,
                listener.getPosition().getX(),
                listener.getPosition().getY(),
                listener.getPosition().getZ()
        );
        alListener3f(AL_VELOCITY,
                listener.getVelocity().getX(),
                listener.getVelocity().getY(),
                listener.getVelocity().getZ()
        );
        alListener(AL_ORIENTATION,
                (FloatBuffer) UBuffer.createFloatBuffer(6).put(new float[]{
                        -listener.getForward().getX(),
                        -listener.getForward().getY(),
                        -listener.getForward().getZ(),
                        listener.getUp().getX(),
                        listener.getUp().getY(),
                        listener.getUp().getZ()
                }).rewind()
        );
    }

    /**
     * only uploads the buffer if and only if it has not been uploaded already.
     * @param buffer to upload iff not uploaded yet
     */
    private void uploadIf(AudioBuffer buffer) {
        if (buffer.getID() == INVALID_ID) {
            upload(buffer);
        }
    }

    @Override
    public IDGenerator getIDGenerator() {
        return idGen;
    }
}
