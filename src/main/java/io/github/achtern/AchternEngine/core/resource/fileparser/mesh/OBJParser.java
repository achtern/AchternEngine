package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.util.UString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Inheritance not possible due to incompatible Indices Types...
 */
public class OBJParser implements Model, LineBasedParser {

    public static final Logger LOGGER = LoggerFactory.getLogger(OBJParser.class);

    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> texCoord;
    private ArrayList<Vector3f> normal;
    private ArrayList<OBJIndex> indices;
    private boolean hasTexCoords;
    private boolean hasNormals;

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
     * @throws Exception
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

        if (tokens[0].startsWith("#")) {
            // Comment Line
            LOGGER.trace("Skipping commented line");
        } else if (tokens[0].equalsIgnoreCase("g")){
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
        } else if (tokens[0].equalsIgnoreCase("v")) {
            positions.add(new Vector3f(
                    Float.valueOf(tokens[1]),
                    Float.valueOf(tokens[2]),
                    Float.valueOf(tokens[3])
            ));
        } else if (tokens[0].equalsIgnoreCase("vt")){
            texCoord.add(new Vector2f(
                    Float.valueOf(tokens[1]),
                    Float.valueOf(tokens[2])
            ));
        } else if (tokens[0].equalsIgnoreCase("vn")){
            normal.add(new Vector3f(
                    Float.valueOf(tokens[1]),
                    Float.valueOf(tokens[2]),
                    Float.valueOf(tokens[3])
            ));
        } else if (tokens[0].equalsIgnoreCase("f")){

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

    protected OBJIndex parseOBJIndex(String token) {
        String[] values = token.split("/");

        OBJIndex result = new OBJIndex();
        result.setVertex(Integer.parseInt(values[0]) - 1);

        if (values.length > 1) {
            this.hasTexCoords = true;
            result.setTexCoord(Integer.parseInt(values[1]) - 1);
            if (values.length > 2) {
                this.hasNormals = true;
                result.setNormal(Integer.parseInt(values[2]) - 1);
            }
        }

        return result;
    }

    public ArrayList<Vector3f> getPositions() {
        return positions;
    }

    public void setPositions(ArrayList<Vector3f> positions) {
        this.positions = positions;
    }

    public ArrayList<Vector2f> getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(ArrayList<Vector2f> texCoord) {
        this.texCoord = texCoord;
    }

    public ArrayList<Vector3f> getNormal() {
        return normal;
    }

    public void setNormal(ArrayList<Vector3f> normal) {
        this.normal = normal;
    }

    public ArrayList<OBJIndex> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<OBJIndex> indices) {
        this.indices = indices;
    }

    public boolean hasRun() {
        return run;
    }
}
