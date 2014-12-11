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

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

/**
 * "@write[(slot)] variable;"
 *
 * This can only be used in the fragment shader and out a given variable into the
 * buffers.
 * The slot in parenthesis is optional and defaults to 0.
 */
public class WriteParser extends BasicStatementParser {

    /**
     * Capture Groups: (example: "@write(0) out;")
     * - 0 garbage (example: "(0)")
     * - 1 slot (example: "0")
     * - 2 varName (example: "out")
     */
    public static final Pattern REGEX = Pattern.compile("@write(\\(([0-9]*)\\))?\\s([a-zA-Z0-9]*);");

    @AllArgsConstructor
    public enum Groups implements GroupProvider {
        GARBAGE(0),
        SLOT(1),
        NAME(2);


        protected int group;

        @Override
        public int get() {
            return group;
        }
    }

    public WriteParser() {
        super(REGEX);
    }

    public int getSlot(String input) {
        String i = getGroup(input, Groups.SLOT);
        if (i == null) {
            return 0;
        }

        return Integer.parseInt(i);
    }

    public String getName(String input) {
        return getGroup(input, Groups.NAME);
    }
}
