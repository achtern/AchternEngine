package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

public class SingleEntityRetriever extends EntityRetriever {

    public <T extends Entity> T get(Class<T > type) {
        if (getAll(type).size() > 0) {
            return getAll(type).get(0);
        } else {
            return null;
        }
    }

}
