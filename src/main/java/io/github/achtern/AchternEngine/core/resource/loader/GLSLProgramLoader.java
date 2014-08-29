/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
