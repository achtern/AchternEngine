package io.github.achtern.AchternEngine.core.resource.fileparser;

/**
 * A LineBasedParser can work on a single
 * line of source code. Independently of the
 * other source code.
 */
public interface LineBasedParser {

    /**
     * Parses the line.
     * Should NOT add a trailing line break to the line.
     * @param line The line to parse
     * @return The parsed line
     * @throws Exception
     */
    public String parse(String line) throws Exception;

}
