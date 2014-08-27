package io.github.achtern.AchternEngine.lwjgl.rendering;

import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import io.github.achtern.AchternEngine.core.rendering.drawing.DrawStrategy;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;

public class LWJGLSolidDraw implements DrawStrategy {

    @Override
    public void draw(DataBinder binder, Mesh mesh) {
        binder.draw(mesh);
    }

}
