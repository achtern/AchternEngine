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

package io.github.achtern.AchternEngine.core.scenegraph;

public class ShortLifeNode extends Node {

    protected final float maxLifeTime;

    protected float lifeTime;

    public ShortLifeNode(String name, float maxLifeTime) {
        super(name);
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = 0;
    }

    public ShortLifeNode(float maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = 0;
    }

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     *
     * @param delta The delta time
     */
    @Override
    public void update(float delta) {
        super.update(delta);

        if (this.lifeTime >= this.maxLifeTime) {
            // detach node
            this.getParent().remove(this);
        }

        this.lifeTime += delta;
    }
}
