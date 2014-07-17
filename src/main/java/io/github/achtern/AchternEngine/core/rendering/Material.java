package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.util.CommonDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Material extends CommonDataStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(Material.class);

    private boolean wireframe = false;

    @Override
    public Texture getTexture(String name) {
        Texture r = super.getTexture(name);
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


    public void setColor(Color color) {
        addColor("color", color);
    }

    public Color getColor() {
        Color c = getColor("color");
        if (c == null) {
            return Color.WHITE;
        } else {
            return c;
        }
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public void asWireframe(boolean wireframe) {
        this.wireframe = wireframe;
    }
}
