package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.json.JSONObject;

/**
 * A Figure Loader loads the contents of a JSON
 * Figure declaration.
 * The JSON file has to contain at least a 'name' key.
 */
public class FigureLoader extends JsonLoader<Figure> {

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
