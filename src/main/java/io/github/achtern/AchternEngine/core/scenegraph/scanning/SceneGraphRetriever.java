package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Entity;

import java.util.List;

public interface SceneGraphRetriever<T extends Entity> extends NodeScanner {

    public List<T> getAll();

    public <E extends Entity> List<E> getAll(Class<E> filter);

    public List<Node> getNodes();

    public boolean contained();

}
