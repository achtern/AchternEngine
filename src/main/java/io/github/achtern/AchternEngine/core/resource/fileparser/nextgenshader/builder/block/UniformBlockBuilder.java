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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.block;

import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.RequireManager;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UniformBlockBuilder {

    @Getter(AccessLevel.PROTECTED)
    protected RequireManager manager;

    public UniformBlockBuilder(RequireManager manager) {
        this.manager = manager;
    }


    /**
     * Calls {@link #get(String)} with the dilimter "\n"
     * @see #getLines()
     * @return Valid GLSL uniform block
     */
    public String get() {
        return get("\n");
    }

    /**
     * Calls {@link #getLines()} and implodes them with the given
     * delimiter
     * @see #getLines()
     * @param delimiter The string to implode the lines
     * @return Block of uniforms
     */
    public String get(String delimiter) {
        List<String> lines = getLines();
        StringBuilder builder = new StringBuilder(lines.size());

        for (String l : lines) {
            builder.append(l).append(delimiter);
        }

        return builder.toString();
    }

    /**
     * Generates valid GLSL lines from the stored {@link io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.RequireManager}
     * (the uniform stuff).
     * @return List of valid GLSL lines WITH ';' {@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser#TOKEN_END_STATEMENT}
     */
    public List<String> getLines() {
        final List<String> lines = new ArrayList<String>(getManager().getRequires().size());

        Iterator<Map.Entry<String, String>> it = getManager().getRequires().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> uniform = it.next();

            lines.add(
                    GLSLParser.TOKEN_UNIFORM
                  + GLSLParser.TOKEN_SPACE
                  + uniform.getValue()
                  + GLSLParser.TOKEN_SPACE
                  + uniform.getKey()
                  + GLSLParser.TOKEN_END_STATEMENT
            );

            it.remove();
        }

        return lines;
    }

}
