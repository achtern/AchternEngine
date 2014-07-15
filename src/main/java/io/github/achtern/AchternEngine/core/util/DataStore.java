package io.github.achtern.AchternEngine.core.util;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Texture;

public interface DataStore {

    public void addTexture(String name, Texture texture);

    public Texture getTexture(String name);

    public boolean hasTexture(String name);

    public void addVector(String name, Vector3f vector);

    public Vector3f getVector(String name);

    public boolean hasVector(String name);

    public void addColor(String name, Color color);

    public Color getColor(String name);

    public boolean hasColor(String name);

    public void addFloat(String name, float f);

    public float getFloat(String name);

    public boolean hasFloat(String name);

    public void addInteger(String name, int i);

    public int getInteger(String name);

    public boolean hasInteger(String name);

    public void addMatrix(String name, Matrix4f matrix);

    public Matrix4f getMatrix(String name);

    public boolean hasMatrix(String name);
}
