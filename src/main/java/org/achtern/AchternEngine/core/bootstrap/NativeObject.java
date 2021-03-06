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

package org.achtern.AchternEngine.core.bootstrap;

/**
 * A NativeObject is used by the Graphics Engine
 * (e.g. OpenGL) and lives therefor most of the times
 * on the graphics card. It has an ID associated with it
 * and maybe buffers.
 */
public abstract class NativeObject implements Native {

    /**
     * The ID is used to identify the object.
     * -1 indicates, that the ID has not been
     * set yet and has to be uploaded to the
     * Graphics Engine!
     */
    private int id = INVALID_ID;

    public NativeObject() {
    }


    /**
     * Returns the corresponding ID,
     * -1 indicates, no ID has been set
     * @return ID
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * internal use only
     */
    @Override
    public void setID(int id) {
        this.id = id;
    }
}
