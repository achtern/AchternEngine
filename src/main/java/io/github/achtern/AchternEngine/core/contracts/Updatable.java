package io.github.achtern.AchternEngine.core.contracts;

public interface Updatable {

    /**
     * Trigger an update.
     * Do you regular updating of nodes/entities in here.
     * @param delta The delta time
     */
    public void update(float delta);

}
