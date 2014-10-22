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

import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PipelineSeparator {

    public static final Logger LOGGER = LoggerFactory.getLogger(PipelineSeparator.class);

    /**
     * Capture Groups: (example: "#---VERTEX---#..stuff...")
     * - 0 everything (example: "#---VERTEX---#..stuff...")
     * - 1 garbage (example: null)
     * - 2 type (example: "VERTEX")
     * - 3 content (example: "..stuff...")
     */
    public static final Pattern BLOCK_EXTRAXTOR = Pattern.compile("(.|\\n)*#---(.*)---#((.|\\n)*)");

    @Getter protected final String source;

    public PipelineSeparator(String source) {
        this.source = source;
    }

    public List<String> getGlobals() {
        // TODO: move this value into a final, constant value!
        String globals = getSource().substring(0, getSource().indexOf("#---"));

        String[] lines = globals.split("\n");

        final List<String> globalStatements = new ArrayList<String>(lines.length);

        for (String l : lines) {
            globalStatements.add(l.trim());
        }

        return globalStatements;
    }

    protected Map<String, String> getBlocks() throws ParsingException {
        String removedGlobals = getSource().substring(getSource().indexOf("#---"), getSource().length());

        String[] blocks = removedGlobals.split("#---END---#");

        final Map<String, String> processedBlocks = new HashMap<String, String>(blocks.length);

        Matcher matcher;
        for (String block : blocks) {
            matcher = BLOCK_EXTRAXTOR.matcher(block);

            if (matcher.matches()) {
                String type = matcher.group(2);
                String content = matcher.group(3).trim();

                processedBlocks.put(type, content);
            } else {
                throw new ParsingException("Block doesn't match regex.\n>>" + block + "<<\n");
            }
        }


        return processedBlocks;
    }
}
