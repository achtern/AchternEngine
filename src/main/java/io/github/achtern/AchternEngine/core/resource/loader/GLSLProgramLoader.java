package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;

public class GLSLProgramLoader extends AsciiFileLoader<GLSLProgram> {

    private GLSLProgram program;

    @Override
    public void load(String name, String file) throws LoadingException {
        program = new GLSLProgram(name, file);
        try {
            program.parse(new GLSLParser());
        } catch (Exception e) {
            throw new LoadingException("Error parsing GLSLProgram", e);
        }
    }

    @Override
    public GLSLProgram get() throws Exception {
        return program;
    }
}
