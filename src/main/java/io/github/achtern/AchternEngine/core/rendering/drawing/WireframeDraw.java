package io.github.achtern.AchternEngine.core.rendering.drawing;

import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;

public class WireframeDraw extends SolidDraw {

    public static final Logger LOGGER = LoggerFactory.getLogger(WireframeDraw.class);

    private float lineWidth = 1;
    private float flashWidth = -666; // Just our "no flash value" value

    @Override
    public void draw(MeshData data) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);


        float prev = glGetFloat(GL_LINE_WIDTH);

        float storedLineWidth = prev;
        if (flashWidth != -666) {
            storedLineWidth = lineWidth;
            lineWidth = flashWidth;
        }


        if (lineWidth != prev) {
            glLineWidth(5f);
        }

        super.draw(data);


        if (lineWidth != prev) {
            glLineWidth(prev);
        }

        if (flashWidth != -666) {
            lineWidth = storedLineWidth;
            flashWidth = -666; // Reset, so we don't keep that.
        }

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void flashLineWidth(float lineWidth) {
        flashWidth = lineWidth;
    }
}
