package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

/**
 * Wrapper of the EntityRetriever and can be used
 * if one only needs a single Entity from the scenegraph.
 * This can be useful, if you want to to retrieve a control
 * Entity from the Camera Node, which has only one control Entity
 */
public class SingleEntityRetriever extends EntityRetriever {

    /**
     * Call #scan(Node) first!
     * Returns the Entity of the given type or null
     * @param type Class of type of Entity
     * @param <T> type of Entity
     * @return Entity | null
     */
    public <T extends Entity> T get(Class<T > type) {
        if (getAll(type).size() > 0) {
            return getAll(type).get(0);
        } else {
            return null;
        }
    }

}
