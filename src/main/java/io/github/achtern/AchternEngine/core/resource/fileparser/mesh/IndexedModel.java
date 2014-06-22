package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;

import java.util.ArrayList;

public class IndexedModel {

    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> texCoord;
    private ArrayList<Vector3f> normal;
    private ArrayList<Integer> indices;


    public IndexedModel() {
        positions = new ArrayList<Vector3f>();
        texCoord = new ArrayList<Vector2f>();
        normal = new ArrayList<Vector3f>();
        indices = new ArrayList<Integer>();
    }

    public void calcNormals() {

        for (int i = 0; i < getIndices().size(); i += 3) {
            int i0 = getIndices().get(i);
            int i1 = getIndices().get(i + 1);
            int i2 = getIndices().get(i + 2);

            Vector3f v1 = getPositions().get(i1).sub(getPositions().get(i0));
            Vector3f v2 = getPositions().get(i2).sub(getPositions().get(i0));

            Vector3f normal = v1.cross(v2).normalized();

            getNormal().get(i0).set(getNormal().get(i0).add(normal));
            getNormal().get(i1).set(getNormal().get(i1).add(normal));
            getNormal().get(i2).set(getNormal().get(i2).add(normal));
        }

        for (Vector3f normal : getNormal()) {
            normal.normalize();
        }

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

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }
}
