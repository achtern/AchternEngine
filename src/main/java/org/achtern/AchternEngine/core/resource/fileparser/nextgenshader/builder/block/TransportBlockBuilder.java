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

import org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.VariableTransportManager;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class TransportBlockBuilder {

    @Getter(AccessLevel.PROTECTED)
    protected final VariableTransportManager manager;

    protected TransportBlockBuilder(VariableTransportManager manager) {
        this.manager = manager;
    }

    /**
     * Calls {@link #get(String)} with the dilimter "\n"
     * @see #getLines()
     * @return Valid GLSL block
     */
    public String get() {
        return get("\n");
    }

    /**
     * Calls {@link #getLines()} and implodes them with the given
     * delimiter
     * @see #getLines()
     * @param delimiter The string to implode the lines
     * @return Block of transport info
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
     * Generates valid GLSL lines from the stored {@link org.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager.VariableTransportManager}.
     * @return List of valid GLSL lines WITH ';' {@link org.achtern.AchternEngine.core.resource.fileparser.GLSLParser#TOKEN_END_STATEMENT}
     */
    public List<String> getLines() {
        final List<String> lines = new ArrayList<String>(getManager().getTransports().size());

        Iterator<Map.Entry<String, String>> it = getManager().getTransports().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, String> transport = it.next();

            lines.add(buildLine(transport.getValue(), transport.getKey()));

            it.remove();
        }

        return lines;
    }

    protected abstract String buildLine(String type, String name);

}
