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

package org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.block;

import org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.RequireManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniformBlockBuilderTest {

    public RequireManager m;
    public UniformBlockBuilder builder;
    public List<String> correctLines;

    @Before
    public void before() {
        m = new RequireManager();
        m.add("vec2", "foo1");
        m.add("vec2", "foo2");
        m.add("vec2", "foo3");
        m.add("vec3", "foo4");

        builder = new UniformBlockBuilder(m);

        correctLines = new ArrayList<String>(4);

        correctLines.add("uniform vec2 foo1;");
        correctLines.add("uniform vec2 foo2;");
        correctLines.add("uniform vec2 foo3;");
        correctLines.add("uniform vec3 foo4;");

        Collections.reverse(correctLines);
    }

    @Test
    public void testGet() throws Exception {
        StringBuilder block = new StringBuilder();

        for (String l : correctLines) {
            block.append(l).append("\n");
        }

        assertEquals("Should use the line break delimiter by default", block.toString(), builder.get());
    }

    @Test
    public void testGetWithArgs() throws Exception {
        StringBuilder block = new StringBuilder();

        String del = "-";

        for (String l : correctLines) {
            block.append(l).append(del);
        }

        assertEquals("Should use the correct delimiter", block.toString(), builder.get(del));
    }

    @Test
    public void testGetLines() throws Exception {
        List<String> lines = builder.getLines();
        assertEquals("Generates correct amount of lines", correctLines.size(), lines.size());
        assertEquals("Generates valid GLSL lines", correctLines, lines);

    }
}