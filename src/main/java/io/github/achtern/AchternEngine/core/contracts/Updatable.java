package io.github.achtern.AchternEngine.core.contracts;

/**
 * Classes which implement this interface
 * indicate that they have to be updated in a specific
 * interval.
 */
public interface Updatable {

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     * @param delta The delta time
     */
    public void update(float delta);

}
