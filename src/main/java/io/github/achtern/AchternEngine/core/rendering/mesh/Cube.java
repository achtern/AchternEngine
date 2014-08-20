package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cube extends Mesh {

    public static final Logger LOGGER = LoggerFactory.getLogger(Cube.class);

    public Cube() {
        super();
        MeshData data;
        try {
            data = ResourceLoader.getMesh("cube.obj").getData();
        } catch (Exception e) {
            LOGGER.warn("Error Loading Bundled Cube OBJ Model.", e);
            data = null;
        }

        setData(data);

    }

}
