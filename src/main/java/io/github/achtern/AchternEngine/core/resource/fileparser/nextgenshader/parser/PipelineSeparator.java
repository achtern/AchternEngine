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
import io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.statement.BlockExtractor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PipelineSeparator {

    public static final Logger LOGGER = LoggerFactory.getLogger(PipelineSeparator.class);

    public static final String PIPELINE_SEPARATOR_START = "#---";

    public static final String PIPELINE_SEPARATOR_END = "---#";

    public static final String PIPELINE_END = PIPELINE_SEPARATOR_START + "END" + PIPELINE_SEPARATOR_END;

    @Getter protected final String source;

    public PipelineSeparator(String source) {
        this.source = source;
    }

    public List<String> getGlobals() {
        final String globals = getSource().substring(0, getSource().indexOf(PIPELINE_SEPARATOR_START));

        final String[] lines = globals.split("\n");

        final List<String> globalStatements = new ArrayList<String>(lines.length);

        for (String l : lines) {
            globalStatements.add(l.trim());
        }

        return globalStatements;
    }

    protected Map<String, String> getBlocks() throws ParsingException {
        final String removedGlobals = getSource().substring(getSource().indexOf("#---"), getSource().length());

        final String[] blocks = removedGlobals.split(PIPELINE_END);

        final Map<String, String> processedBlocks = new HashMap<String, String>(blocks.length);

        BlockExtractor extractor;
        for (String block : blocks) {
            extractor = new BlockExtractor(block);

            if (extractor.getTargetMatcher().matches()) {
                String type = extractor.getType().trim();
                String content = extractor.getContent().trim();

                processedBlocks.put(type, content);
            } else {
                throw new ParsingException("Block doesn't match regex.\n>>" + block + "<<\n");
            }
        }


        return processedBlocks;
    }
}
