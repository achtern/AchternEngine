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

package org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.statement;

import java.util.regex.Pattern;

/**
 * "#extends shader_file"
 *
 * extends can be used to re-use all code from the given shader file
 * name.
 *
 * This allows you to add additional @import statements.
 * Especially usefull, when you have a basic diffuse shader
 * and based on light-type want to import a diffrent set of lighting
 * methods.
 *
 * You can only include ".shader" files! NOT shader libs (".slib")
 */
public class ExtendsParser extends BasicStatementParser {

    public static final Pattern REGEX = Pattern.compile("#extends\\s([a-zA-Z0-9]*)$");

    public ExtendsParser() {
        super(REGEX);
    }

    public String getParent(String input) {
        return REGEX.matcher(input).group();
    }

}