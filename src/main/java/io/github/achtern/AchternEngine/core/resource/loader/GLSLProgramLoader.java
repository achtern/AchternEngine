package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;

/**
 * Loads a {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram}.
 * @see io.github.achtern.AchternEngine.core.resource.loader.AsciiFileLoader
 */
public class GLSLProgramLoader extends AsciiFileLoader<GLSLProgram> {

    /**
     * GLSLProgram which was loaded from the file.
     */
    private GLSLProgram program;

    /**
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     *
     * <strong>NOTE:</strong>
     * This also triggers the loading and parsing of the sub-shader-programs
     * associated with the program.
     *
     * @param name The name of the original file
     * @param file The input file
     * @throws LoadingException when the loading fails
     */
    @Override
    public void load(String name, String file) throws LoadingException {
        program = new GLSLProgram(name, file);
        try {
            program.parse(new GLSLParser());
        } catch (Exception e) {
            throw new LoadingException("Error parsing GLSLProgram", e);
        }
    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * @return The new object
     * @throws Exception
     */
    @Override
    public GLSLProgram get() throws Exception {
        return program;
    }
}
