package io.github.achtern.AchternEngine.core.contracts;

public interface Inputable {

    /**
     * Trigger an input.
     * Grab information from {@link io.github.achtern.AchternEngine.core.Input} now.
     * @param delta The delta time
     */
    public void input(float delta);

}
