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

package org.achtern.AchternEngine.core.resource.loader;

import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Vertex;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import org.achtern.AchternEngine.core.resource.fileparser.mesh.IndexedModel;
import org.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser;
import org.achtern.AchternEngine.core.util.UInteger;

import java.util.ArrayList;

/**
 * Loads a {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh} from
 * OBJ Files.
 * @see org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader
 */
public class MeshLoader extends AsciiFileLoader<Mesh> {

    /**
     * OBJParser used to read in the OBJ File
     * and convert it into a {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh}
     */
    protected OBJParser objParser;

    /**
     * Constructs a new MeshLoader and the
     * {@link org.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser}
     */
    public MeshLoader() {
        this.objParser = new OBJParser();
    }

    /**
     * If the {@link org.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser}
     * has not been called as {@link org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
     * during loading (because of caching). The file gets split by line breaks and the Parser runs over the array.
     * This performs any type of loading and parsing.
     * This should load the resource, but should not constructed it,
     * just loading/parsing and preparations to create the object
     * @param name The name of the original file
     * @param file The input file
     * @throws LoadingException when the loading fails
     */
    @Override
    public void load(String name, String file) throws LoadingException {
        if (objParser.hasRun()) return;

        String[] lines = file.split("\n");
        for (String l : lines) {
            try {
                getPreProcessor().parse(l);
            } catch (Exception e) {
                throw new LoadingException("Could not load OBJ File", e);
            }
        }

    }

    /**
     * The preprocessor is used during reading.
     * There is no guarantee that this {@link org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
     * will get called. (When reading from cache for example, no PreProcessor is getting called).
     * @return LineBasedParser
     */
    @Override
    public LineBasedParser getPreProcessor() {
        return objParser;
    }

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * @return The new object
     * @throws Exception on parsing and processing errors
     */
    @Override
    public Mesh get() throws Exception {

        IndexedModel model = objParser.toIndexedModel();
        model.calcTangents();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        for (int i = 0; i < model.getPositions().size(); i++) {
            Vector3f position = model.getPositions().get(i);
            Vector2f texCoord = model.getTexCoord().get(i);
            Vector3f normal = model.getNormal().get(i);
            Vector3f tangent = model.getTangent().get(i);

            vertices.add(new Vertex(position, texCoord, normal, tangent));
        }

        Vertex[] vData = new Vertex[vertices.size()];
        vertices.toArray(vData);

        Integer[] iData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(iData);

        return new Mesh(vData, UInteger.toIntArray(iData));

    }
}
