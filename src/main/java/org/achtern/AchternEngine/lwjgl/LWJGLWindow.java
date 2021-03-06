/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package org.achtern.AchternEngine.lwjgl;

import org.achtern.AchternEngine.core.Window;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.binding.DataBinder;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.*;

public class LWJGLWindow extends Window {

    public static final Logger LOGGER = LoggerFactory.getLogger(LWJGLWindow.class);

    protected int drawBuffer, readBuffer;

    public LWJGLWindow(Dimension copy) {
        super(copy);
        Window.instance = this;
    }

    public LWJGLWindow(int width, int height) {
        super(width, height);
        Window.instance = this;
    }

    @Override
    public void create(String title) {
        setTitle(title);
        enableResize(true);

        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(getWidth(), getHeight()));
            Display.create(pixelFormat, contextAtrributes);
            Keyboard.create();
            Mouse.create();
            AL.create();
            drawBuffer = glGetInteger(GL_DRAW_BUFFER);
            readBuffer = glGetInteger(GL_READ_BUFFER);
        } catch (LWJGLException e) {
            LOGGER.error("Error setting up input and window", e);
        }
    }

    @Override
    public void enableResize(boolean enable) {
        Display.setResizable(true);
    }

    @Override
    public void render() {
        Display.update();
    }

    @Override
    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    @Override
    public boolean resized() {
        boolean rs = Display.wasResized();

        if (rs) {
            setWidth(Display.getWidth());
            setHeight(Display.getHeight());
        }

        return rs;
    }

    @Override
    public void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
        AL.destroy();
    }

    @Override
    public void bindAsRenderTarget(DataBinder binder) {
        if (binder.getState().getBoundFbo() == null && !resized()) {
            // Window is already bound.
            return;
        }
        binder.bindAsRenderTarget(null);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDrawBuffer(drawBuffer);
        glReadBuffer(readBuffer);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        glViewport(0, 0, getWidth(), getHeight());

    }

    @Override
    public String getTitle() {
        return Display.getTitle();
    }

    @Override
    public void setTitle(String title) {
        Display.setTitle(title);
    }
}
