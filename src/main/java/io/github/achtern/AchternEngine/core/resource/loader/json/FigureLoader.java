package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.json.JSONObject;

public class FigureLoader extends JsonLoader<Figure> {

    @Override
    public Figure get() throws Exception {
        final Figure figure;

        JSONObject json = getJsonObject();

//        Name
        String name = json.getString("name");

        figure = new Figure(name);

//        Mesh
        JSONObject mesh = json.getJSONObject("Mesh");
        MeshLoader meshLoader = new MeshLoader();
        meshLoader.setJsonObject(mesh);

        figure.setMesh(meshLoader.get());

//        Material
        JSONObject material = json.getJSONObject("Material");
        MaterialLoader materialLoader = new MaterialLoader();
        materialLoader.setJsonObject(material);

        figure.setMaterial(materialLoader.get());

//        DrawStrategy
        JSONObject drawStrategy = json.getJSONObject("DrawStrategy");
        DrawStrategyLoader drawStrategyLoader = new DrawStrategyLoader();
        drawStrategyLoader.setJsonObject(drawStrategy);

        figure.setMaterial(materialLoader.get());



        return figure;
    }
}
