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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.statement;

import java.util.regex.Pattern;

/**
 * "@request type name;"
 *
 * This is the counter part to the @provide statement and pulls the data from
 * the vertex/geometry shader!
 * If there is now corresponding @provide statement the shader won't compile!
 */
public class RequestParser extends BasicStatementParser {

    /**
     * Capture Groups: (example: "@request vec2 texCoord;")
     * - 0 type (example: "vec2")
     * - 1 name (example: "texCoord")
     */
    public static final Pattern REGEX = Pattern.compile("@request\\s([a-zA-Z0-9]*)\\s([a-zA-Z0-9]*);");

    public RequestParser() {
        super(REGEX);
    }

    public String getType(String input) {
        return getGroup(input, 0);
    }

    public String getName(String input) {
        return getGroup(input, 1);
    }
}