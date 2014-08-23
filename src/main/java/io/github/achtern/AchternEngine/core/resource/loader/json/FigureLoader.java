package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.json.JSONObject;

/**
 * A {@link FigureLoader} loads the contents of a JSON
 * Figure declaration.
 * The JSON file has to contain at least a 'name' key.
 * A {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Figure}
 * contains a {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh},
 *  {@link io.github.achtern.AchternEngine.core.rendering.Material} and
 * optional {@link io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy}.
 *
 * The keys in the root node of the json file, should therefor be
 * "name", "Mesh", "Material", "DrawStrategy".
 * All are optional, but the name!
 * The others are delegated to the specific loaders:
 * {@link io.github.achtern.AchternEngine.core.resource.loader.json.MeshDeclarationLoader},
 * {@link io.github.achtern.AchternEngine.core.resource.loader.json.MaterialLoader},
 * {@link io.github.achtern.AchternEngine.core.resource.loader.json.DrawStrategyLoader}
 *
 *
 */
public class FigureLoader extends JsonLoader<Figure> {

    /**
     * This should used the information, generated during
     * loading and construct an Object.
     * This reads the {@link org.json.JSONObject} and constructs the Figure.
     * @return The new object
     * @throws Exception
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
