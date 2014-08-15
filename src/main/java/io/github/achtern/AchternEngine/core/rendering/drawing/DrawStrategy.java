package io.github.achtern.AchternEngine.core.rendering.drawing;

import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;

public interface DrawStrategy {

    public void draw(DataBinder binder, Mesh mesh);

}
