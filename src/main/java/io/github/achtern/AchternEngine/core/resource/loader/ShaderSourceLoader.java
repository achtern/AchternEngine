package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;

public class ShaderSourceLoader extends Loader<String> {

    protected String file;

    protected LineBasedParser parser;

    public ShaderSourceLoader() {
        this(new GLSLParser());
    }

    public ShaderSourceLoader(LineBasedParser parser) {
        this.parser = parser;
    }

    @Override
    public LineBasedParser getPreProcessor() {
        return this.parser;
    }

    @Override
    public void parse(String name, String file) throws ParsingException {
        this.file = file;
    }

    @Override
    public String get() throws Exception {
        return file;
    }
}
