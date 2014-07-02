package io.github.achtern.AchternEngine.core.scenegraph.scanning;

import io.github.achtern.AchternEngine.core.scenegraph.Node;

import java.util.List;

public class BasicWalker implements SceneGraphWalker {

    protected List<Visitor> visitors;

    @Override
    public void scan(Node node) {

        for (Visitor v : visitors) {
            v.on(node);

            for (Node n : node.getChildren().values()) {
                scan(node);
            }
        }

    }

    @Override
    public void addVisitor(Visitor visitor) {
        visitors.add(visitor);
    }

    @Override
    public void clearVisitors() {
        visitors.clear();
    }

    @Override
    public List<Visitor> getVisitors() {
        return visitors;
    }
}
