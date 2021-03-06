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

package org.achtern.AchternEngine.core.resource.loader.json;

import org.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.json.JSONObject;

/**
 * A {@link FigureLoader} loads the contents of a JSON
 * Figure declaration.
 * The JSON file has to contain at least a 'name' key.
 * A {@link org.achtern.AchternEngine.core.scenegraph.entity.Figure}
 * contains a {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh},
 *  {@link org.achtern.AchternEngine.core.rendering.Material} and
 * optional {@link org.achtern.AchternEngine.core.rendering.drawing.DrawStrategy}.
 *
 * The keys in the root node of the json file, should therefor be
 * "name", "Mesh", "Material", "DrawStrategy".
 * All are optional, but the name!
 * The others are delegated to the specific loaders:
 * {@link org.achtern.AchternEngine.core.resource.loader.json.MeshDeclarationLoader},
 * {@link org.achtern.AchternEngine.core.resource.loader.json.MaterialLoader},
 * {@link org.achtern.AchternEngine.core.resource.loader.json.DrawStrategyLoader}
 *
 *
 */
public class FigureLoader extends JsonLoader<Figure> {

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * This reads the {@link org.json.JSONObject} and constructs the Figure.
     * @return The new object
     * @throws Exception on json formatting or custom format spec violations
     */
    @Override
    public Figure get() throws Exception {
        final Figure figure;

        JSONObject json = getJsonObject();

//        Name
        String name = json.getString("name");

        figure = new Figure(name);

        if (json.has("Mesh")) {
//        Mesh
            JSONObject mesh = json.getJSONObject("Mesh");
            MeshDeclarationLoader meshDeclarationLoader = new MeshDeclarationLoader();
            meshDeclarationLoader.setJsonObject(mesh);

            figure.setMesh(meshDeclarationLoader.get());
        }

        if (json.has("Material")) {
//        Material
            JSONObject material = json.getJSONObject("Material");
            MaterialLoader materialLoader = new MaterialLoader();
            materialLoader.setJsonObject(material);

            figure.setMaterial(materialLoader.get());
        }

        if (json.has("DrawStrategy")) {
//        DrawStrategy
            JSONObject drawStrategy = json.getJSONObject("DrawStrategy");
            DrawStrategyLoader drawStrategyLoader = new DrawStrategyLoader();
            drawStrategyLoader.setJsonObject(drawStrategy);

            figure.setDrawStrategy(drawStrategyLoader.get());
        }



        return figure;
    }
}
