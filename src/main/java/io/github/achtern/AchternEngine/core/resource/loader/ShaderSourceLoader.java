package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;

/**
 * A ShaderSourceLoader is just a passthrough Loader,
 * which specifies the LineBasedParser (
 * {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser}).
 */
public class ShaderSourceLoader extends AsciiFileLoader<String> {

    /**
     * Stores file contents
     */
    protected String file;

    /**
     * Parser to use, defaults to {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser}
     */
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
    public void load(String name, String file) throws LoadingException {
        this.file = file;
    }

    @Override
    public String get() throws Exception {
        return file;
    }
}
