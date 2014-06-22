package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;

public class Window {

    public static final Logger LOGGER = LoggerFactory.getLogger(Window.class);

    public static void create(Dimension dimension, String title) {
        create(dimension.getWidth(), dimension.getHeight(), title);
    }

    public static void create(int width, int height, String title) {
        Display.setTitle(title);

        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(pixelFormat, contextAtrributes);
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            LOGGER.error("Error setting up input and window", e);
        }
    }

    public static void render() {
        Display.update();
    }

    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    public static void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    public static void bindAsRenderTarget() {

        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glViewport(0, 0, getWidth(), getHeight());

    }

    public static Vector2f getCenter() {
        return new Vector2f(getWidth() / 2, getHeight() / 2);
    }

    public static Dimension getDimension() {
        return new Dimension(getWidth(), getHeight());
    }

    public static int getWidth() {
        return Display.getDisplayMode().getWidth();
    }

    public static int getHeight() {
        return Display.getDisplayMode().getHeight();
    }

    public static String getTitle() {
        return Display.getTitle();
    }

    public static void setTitle(String title) {
        Display.setTitle(title);
    }

}
