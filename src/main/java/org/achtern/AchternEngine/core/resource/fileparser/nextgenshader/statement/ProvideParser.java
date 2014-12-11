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
 * "@provide type name = expr;"
 *
 * <code>@provide</code> can only be used inside the main method of the vertex block.
 * It will generate the out statements and assign the value.
 * If there is now shader which is requesting the value, the statement will get stripped!
 */
public class ProvideParser extends BasicStatementParser {

    /**
     * Capture Groups: (example: "@provide vec2 texCoord = vec4();")
     * - 0 statement (example: "vec2 texCoord = vec4();")
     * - 1 definition/declaration (example: "vec2 texCoord ")
     * - 2 type (example: "vec2")
     * - 3 name (example: "texCoord")
     * - 4 expression (example: " vec4()")
     */
    public static final Pattern REGEX = Pattern.compile("@provide\\s((([a-zA-Z0-9]*)\\s([a-zA-Z0-9]*).*)\\s?=(.*);)");

    @AllArgsConstructor
    public enum Groups implements GroupProvider {
        STATEMENT(0),
        DECLARATION(1),
        TYPE(2),
        NAME(3),
        EXPRESSION(4);


        protected int group;

        @Override
        public int get() {
            return group;
        }
    }

    public ProvideParser() {
        super(REGEX);
    }


    public String getType(String input) {
        return getGroup(input, Groups.TYPE);
    }

    public String getName(String input) {
        return getGroup(input, Groups.NAME);
    }

    public String getMainLine(String input) {
        return getGroup(input, Groups.STATEMENT);
    }

}
