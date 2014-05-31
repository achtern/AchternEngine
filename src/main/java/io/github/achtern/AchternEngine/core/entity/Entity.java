package io.github.achtern.AchternEngine.core.entity;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.Node;
import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public interface Entity {

    public void input(float delta);
    public void update(float delta);
    public void render(Shader shader, RenderEngine renderEngine);

    public Transform getTransform();

    public void setParent(Node parent);

    public void setEngine(CoreEngine engine);

    public void removed();

    public String getName();

}
