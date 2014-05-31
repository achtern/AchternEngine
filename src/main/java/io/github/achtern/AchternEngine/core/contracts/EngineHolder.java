package io.github.achtern.AchternEngine.core.contracts;

public interface EngineHolder<T> {

    /**
     * Inject the engine
     * @param engine The engine to store
     */
    public void setEngine(T engine);

    /**
     * Retrieves the stored engine
     * @return The stored engine
     */
    public T getEngine();

}
