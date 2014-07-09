package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;

public interface RenderEngine {

    public void render(Node node);

    public void addRenderPass(RenderPass pass);

    public void removeRenderPass(RenderPass pass);

    public void addCamera(Camera camera);

    public RenderPass getActiveRenderPass();

    public void setClearColor(Color color);

    public String getOpenGLVersion();

    public Camera getMainCamera();

    public void setMainCamera(Camera mainCamera);

    public Color getClearColor();

    public DrawStrategy getDrawStrategy();

    public void setDrawStrategy(DrawStrategy drawStrategy);

}
