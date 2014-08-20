package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;

public class GLSLProgramLoader extends Loader<GLSLProgram> {


    private String name;
    private String file;

    @Override
    public void parse(String name, String file) throws ParsingException {
        this.name = name;
        this.file = file;
    }

    @Override
    public GLSLProgram get() throws Exception {
        GLSLProgram program = new GLSLProgram(name, file);
        program.parse(new GLSLParser());
        return program;
    }
}
