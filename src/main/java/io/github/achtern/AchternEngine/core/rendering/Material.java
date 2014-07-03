package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

public class Material {

    public static final Logger LOGGER = LoggerFactory.getLogger(Material.class);

    private HashMap<String, Texture> textures;
    private HashMap<String, Vector3f> vectors;
    private HashMap<String, Color> colors;
    private HashMap<String, Float> floats;

    private boolean wireframe = false;

    public Material() {
        textures = new HashMap<String, Texture>();
        vectors = new HashMap<String, Vector3f>();
        colors = new HashMap<String, Color>();
        floats = new HashMap<String, Float>();
    }


    public void addTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    public Texture getTexture(String name) {
        Texture r = textures.get(name);
        if (r != null) {
            return r;
        }

        try {
            return ResourceLoader.getTexture("missing.jpg");
        } catch (IOException e) {
            // WILL NEVER HAPPEN... But log it and return null.
            LOGGER.error("BREAK IN THE SPACETIME! MISSING BUNDLED TEXTURE!", e);
            return null;
        }
    }

    public void addVector(String name, Vector3f vector) {
        vectors.put(name, vector);
    }

    public Vector3f getVector(String name) {
        Vector3f r = vectors.get(name);
        if (r != null) {
            return r;
        }

        return new Vector3f(0, 0, 0);
    }

    public void addColor(String name, Color color) {
        colors.put(name, color);
    }

    public Color getColor(String name) {
        return getColor(name, Color.BLACK);
    }

    public Color getColor(String name, Color fallback) {
        Color c = colors.get(name);
        if (c != null) {
            return c;
        }

        return fallback;
    }

    public void setColor(Color color) {
        addColor("color", color);
    }

    public Color getColor() {
        return getColor("color", Color.WHITE);
    }

    public void addFloat(String name, float f) {
        floats.put(name, f);
    }

    public float getFloat(String name) {
        Float r = floats.get(name);
        if (r != null) {
            return r;
        }

        return 0;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void asWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
