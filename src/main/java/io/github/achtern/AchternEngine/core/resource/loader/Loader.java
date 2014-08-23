package io.github.achtern.AchternEngine.core.resource.loader;

public interface Loader<T, I> {

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     * @param name The name of the original file
     * @param input The input file
     * @throws LoadingException when the loading fails
     */
    public void load(String name, I input) throws LoadingException;

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * @return The new object
     * @throws Exception
     */
    public T get() throws Exception;

}
