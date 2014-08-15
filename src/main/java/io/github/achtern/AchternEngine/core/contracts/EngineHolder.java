package io.github.achtern.AchternEngine.core.contracts;

/**
 * Indicates that a class holdes an engine
 * In AchternEngine this will be CoreEngine or RenderEngine
 * in most cases.
 * @param <T> A Engine to hold
 */
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
