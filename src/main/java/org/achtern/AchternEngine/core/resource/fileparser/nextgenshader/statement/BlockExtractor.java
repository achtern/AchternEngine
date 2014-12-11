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

public class BlockExtractor extends BasicStatementParser {

    /**
     * Capture Groups: (example: "#---VERTEX---#..stuff...")
     * - 0 everything (example: "#---VERTEX---#..stuff...")
     * - 1 garbage (example: null)
     * - 2 type (example: "VERTEX")
     * - 3 content (example: "..stuff...")
     */
    public static final Pattern REGEX = Pattern.compile("(.|\\n)*#---(.*)---#((.|\\n)*)");

    @AllArgsConstructor
    public enum Groups implements GroupProvider {
        EVERYTHING(0),
        GARBAGE(1),
        TYPE(2),
        CONTENT(3);

        protected int group;

        @Override
        public int get() {
            return group;
        }
    }

    public BlockExtractor() {
        super(REGEX);
    }

    public BlockExtractor(String input) {
        super(REGEX, input);
    }

    public String getType() {
        return getGroup(Groups.TYPE);
    }

    public String getType(String input) {
        return getGroup(input, Groups.TYPE);
    }

    public String getContent() {
        return getGroup(Groups.CONTENT);
    }

    public String getContent(String input) {
        return getGroup(input, Groups.CONTENT);
    }
}