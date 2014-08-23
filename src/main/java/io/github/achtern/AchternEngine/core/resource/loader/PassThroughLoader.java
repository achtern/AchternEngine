package io.github.achtern.AchternEngine.core.resource.loader;

/**
 * Just passes the given input String to the {@link #get()} method.
 */
public class PassThroughLoader extends AsciiFileLoader<String> {

    /**
     * input string
     */
    protected String input;

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     *
     * @param name  The name of the original file
     * @param input The input file
     * @throws LoadingException when the loading fails
     */
    @Override
    public void load(String name, String input) throws LoadingException {
        this.input = input;
    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     *
     * @return The new object
     * @throws Exception
     */
    @Override
    public String get() throws Exception {
        return input;
    }
}
