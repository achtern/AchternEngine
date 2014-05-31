package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Node;
import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.contracts.EngineHolder;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public abstract class QuickEntity implements Entity, EngineHolder<CoreEngine> {

    protected Node parent;
    protected CoreEngine engine;
    protected String name;

    public QuickEntity() {
        this("Untitled Entity");
    }

    public QuickEntity(String name) {
        setName(name);
    }

    @Override
    public void input(float delta) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Shader shader, RenderEngine renderEngine) {

    }

    @Override
    public void removed() {
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public Transform getTransform() {
        return this.parent.getTransform();
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    protected Node getParent() {
        return parent;
    }

    @Override
    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    @Override
    public CoreEngine getEngine() {
        return engine;
    }
}
