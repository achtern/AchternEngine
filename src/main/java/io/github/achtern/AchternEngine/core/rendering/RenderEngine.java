package io.github.achtern.AchternEngine.core.rendering;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.util.DataStore;

public interface RenderEngine extends DataStore {

    public void render(Node node);

    public void setRenderTarget(RenderTarget target);

    public RenderTarget getRenderTarget();

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

    public int getSamplerSlot(String name);

    public DataBinder getDataBinder();
}
