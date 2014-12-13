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
package org.achtern.AchternEngine.core.scenegraph;


import lombok.Getter;
import lombok.Setter;
import org.achtern.AchternEngine.core.util.Callback;

/**
 * A ShortLifeNode is just a standard {@link org.achtern.AchternEngine.core.scenegraph.Node}
 * with a limited lifetime. After this lifetime has passed, it gets removed from the scenegraph.
 *
 * You can also provide your own on death {@link org.achtern.AchternEngine.core.util.Callback} for each Node.
 * This callback gets called <strong>before</strong> the removal of the Node!
 */
public class ShortLifeNode extends Node {

    /**
     * This hints the ShortLifeNode how to behave,
     * once the lifespan ends.
     *
     * This hint is only a hint. No more, no less.
     */
    public enum Mode {
        /**
         * The Node has just been attached.
         *
         * Keep resources active
         */
        DETACH,
        /**
         * Destroy all associated resource.
         */
        DESTROY
    }

    /**
     * The max lifetime this Node has,
     * This value should be in seconds!
     *
     * After {@link #lifeTime} is greater than maxLifeTime
     * it gets removed.
     */
    protected final float maxLifeTime;

    /**
     * The amount of time the Node has "lived" in the scenegraph
     * in seconds
     */
    protected float lifeTime;

    /**
     * The Mode hint
     */
    protected Mode mode;

    /**
     * This callback gets called, when the life has end of this Node.
     * It gets called before {@link #removed()}.
     */
    @Getter @Setter protected Callback<ShortLifeNode> deathCallback;

    /**
     * Construct a ShortLifeNode. This constructor provides access to almost all (semi) public fields
     * @param name The Name of the Node
     * @param maxLifeTime The maximum lifetime in seconds
     * @param mode The {@link org.achtern.AchternEngine.core.scenegraph.ShortLifeNode.Mode} hint
     * @param callback Death callback {@link #deathCallback}
     */
    public ShortLifeNode(String name, float maxLifeTime, Mode mode, Callback<ShortLifeNode> callback) {
        super(name);
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = 0;
        this.deathCallback = callback;
    }

    /**
     * Construct a ShortLifeNode. This constructor provides acces to almost all (semi) public fields,
     * but leaves the Node untitled.
     * @param maxLifeTime The maximum lifetime in seconds
     * @param mode The {@link org.achtern.AchternEngine.core.scenegraph.ShortLifeNode.Mode} hint
     * @param callback Death callback {@link #deathCallback}
     */
    public ShortLifeNode(float maxLifeTime, Mode mode, Callback<ShortLifeNode> callback) {
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = 0;
        this.deathCallback = callback;
    }

    /**
     * Construct a ShortLifeNode with a name and max. life time.
     * The death callback defaults to null;
     * the Mode to {@link org.achtern.AchternEngine.core.scenegraph.ShortLifeNode.Mode#DETACH}
     * @param name The Name of the Node
     * @param maxLifeTime The maximum lifetime in seconds
     */
    public ShortLifeNode(String name, float maxLifeTime) {
        this(name, maxLifeTime, Mode.DETACH, null);
    }

    /**
     * Construct a ShortLifeNode with just a maximum life time.
     * The death callback defaults to null;
     * the Mode to {@link org.achtern.AchternEngine.core.scenegraph.ShortLifeNode.Mode#DETACH}
     * @param maxLifeTime The maximum lifetime in seconds
     */
    public ShortLifeNode(float maxLifeTime) {
        this(maxLifeTime, Mode.DETACH, null);
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

        this.lifeTime += delta;

        if (this.lifeTime >= this.maxLifeTime) {
            // detach node
            if (getDeathCallback() != null) {
                getDeathCallback().call(this);
            }
            this.getParent().remove(this);
        }
    }
}
