/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
