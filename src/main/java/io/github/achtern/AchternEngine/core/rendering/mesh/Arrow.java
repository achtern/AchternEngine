package io.github.achtern.AchternEngine.core.rendering.mesh;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Arrow extends Mesh {

    public static final Logger LOGGER = LoggerFactory.getLogger(Arrow.class);

    public Arrow() {
        super();
        MeshData data;
        try {
            data = ResourceLoader.getMesh("arrow.obj").getData();
        } catch (Exception e) {
            LOGGER.warn("Error Loading Bundled Arrow OBJ Model.", e);
            data = null;
        }

        setData(data);
    }
}
