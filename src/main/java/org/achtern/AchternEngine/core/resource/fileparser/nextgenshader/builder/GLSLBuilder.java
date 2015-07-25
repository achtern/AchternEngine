/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder;

import org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.validator.MainBlockValidator;
import lombok.Getter;
import lombok.Setter;

public class GLSLBuilder {

    public static final String VERSION = "#version 330 core";

    /**
     * Nextgen source of the vertex shader (can be retrieved using
     * {@link org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.parser.PipelineSeparator})
     *
     * @param vertex source code
     * @return source code
     */
    @Getter @Setter protected String vertex;
    /**
     * Nextgen source of the vertex shader (can be retrieved using
     * {@link org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.parser.PipelineSeparator})
     *
     * @param fragment source code
     * @return source code
     */
    @Getter @Setter protected String fragment;
    /**
     * Nextgen source of the vertex shader (can be retrieved using
     * {@link org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.parser.PipelineSeparator})
     *
     * @param geometry source code
     * @return source code
     */
    @Getter @Setter protected String geometry;

    private final MainBlockValidator _mainBlockValidator = new MainBlockValidator();

    /**
     * This just checks whether the given source file contains a main() method to call.
     * @param source input source file
     * @return whether the source is valid.
     */
    public boolean isValidSource(String source) {
        return _mainBlockValidator.isValid(source);
    }

}
