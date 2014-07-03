package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

public class SingleEntityRetriever extends EntityRetriever {

    public <T extends Entity> T get(Class<T > type) {
        return getAll(type).get(0);
    }

}
