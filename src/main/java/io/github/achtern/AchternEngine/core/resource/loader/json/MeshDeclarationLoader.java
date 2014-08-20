package io.github.achtern.AchternEngine.core.resource.loader.json;

import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.json.JSONArray;
import org.json.JSONObject;

public class MeshDeclarationLoader extends JsonLoader<Mesh> {
    @Override
    public Mesh get() throws Exception {
        JSONObject json = getJsonObject();

        if (json.has("@")) {
            return handleClass(json);
        } else if (json.has("file")) {
            return ResourceLoader.getMesh(json.getString("file"));
        } else {
            throw new UnsupportedOperationException("Mesh declaration has to contain either" +
                    " '@' or 'file' key!");
        }

    }


    protected Mesh handleClass(JSONObject json) throws Exception {
        String className = json.getString("@");

        if (!className.contains(".")) {
            // If we have just a name. Search in the default mesh package
            className = "io.github.achtern.AchternEngine.core.rendering.mesh." + className;
        }

        if (json.has("args")) {
            JSONArray args = json.getJSONArray("args");
            return (Mesh) getObject(className, toObjectArray(args));
        } else {
            return (Mesh) getObject(className, null);
        }
    }
}
