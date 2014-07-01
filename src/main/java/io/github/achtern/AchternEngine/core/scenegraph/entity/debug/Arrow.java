package io.github.achtern.AchternEngine.core.scenegraph.entity.debug;

import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Arrow extends Mesh {

    public static final Logger LOGGER = LoggerFactory.getLogger(Arrow.class);

    public Arrow() {
        super();
        MeshData data;
        try {
            data = ResourceLoader.getMesh("arrow.obj").getData();
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Arrow OBJ Model.", e);
            data = null;
        }

        this.data = data;
    }
}
