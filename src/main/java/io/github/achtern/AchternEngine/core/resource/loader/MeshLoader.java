package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.IndexedModel;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser;
import io.github.achtern.AchternEngine.core.util.UInteger;

import java.util.ArrayList;

public class MeshLoader extends Loader<Mesh> {

    protected OBJParser objParser;

    public MeshLoader(OBJParser objParser) {
        this.objParser = objParser;
    }

    public MeshLoader() {
        this.objParser = new OBJParser();
    }

    @Override
    public void parse(String name, String file) throws ParsingException {

    }

    @Override
    public LineBasedParser getPreProcessor() {
        return objParser;
    }

    @Override
    public Mesh get() throws Exception {

        IndexedModel model = objParser.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        for (int i = 0; i < model.getPositions().size(); i++) {
            Vector3f position = model.getPositions().get(i);
            Vector2f texCoord = model.getTexCoord().get(i);
            Vector3f normal = model.getNormal().get(i);

            vertices.add(new Vertex(position, texCoord, normal));
        }

        Vertex[] vData = new Vertex[vertices.size()];
        vertices.toArray(vData);

        Integer[] iData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(iData);

        return new Mesh(vData, UInteger.toIntArray(iData));

    }
}
