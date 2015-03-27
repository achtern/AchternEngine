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

import lombok.Getter;
import lombok.Setter;
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
 * The {@link org.achtern.AchternEngine.core.resource.ResourceLoader} is the main entry point
 * to the FileSystem and other Resources.
 * The ResourceLoader allows you to load {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh}es and
 * {@link org.achtern.AchternEngine.core.scenegraph.entity.Figure}s,
 * {@link org.achtern.AchternEngine.core.rendering.shader.Shader}s
 * ({@link org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram}),
 * {@link org.achtern.AchternEngine.core.rendering.texture.Texture}s and
 * any type of String from a file.
 * You can easily load your own resources from any type of file.
 * You can get {@link java.net.URL}s and {@link java.io.InputStream}s for any filename or write your
 * own {@link org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader} to load text-based files.
 * The ResourceLoader looks into given {@link org.achtern.AchternEngine.core.resource.ResourceLocation}s in
 * order to get URLs and InputStreams for the given name.
 * By default the Classpath, Bundled Textures, Meshes, Figures, Shaders and the Local File System
 * are added as ResourceLocation by default. You can add your own by using {@link #addResourceLocation(ResourceLocation)}
 * or remove all {@link #clearResourceLocations()}.
 *
 * The ResourceLoader uses a very basic {@link org.achtern.AchternEngine.core.resource.ResourceCache}s in order
 * to avoid re-reading data from disk. These caches - however - are not caching object, only the data.
 */
public class ResourceLoader {

    @Getter @Setter private static ResourceLoaderProvider provider = new BasicResourceLoader();

    public static void addResourceLocation(ResourceLocation location) {
        provider.addResourceLocation(location);
    }

    public static void pushResourceLocation(ResourceLocation location) {
        provider.pushResourceLocation(location);
    }

    public static void removeResourceLocation(ResourceLocation location) {
        provider.removeResourceLocation(location);
    }

    public static void clearResourceLocations() {
        provider.clearResourceLocations();
    }

    public static List<ResourceLocation> getResourceLocations() {
        return provider.getResourceLocations();
    }

    public static void preLoadMesh(String name) throws Exception {
        provider.preLoadMesh(name);
    }

    public static void preLoadTexture(String name) throws Exception {
        provider.preLoadTexture(name);
    }

    public static void preLoadShader(String name) throws Exception {
        provider.preLoadShader(name);
    }

    public static Figure getFigure(String name) throws Exception {
        return provider.getFigure(name);
    }

    public static Figure getFigure(String name, boolean forceLoading) throws Exception {
        return provider.getFigure(name, forceLoading);
    }

    public static Mesh getMesh(String name) throws Exception {
        return provider.getMesh(name);
    }

    public static Mesh getMesh(String name, boolean forceLoading) throws Exception {
        return provider.getMesh(name, forceLoading);
    }

    public static Texture getTexture(String name) throws Exception {
        return provider.getTexture(name);
    }

    public static Texture getTexture(String name, Dimension dimension) throws Exception {
        return provider.getTexture(name, dimension);
    }

    public static Texture getTexture(String name, Dimension dimension, boolean forceLoading) throws Exception {
        return provider.getTexture(name, dimension, forceLoading);
    }

    public static String getShader(String name) throws Exception {
        return provider.getShader(name);
    }

    public static String getShader(String name, boolean forceLoading) throws Exception {
        return provider.getShader(name, forceLoading);
    }

    public static String getShader(String name, boolean forceLoading, LineBasedParser parser) throws Exception {
        return provider.getShader(name, forceLoading, parser);
    }

    public static GLSLProgram getShaderProgram(String name) throws Exception {
        return provider.getShaderProgram(name);
    }

    public static GLSLProgram getShaderProgram(String name, boolean forceLoading) throws Exception {
        return provider.getShaderProgram(name, forceLoading);
    }

    public static <T> T load(String name, AsciiFileLoader<T> loader, boolean forceLoading) throws Exception {
        return provider.load(name, loader, forceLoading);
    }

    @SuppressWarnings("unchecked")
    public static <T, C> T load(String name, BinaryLoader<T, C> loader, boolean forceLoading) throws Exception {
        return provider.load(name, loader, forceLoading);
    }

    public static String readFile(String name) throws IOException {
        return provider.readFile(name);
    }

    public static String readFile(String name, boolean forceLoading) throws IOException {
        return provider.readFile(name, forceLoading);
    }

    public static String readFile(String name, boolean forceLoading, LineBasedParser parser) throws IOException {
        return provider.readFile(name, forceLoading, parser);
    }

    public static boolean exists(String name) {
        return provider.exists(name);
    }

    public static InputStream getStream(String name) throws IOException {
        return provider.getStream(name);
    }

    public static URL getURL(String name) throws IOException {
        return provider.getURL(name);
    }
}
