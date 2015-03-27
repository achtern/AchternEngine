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

package org.achtern.AchternEngine.core.resource;

import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.rendering.texture.Texture;
import org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader;
import org.achtern.AchternEngine.core.resource.loader.BinaryLoader;
import org.achtern.AchternEngine.core.scenegraph.entity.Figure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Christian on 27.03.15.
 */
public interface ResourceLoaderProvider {
    void addResourceLocation(ResourceLocation location);

    void pushResourceLocation(ResourceLocation location);

    void removeResourceLocation(ResourceLocation location);

    void clearResourceLocations();

    List<ResourceLocation> getResourceLocations();

    void preLoadMesh(String name) throws Exception;

    void preLoadTexture(String name) throws Exception;

    void preLoadShader(String name) throws Exception;

    Figure getFigure(String name) throws Exception;

    Figure getFigure(String name, boolean forceLoading) throws Exception;

    Mesh getMesh(String name) throws Exception;

    Mesh getMesh(String name, boolean forceLoading) throws Exception;

    Texture getTexture(String name) throws Exception;

    Texture getTexture(String name, Dimension dimension) throws Exception;

    Texture getTexture(String name, Dimension dimension, boolean forceLoading) throws Exception;

    String getShader(String name) throws Exception;

    String getShader(String name, boolean forceLoading) throws Exception;

    String getShader(String name, boolean forceLoading, LineBasedParser parser) throws Exception;

    GLSLProgram getShaderProgram(String name) throws Exception;

    GLSLProgram getShaderProgram(String name, boolean forceLoading) throws Exception;

    <T> T load(String name, AsciiFileLoader<T> loader, boolean forceLoading) throws Exception;

    @SuppressWarnings("unchecked")
    <T, C> T load(String name, BinaryLoader<T, C> loader, boolean forceLoading) throws Exception;

    String readFile(String name) throws IOException;

    String readFile(String name, boolean forceLoading) throws IOException;

    String readFile(String name, boolean forceLoading, LineBasedParser parser) throws IOException;

    boolean exists(String name);

    InputStream getStream(String name) throws IOException;

    URL getURL(String name) throws IOException;
}
