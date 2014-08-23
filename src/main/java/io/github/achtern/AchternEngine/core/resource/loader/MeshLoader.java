package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.IndexedModel;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser;
import io.github.achtern.AchternEngine.core.util.UInteger;

import java.util.ArrayList;

/**
 * Loads a {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh} from
 * OBJ Files.
 * @see io.github.achtern.AchternEngine.core.resource.loader.AsciiFileLoader
 */
public class MeshLoader extends AsciiFileLoader<Mesh> {

    /**
     * OBJParser used to read in the OBJ File
     * and convert it into a {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh}
     */
    protected OBJParser objParser;

    /**
     * Constructs a new MeshLoader and the
     * {@link io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser}
     */
    public MeshLoader() {
        this.objParser = new OBJParser();
    }

    /**
     * If the {@link io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser}
     * has not been called as {@link io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
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
     * There is no guarantee that this {@link io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
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
     * @throws Exception
     */
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
