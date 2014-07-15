package io.github.achtern.AchternEngine.core.util;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Texture;

import java.util.HashMap;
import java.util.Map;

public class CommonDataStore implements DataStore {

    protected Map<String, Texture> textures = new HashMap<String, Texture>();
    protected Map<String, Vector3f> vectors = new HashMap<String, Vector3f>();
    protected Map<String, Color> colors = new HashMap<String, Color>();
    protected Map<String, Float> floats = new HashMap<String, Float>();
    protected Map<String, Integer> integers = new HashMap<String, Integer>();
    protected Map<String, Matrix4f> matrices = new HashMap<String, Matrix4f>();


    @Override
    public void addTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    @Override
    public Texture getTexture(String name) {
        return textures.get(name);
    }

    @Override
    public boolean hasTexture(String name) {
        return textures.containsKey(name);
    }

    @Override
    public void addVector(String name, Vector3f vector) {
        vectors.put(name, vector);
    }

    @Override
    public Vector3f getVector(String name) {
        Vector3f r = vectors.get(name);
        if (r != null) {
            return r;
        }

        return new Vector3f(0, 0, 0);
    }

    @Override
    public boolean hasVector(String name) {
        return vectors.containsKey(name);
    }

    @Override
    public void addColor(String name, Color color) {
        colors.put(name, color);
    }

    @Override
    public Color getColor(String name) {
        return colors.get(name);
    }

    @Override
    public boolean hasColor(String name) {
        return colors.containsKey(name);
    }

    @Override
    public void addInteger(String name, int i) {
        integers.put(name, i);
    }

    @Override
    public int getInteger(String name) {
        Integer r = integers.get(name);
        if (r != null) {
            return r;
        }

        return 0;
    }

    @Override
    public boolean hasInteger(String name) {
        return integers.containsKey(name);
    }

    @Override
    public void addFloat(String name, float f) {
        floats.put(name, f);
    }

    @Override
    public float getFloat(String name) {
        Float r = floats.get(name);
        if (r != null) {
            return r;
        }

        return 0;
    }

    @Override
    public boolean hasFloat(String name) {
        return floats.containsKey(name);
    }

    @Override
    public void addMatrix(String name, Matrix4f matrix) {
        matrices.put(name, matrix);
    }

    @Override
    public Matrix4f getMatrix(String name) {
        return matrices.get(name);
    }

    @Override
    public boolean hasMatrix(String name) {
        return matrices.containsKey(name);
    }

}
