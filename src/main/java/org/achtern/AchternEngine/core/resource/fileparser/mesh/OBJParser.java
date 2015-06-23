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

package org.achtern.AchternEngine.core.resource.fileparser.mesh;

import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import org.achtern.AchternEngine.core.resource.loader.LoadingException;
import org.achtern.AchternEngine.core.util.UString;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inheritance not possible due to incompatible Indices Types...
 */
public class OBJParser implements Model, LineBasedParser {

    public static final Logger LOGGER = LoggerFactory.getLogger(OBJParser.class);


    public static final String COMMENT = "#";
    public static final String VERTEX = "v";
    public static final String NORMAL = "vn";
    public static final String UV = "vt";
    public static final String FACE = "f";
    public static final String GROUP = "g";

    @Getter @Setter protected ArrayList<Vector3f> positions;
    @Getter @Setter protected ArrayList<Vector2f> texCoord;
    @Getter @Setter protected ArrayList<Vector3f> normal;
    @Getter @Setter protected ArrayList<OBJIndex> indices;
    protected boolean hasTexCoords;
    protected boolean hasNormals;

    protected boolean run = false;

    private static boolean no_mulit_object_warned = false;

    public OBJParser() {
        positions = new ArrayList<Vector3f>();
        texCoord = new ArrayList<Vector2f>();
        normal = new ArrayList<Vector3f>();
        indices = new ArrayList<OBJIndex>();
        hasTexCoords = false;
        hasNormals = false;
    }

    /**
     * Parses the line.
     * Should NOT add a trailing line break to the line.
     *
     * @param line The line to load
     * @return The parsed line
     * @throws Exception if parsing fails
     */
    @Override
    public String parse(String line) throws Exception {
        if (!run) {
            run = true;
        }

        String[] tokens = line.split(" ");
        tokens = UString.removeEmptyFromArray(tokens);

        if (tokens.length == 0) {
            // We found an empty line
            return line;
        }

        if (tokens[0].startsWith(COMMENT)) {
            // Comment Line
            LOGGER.trace("Skipping commented line");
        } else if (tokens[0].equalsIgnoreCase(GROUP)){
            // We have a multi object OBJ Model.
            // At this point in time the parser
            // doesn't support these files.
            // It loads the model, but all texture coordinates
            // are broken.
            if (!no_mulit_object_warned) {
                LOGGER.warn("Multi-Object OBJ files are not fully supported. " +
                        "Texture Coordinates may be broken. You can combine " +
                        "the objects in the 3D Editor of your choice.");
                no_mulit_object_warned = true;
            }
        } else if (tokens[0].equalsIgnoreCase(VERTEX)) {
            positions.add(new Vector3f(
                    Float.valueOf(tokens[1]),
                    Float.valueOf(tokens[2]),
                    Float.valueOf(tokens[3])
            ));
        } else if (tokens[0].equalsIgnoreCase(UV)){
            texCoord.add(new Vector2f(
                    Float.valueOf(tokens[1]),
                    1 - Float.valueOf(tokens[2])
            ));
        } else if (tokens[0].equalsIgnoreCase(NORMAL)){
            normal.add(new Vector3f(
                    Float.valueOf(tokens[1]),
                    Float.valueOf(tokens[2]),
                    Float.valueOf(tokens[3])
            ));
        } else if (tokens[0].equalsIgnoreCase(FACE)){

            for (int i = 0; i < tokens.length - 3; i++) {

                indices.add(this.parseOBJIndex(tokens[1]));
                indices.add(this.parseOBJIndex(tokens[2 + i]));
                indices.add(this.parseOBJIndex(tokens[3 + i]));
            }

        }

        return line;
    }

    @Override
    public IndexedModel toIndexedModel() {


        LOGGER.trace("Starting to convert OBJ to Indexed Model (incl. Optimization)");

        IndexedModel rMain = new IndexedModel();
        IndexedModel normalModel = new IndexedModel();
        HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
        HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

        for (int i = 0; i < getIndices().size(); i++) {
            OBJIndex index = getIndices().get(i);

            Vector3f curPos = getPositions().get(index.getVertex());
            Vector2f curTexCoord;
            Vector3f curNormal;

            if (hasTexCoords) {
                curTexCoord = getTexCoord().get(index.getTexCoord());
            } else {
                curTexCoord = new Vector2f(0, 0);
            }

            if (hasNormals) {
                curNormal = getNormal().get(index.getNormal());
            } else {
                curNormal = new Vector3f(0, 0, 0);
            }

            Integer modelIndex = resultIndexMap.get(index);

            if (modelIndex == null) {

                modelIndex = rMain.getPositions().size();
                resultIndexMap.put(index, modelIndex);

                rMain.getPositions().add(curPos);
                rMain.getTexCoord().add(curTexCoord);

                if (hasNormals) {
                    rMain.getNormal().add(curNormal);
                }


            }

            Integer normalModelIndex = normalIndexMap.get(index.getVertex());

            if (normalModelIndex == null) {
                normalModelIndex = normalModel.getPositions().size();
                normalIndexMap.put(index.getVertex(), normalModelIndex);

                normalModel.getPositions().add(curPos);
                normalModel.getTexCoord().add(curTexCoord);
                normalModel.getNormal().add(curNormal);
            }

            rMain.getIndices().add(modelIndex);
            normalModel.getIndices().add(normalModelIndex);
            indexMap.put(modelIndex, normalModelIndex);

        }

        if (!hasNormals) {
            normalModel.calcNormals();


            for (int i = 0; i < rMain.getPositions().size(); i++) {
                rMain.getNormal().add(normalModel.getNormal().get(indexMap.get(i)));
            }
        }

        LOGGER.trace("Done to convert OBJ to Indexed Model (incl. Optimization)");

        return rMain;
    }

    /**
     * An OBJIndex contains indicies to various types of data.
     *
     * - vertex/tex_coord/normal
     * @param token single input token
     * @return parsed OBJIndex
     * @throws org.achtern.AchternEngine.core.resource.loader.LoadingException if integer parsing fails
     */
    protected OBJIndex parseOBJIndex(String token) throws LoadingException {
        String[] values = token.split("/");

        OBJIndex result = new OBJIndex();

        try {
            result.setVertex(Integer.parseInt(values[0]) - 1);

            if (values.length > 1 && !values[1].isEmpty()) {
                this.hasTexCoords = true;
                result.setTexCoord(Integer.parseInt(values[1]) - 1);
            }
            if (values.length > 2) {
                this.hasNormals = true;
                result.setNormal(Integer.parseInt(values[2]) - 1);
            }
        } catch (NumberFormatException e) {
            throw new LoadingException("Failed to parse integer in OBJ f index, token<" + token + ">");
        }

        return result;
    }

    public boolean hasRun() {
        return run;
    }
}
