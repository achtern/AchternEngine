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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.parser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PipelineSeparatorTest {

    public PipelineSeparator ps;

    @Before
    public void before() throws Exception {
        ps = new PipelineSeparator(getSource());
    }

    @Test
    public void testGetGlobals() throws Exception {
        List<String> globals = new ArrayList<String>(2);
        globals.add("@import fog.slib;");
        globals.add("@import fuckit.slib;");

        assertEquals("Should retrieve all global statements", globals.size(), ps.getGlobals().size());
        assertEquals("Should retrieve correct, trimmed global statements", globals, ps.getGlobals());
    }

    @Test
    public void testGetBlocks() throws Exception {
        Map<String, String> blocks = new HashMap<String, String>(2);
        blocks.put("VERTEX", getVertexContent().trim());
        blocks.put("FRAGMENT", getFragmentContent().trim());

        assertEquals("Should extract the correct number of blocks", blocks.size(), ps.getBlocks().size());
        assertEquals("Should extract the correct blocks", blocks, ps.getBlocks());
    }

    public static String getSource() {
        return "@import fog.slib;\n" +
                "@import fuckit.slib;\n" +
                "\n" +
                "#---VERTEX---#\n" +
                "layout (location = 0) in vec3 inPosition;\n" +
                "layout (location = 1) in vec2 inTexCoord;\n" +
                "layout (location = 2) in vec3 inNormal;\n" +
                "\n" +
                "\n" +
                "@require mat4 model;\n" +
                "@require mat4 modelView;\n" +
                "@require mat4 MVP;\n" +
                "@require mat4 shadowMatrix;\n" +
                "\n" +
                "void main ()\n" +
                "{\n" +
                "  gl_Position = MVP * vec4(inPosition, 1.0);\n" +
                "\n" +
                "  @provide vec2 texCoord = inTexCoord;\n" +
                "  @provide vec3 normal = (model * vec4(inNormal, 0.0)).xyz;\n" +
                "  @provide vec3 worldPos = (model * vec4(inPosition, 1.0)).xyz;\n" +
                "  @provide vec4 shadowMapCoord = shadowMatrix * vec4(inPosition, 1.0);\n" +
                "\n" +
                "  @yield;\n" +
                "}\n" +
                "\n" +
                "#---END---#\n" +
                "#---FRAGMENT---#\n" +
                "@request vec2 texCoord;\n" +
                "\n" +
                "@require vec4 color;\n" +
                "@require sampler2D diffuse;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    vec4 out = color * texture(diffuse, texCoord.xy);\n" +
                "\n" +
                "    @yield out;\n" +
                "\n" +
                "    @write(0) out;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "#---END---#";
    }

    public static String getVertexContent() {
        return "layout (location = 0) in vec3 inPosition;\n" +
                "layout (location = 1) in vec2 inTexCoord;\n" +
                "layout (location = 2) in vec3 inNormal;\n" +
                "\n" +
                "\n" +
                "@require mat4 model;\n" +
                "@require mat4 modelView;\n" +
                "@require mat4 MVP;\n" +
                "@require mat4 shadowMatrix;\n" +
                "\n" +
                "void main ()\n" +
                "{\n" +
                "  gl_Position = MVP * vec4(inPosition, 1.0);\n" +
                "\n" +
                "  @provide vec2 texCoord = inTexCoord;\n" +
                "  @provide vec3 normal = (model * vec4(inNormal, 0.0)).xyz;\n" +
                "  @provide vec3 worldPos = (model * vec4(inPosition, 1.0)).xyz;\n" +
                "  @provide vec4 shadowMapCoord = shadowMatrix * vec4(inPosition, 1.0);\n" +
                "\n" +
                "  @yield;\n" +
                "}\n" +
                "\n";
    }

    public static String getFragmentContent() {
        return "@request vec2 texCoord;\n" +
                "\n" +
                "@require vec4 color;\n" +
                "@require sampler2D diffuse;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    vec4 out = color * texture(diffuse, texCoord.xy);\n" +
                "\n" +
                "    @yield out;\n" +
                "\n" +
                "    @write(0) out;\n" +
                "}\n" +
                "\n" +
                "\n";
    }
}