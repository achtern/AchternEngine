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

package org.achtern.AchternEngine.core.resource.fileparser;

import org.achtern.AchternEngine.core.scenegraph.entity.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .figure is a custom extension used by AchternEngine.
 * Developmented halted currently.
 */
@Deprecated
public class FigureParser {

    public static final String BLOCK_OPEN = "{";

    public static final String BLOCK_CLOSE = "}";

    public static final String MESH_BLOCK = "Mesh";

    public static final String MATERIAL_BLOCK = "Material";

    public static final String TEXTURE_BLOCK = "Texture";

    public static final String FLOAT_BLOCK = "float";

    public static final String FILE_STATEMENT = "file";

    public static final String DRAW_STRATEGY_BLOCK = "DrawStrategy";

    public static final String MATERIAL_WIREFRAME_DRAW_KEY = "wireframe";

    public static final String TEXTURE_ALPHA_STATEMENT = "alpha";

    public static final String PACKAGE_SEARCH_INDICATOR = "@";

    public static final String ASSIGNMENT_CHARACTER = "=";



    public static final String TOKEN_INDICATOR = "$";

    public static final String TOKEN_CUSTOM_INDICATOR = "&";

    public static final String TOKEN_BLOCK_START = TOKEN_INDICATOR + "BLOCK_START(%s)";

    public static final String TOKEN_BLOCK_END = TOKEN_INDICATOR + "BLOCK_END(%s)";

    public static final String TOKEN_PACKAGE_SEARCH = TOKEN_INDICATOR + "PACKAGE_SEARCH_FOR(%s)";

    public static final String TOKEN_MATERIAL_WIREFRAME = TOKEN_INDICATOR + "WIREFRAME(%s)";

    public static final String TOKEN_TEXTURE_ALPHA = TOKEN_INDICATOR + "ALPHA(%s)";

    public static final String TOKEN_FILE = TOKEN_INDICATOR + "FILE(%s)";

    public static final String TOKEN_VALUE_ASSIGNMENT = TOKEN_CUSTOM_INDICATOR + "%s(%s)";


    public static final Pattern PATTERN_FIRST_BLOCK = Pattern.compile("\\$BLOCK_START\\(&([A-Za-z0-9]*)\\)");


    protected String source;
    protected List<String> tokens;


    protected Figure get() throws ParsingException {
        final Figure figure;

        String name = tokens.get(0);
        Matcher nameMater = PATTERN_FIRST_BLOCK.matcher(name);
        if (!nameMater.find()) {
            throw new ParsingException("Could not get Figure name from token stream");
        }

        figure = new Figure(nameMater.group());



        return figure;
    }


    protected void tokenize() throws ParsingException {

        String[] lines = source.split("\n");
        tokens = new ArrayList<String>(lines.length);

        int blockLevel = 0;
        String previousBlock = "";

        for (String l : lines) {
            l = l.trim();
            if (l.isEmpty()) continue;

            if (l.contains(BLOCK_OPEN)) {

                String name = l.substring(0, l.indexOf(BLOCK_OPEN)).trim();

                String token = (buildInBlock(name)) ? TOKEN_INDICATOR : TOKEN_CUSTOM_INDICATOR;



                tokens.add(String.format(TOKEN_BLOCK_START, previousBlock + token + name));

                previousBlock += token + name;

                blockLevel++;

            } else if (l.contains(BLOCK_CLOSE)) {
                if (blockLevel == 0) {
                    // reached end of file?
                    // TODO: check
                    System.out.println("Reached end statements");
                } else {
                    tokens.add(String.format(TOKEN_BLOCK_END, previousBlock));

                    // previousBlock = "$BLOCK1%block$BLOCK3"
                    // => "$BLOCK1$BLOCK2"
                    int tI = previousBlock.lastIndexOf(TOKEN_INDICATOR);
                    int cI = previousBlock.lastIndexOf(TOKEN_CUSTOM_INDICATOR);
                    if (tI == -1 && cI == -1 || tI == 0 && cI == -1 || tI == -1 && cI == 0) {
                        previousBlock = "";
                    } else {
                        previousBlock = previousBlock.substring(0, (tI > cI) ? tI : cI);
                    }

                    blockLevel--;

                }
            } else {
                // We are inside a block. verify it
                if (blockLevel == 0) {
                    throw new ParsingException("Block Level cannot be null");
                } else if (blockLevel == 2) {

                    if (previousBlock.endsWith(TOKEN_INDICATOR + MESH_BLOCK)) {
                        // load Mesh Block
                        // supports only Build in classes atm.
                        // TODO: allow file loading
                        if (l.startsWith(PACKAGE_SEARCH_INDICATOR)) {
                            String clazz = l.substring(1);
                            tokens.add(String.format(TOKEN_PACKAGE_SEARCH, clazz));
                        }
                    } else if (previousBlock.endsWith(TOKEN_INDICATOR + MATERIAL_BLOCK)) {

                        if (l.startsWith(MATERIAL_WIREFRAME_DRAW_KEY)) {
                            String[] parts = l.split(ASSIGNMENT_CHARACTER);
                            String name = parts[0].trim();
                            assert name.equalsIgnoreCase("MATERIAL_WIREFRAME_DRAW_KEY");

                            String value = parts[1].trim();

                            tokens.add(String.format(TOKEN_MATERIAL_WIREFRAME, value));
                        }


                    }  else if (previousBlock.endsWith(TOKEN_INDICATOR + DRAW_STRATEGY_BLOCK)) {
                        if (l.startsWith(PACKAGE_SEARCH_INDICATOR)) {
                            String clazz = l.substring(1);
                            tokens.add(String.format(TOKEN_PACKAGE_SEARCH, clazz));
                        }
                    }
                } else if (blockLevel == 3) {
                    if (previousBlock.endsWith(TOKEN_INDICATOR + MATERIAL_BLOCK + TOKEN_INDICATOR + FLOAT_BLOCK)) {
                        if (!l.contains(ASSIGNMENT_CHARACTER)) {
                            throw new ParsingException("In float Block assignments are mandatory");
                        }
                        String[] parts = l.split(ASSIGNMENT_CHARACTER);
                        String name = parts[0].trim();
                        String value = parts[1].trim();

                        tokens.add(String.format(TOKEN_VALUE_ASSIGNMENT, name, value));
                    }
                } else if (blockLevel == 4) {
                    if (previousBlock.matches(
                            ".*\\" +
                                    TOKEN_INDICATOR +
                                    MATERIAL_BLOCK +
                                    "\\" +
                                    TOKEN_INDICATOR +
                                    TEXTURE_BLOCK +
                                    TOKEN_CUSTOM_INDICATOR +
                                    "([a-z]*)")) {

                        // load custom block
                        if (!l.contains(ASSIGNMENT_CHARACTER)) {
                            throw new ParsingException("In sub Texture Block assignments are mandatory");
                        }
                        String[] parts = l.split(ASSIGNMENT_CHARACTER);
                        String name = parts[0].trim();
                        String value = parts[1].trim();

                        if (name.equalsIgnoreCase(FILE_STATEMENT)) {
                            tokens.add(String.format(TOKEN_FILE, value));
                        } else if (name.equalsIgnoreCase(TEXTURE_ALPHA_STATEMENT)) {
                            tokens.add(String.format(TOKEN_TEXTURE_ALPHA, value));
                        } else {
                            throw new ParsingException("Unexpected variable type " + name);
                        }
                    }
                }
            }
            previousBlock = previousBlock.trim();

        }

    }

    private static boolean buildInBlock(String name) {
        return name.equalsIgnoreCase(MESH_BLOCK) || name.equalsIgnoreCase(TEXTURE_BLOCK) ||
                name.equalsIgnoreCase(MATERIAL_BLOCK) || name.equalsIgnoreCase(FLOAT_BLOCK) ||
                name.equalsIgnoreCase(DRAW_STRATEGY_BLOCK);
    }


}