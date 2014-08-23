package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;

/**
 * A ShaderSourceLoader is just a {@link io.github.achtern.AchternEngine.core.resource.loader.PassThroughLoader},
 * which specifies the LineBasedParser (
 * {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser}).
 * @see io.github.achtern.AchternEngine.core.resource.loader.PassThroughLoader
 */
public class ShaderSourceLoader extends PassThroughLoader {
    /**
     * Parser to use, defaults to {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser}
     */
    protected LineBasedParser parser;

    /**
     * Constructs with a {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser}
     */
    public ShaderSourceLoader() {
        this(new GLSLParser());
    }

    /**
     * Constructs with a custom parser
     * @param parser parser to use
     */
    public ShaderSourceLoader(LineBasedParser parser) {
        this.parser = parser;
    }

    /**
     * The preprocessor is used during reading.
     * There is no guarantee that this {@link io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
     * will get called. (When reading from cache for example, no PreProcessor is getting called).
     * @return LineBasedParser
     */
    @Override
    public LineBasedParser getPreProcessor() {
        return this.parser;
    }
}
