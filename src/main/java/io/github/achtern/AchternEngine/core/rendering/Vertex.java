package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;

import java.util.ArrayList;

public class Vertex {

    public static final int SIZE = 8;

    private Vector3f pos;
    private Vector2f texCor;
    private Vector3f normal;

    public static Vertex[] toArray(float[] positions) {
        ArrayList<Vertex> r = new ArrayList<Vertex>(positions.length / 3);

        for (int i = 0; i < positions.length; i += 3) {
            r.add(new Vertex(new Vector3f(positions[i], positions[i + 1], positions[i + 2])));
        }


        return r.toArray(new Vertex[r.size()]);

    }

    public static Vertex[] toArray(float[] positions, float[] texCoords) {
        Vertex[] r = toArray(positions);


        for (int i = 0; i < texCoords.length; i += 2) {
            r[i].setTexCor(new Vector2f(texCoords[i], texCoords[i + 1]));
        }

        return r;

    }

    public static Vertex[] toArray(float[] positions, float[] texCoords, float[] normals) {
        Vertex[] r = toArray(positions, texCoords);


        for (int i = 0; i < normals.length; i += 3) {
            r[i].setNormal(new Vector3f(texCoords[i], texCoords[i + 1], texCoords[i + 2]));
        }

        return r;

    }

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCor) {
        this(pos, texCor, new Vector3f(0, 0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texCor, Vector3f normal) {
        this.pos = pos;
        this.texCor = texCor;
        this.normal = normal;
    }

    public Vertex(float x, float y, float z, float texX, float texY) {
        this(new Vector3f(x, y, z), new Vector2f(texX, texY));
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTexCor() {
        return texCor;
    }

    public void setTexCor(Vector2f texCor) {
        this.texCor = texCor;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}