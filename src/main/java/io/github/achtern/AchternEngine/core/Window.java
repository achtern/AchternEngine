package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.RenderTarget;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.*;

public class Window extends Dimension implements RenderTarget {

    public static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    private static Window instance;

    public static RenderTarget getTarget() {
        return instance;
    }

    public static Window get() {
        return instance;
    }

    protected int drawBuffer, readBuffer;

    public Window(Dimension copy) {
        super(copy);
        instance = this;
    }

    public Window(int width, int height) {
        super(width, height);
        instance = this;
    }

    public void create(String title) {
        Display.setTitle(title);

        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(getWidth(), getHeight()));
            Display.create(pixelFormat, contextAtrributes);
            Keyboard.create();
            Mouse.create();
            drawBuffer = glGetInteger(GL_DRAW_BUFFER);
            readBuffer = glGetInteger(GL_READ_BUFFER);
        } catch (LWJGLException e) {
            LOGGER.error("Error setting up input and window", e);
        }
    }

    public void render() {
        Display.update();
    }

    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    @Override
    public void bindAsRenderTarget(DataBinder binder) {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDrawBuffer(drawBuffer);
        glReadBuffer(readBuffer);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glViewport(0, 0, getWidth(), getHeight());

    }

    public Vector2f getCenter() {
        return new Vector2f(getWidth() / 2, getHeight() / 2);
    }

    public String getTitle() {
        return Display.getTitle();
    }

    public void setTitle(String title) {
        Display.setTitle(title);
    }

}
