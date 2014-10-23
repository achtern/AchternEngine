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

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

/**
 * "@yield[ variable];"
 *
 * This declares where slib code will go.
 *
 * The vertex shader cannot pass an variable, but the fragment can.
 *
 * This will pass the variable into the <code>vec4 get()</code> method of the lib.
 * The returned value from that will get passed to the next one!
 * The first @import statement will get called first!
 */
public class YieldParser extends BasicStatementParser {

    /**
     * Capture Groups: (example: "@yield out;")
     * - 0 varName (example: "out")
     */
    public static final Pattern REGEX = Pattern.compile("@yield\\s?([a-zA-Z0-9]*)?;");

    @AllArgsConstructor
    public enum Groups implements GroupProvider {
        NAME(0);


        protected int group;

        @Override
        public int get() {
            return group;
        }
    }

    public YieldParser() {
        super(REGEX);
    }

    public String getName(String input) {
        return getGroup(input, Groups.NAME);
    }
}
