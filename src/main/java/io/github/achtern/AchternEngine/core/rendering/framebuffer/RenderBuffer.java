package io.github.achtern.AchternEngine.core.rendering.framebuffer;

import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;

public class RenderBuffer {

    private Texture texture;
    private Format format;

    private int id = -1;

    public RenderBuffer(Format format) {
        this.format = format;
    }

    public RenderBuffer(Texture texture) {
        this.texture = texture;
        this.format = texture.getFormat();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getID() {
        return id;
    }

    /**
     * Internal use only!
     * @param id The ID
     */
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (getTexture() == null) {
            return "RenderBuffer[" + getFormat() + "]";
        } else {
            return "RenderBuffer[" + getFormat() + "]<=>" + getTexture();
        }
    }
}
