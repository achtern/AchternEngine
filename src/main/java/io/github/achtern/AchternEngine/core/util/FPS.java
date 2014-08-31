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

package io.github.achtern.AchternEngine.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple FPS Counter
 */
public class FPS {

    /**
     * This logger will log the FPS.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(FPS.class);

    /**
     * Stores how many frames got rendered so far.
     * Will get reset every second
     */
    private int frames;
    /**
     * Keeps track of time passed.
     */
    private double frameCounter;
    /**
     * The FPS of the last second.
     */
    private int fps;
    /**
     * The average fps during runtime
     */
    private float average;

    /**
     * Initializes everything with 0.
     */
    public FPS() {
        frames = 0;
        frameCounter = 0;
        fps = 0;
    }

    /**
     * Adds time passed
     * @param passed How many has passed so far.
     */
    public void passed(double passed) {
        frameCounter += passed;
    }

    /**
     * Should get invoked on every frame render.
     */
    public void rendered() {
        frames++;
    }

    /**
     * Dispays the FPS (Logs it)
     * and calculates average fps
     */
    public void display() {

        // If a second has passed print it
        if (frameCounter >= 1.0) {
            // Set fps to frames passed
            fps = frames;
            // If the average is 0, just set it to fps
            // will be on first iteration the case
            if (average == 0) {
                average = fps;
            } else {
                // Otherwise calculate the average
                average += fps;
                average /= 2;
            }

            LOGGER.info("FPS: {} (Average: {})", fps, (float) Math.round(average * 100) / 100);
            // Reset frames and frameCounter
            frameCounter = frames = 0;
        }
    }

    /**
     * Returns the fps of the last second.
     * @return fps
     */
    public int get() {
        return fps;
    }

    /**
     * Returns the fps average during runtime
     * @return fps average
     */
    public float getAverage() {
        return average;
    }
}
