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
 * "@import [module from ] lib.slib"
 *
 * This will import the 'module' from the 'lib' shader lib.
 *
 * If the lib to import from doesn't declare the requested module, the shader won't compile!
 *
 * You can also just use <code>@import lib.slib</code> and it will import the nameless
 * module by default!
 *
 * Imports are global and cannot be inside a VERTX or similar block, they are global
 * since they can modify all shader types!
 */
public class ImportParser extends BasicStatementParser {

    public static final Pattern REGEX = Pattern.compile("@import\\s(([a-zA-Z0-9]*)\\sfrom\\s)?([a-zA-Z0-9]*)\\.slib;");

    public ImportParser() {
        super(REGEX);
    }

    public String getLib(String input) {
        return getGroup(input, 2);
    }

    public String getModule(String input) {
        return getGroup(input, 1);
    }
}
