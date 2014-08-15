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
