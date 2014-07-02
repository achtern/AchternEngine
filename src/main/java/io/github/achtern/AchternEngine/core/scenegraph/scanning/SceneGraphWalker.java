package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;

import java.util.List;

public interface SceneGraphWalker extends NodeScanner {

    public interface Visitor {

        public void on(Node node);

    }

    public void scan(Node node);

    public void addVisitor(Visitor visitor);

    public void clearVisitors();

    public List<Visitor> getVisitors();

}
