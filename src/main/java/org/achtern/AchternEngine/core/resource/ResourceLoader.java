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

    /**
     * Add a ResourceLocation to the search path.
     * NOTE: This will insert it to the very end of the List,
     * resulting in the lowest priority when loading files.
     * Add it to the very front (highest priority)
     * with io.github.achtern.AchternEngine.core.resource.ResourceLoader#pushResourceLocation(ResourceLocation)
     * @param location This will get added to the search path.
     */
    public static void addResourceLocation(ResourceLocation location) {
        provider.addResourceLocation(location);
    }

    /**
     * @see org.achtern.AchternEngine.core.resource.ResourceLoader#addResourceLocation(ResourceLocation)
     * (Prepends)
     * @param location This will get added to the front of the search path
     */
    public static void pushResourceLocation(ResourceLocation location) {
        provider.pushResourceLocation(location);
    }

    /**
     * Removes a ResourceLocation from the search path
     * @param location Will get removed from the search path.
     */
    public static void removeResourceLocation(ResourceLocation location) {
        provider.removeResourceLocation(location);
    }

    /**
     * Clears all ResourceLocations from the search path.
     */
    public static void clearResourceLocations() {
        provider.clearResourceLocations();
    }

    /**
     * Returns all {@link org.achtern.AchternEngine.core.resource.ResourceLocation}s.
     * @return List of all ResourceLocations
     */
    public static List<ResourceLocation> getResourceLocations() {
        return provider.getResourceLocations();
    }

    /**
     * Pre Load a Mesh, good at startup, to allow getting the Mesh at runtime.
     * @see #getMesh(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    public static void preLoadMesh(String name) throws Exception {
        provider.preLoadMesh(name);
    }

    /**
     * Pre Load a Texture, good at startup, to allow getting the Texture at runtime.
     * @see #getTexture(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    public static void preLoadTexture(String name) throws Exception {
        provider.preLoadTexture(name);
    }

    /**
     * Pre Load a Shader source file.
     * Only parses the sourcefile, no binding or uniform adding at this stage!
     * @see #getShader(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    public static void preLoadShader(String name) throws Exception {
        provider.preLoadShader(name);
    }

    /**
     * Loads a {@link org.achtern.AchternEngine.core.scenegraph.entity.Figure} from a json
     * declaration file. Read more about it here {@link org.achtern.AchternEngine.core.resource.loader.json.FigureLoader}
     * When loading from cache, only the file itself will get reused. This returns a completely independent Figure.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the Figure itself.
     * @param name The name of the file to load
     * @return Figure (new instance)
     * @throws Exception if loading/parsing/processing fails
     */
    public static Figure getFigure(String name) throws Exception {
        return provider.getFigure(name);
    }

    /**
     * Loads a {@link org.achtern.AchternEngine.core.scenegraph.entity.Figure} from a json
     * declaration file. Read more about it here {@link org.achtern.AchternEngine.core.resource.loader.json.FigureLoader}
     * When loading from cache, only the file itself will get reused. This returns a completely independent Figure.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the Figure itself.
     * @param name The name of the file to load
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return Figure (new instance)
     * @throws Exception if loading/parsing/processing fails
     */
    public static Figure getFigure(String name, boolean forceLoading) throws Exception {
        return provider.getFigure(name, forceLoading);
    }

    /**
     * Loads a .obj file, parses it, optimises it and converts it into renderable {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh}
     * When loading from cache only the data will get reused, not the object itself and it
     * will have to use new buffers (handled automatically).
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return Mesh (new instance)
     * @throws Exception if loading/parsing/processing fails
     */
    public static Mesh getMesh(String name) throws Exception {
        return provider.getMesh(name);
    }

    /**
     * Loads a .obj file, parses it, optimises it and converts it into renderable {@link org.achtern.AchternEngine.core.rendering.mesh.Mesh}
     * When loading from cache only the data will get reused, not the object itself and it
     * will have to use new buffers (handled automatically).
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return Mesh (new instance)
     * @throws Exception if loading/parsing/processing fails
     */
    public static Mesh getMesh(String name, boolean forceLoading) throws Exception {
        return provider.getMesh(name, forceLoading);
    }

    /**
     * Loads a image file and converts it into a {@link org.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link org.achtern.AchternEngine.core.rendering.texture.TexturableData} will be
     * used to create the new {@link org.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name) throws Exception {
        return provider.getTexture(name);
    }

    /**
     * Loads a image file and converts it into a {@link org.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link org.achtern.AchternEngine.core.rendering.texture.TexturableData} will be
     * used to create the new {@link org.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * The {@link org.achtern.AchternEngine.core.rendering.Dimension} can be used to modify the width and height
     * of the Texture. The image will get resized.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param dimension The dimension of the new texture
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name, Dimension dimension) throws Exception {
        return provider.getTexture(name, dimension);
    }

    /**
     * Loads a image file and converts it into a {@link org.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link org.achtern.AchternEngine.core.rendering.texture.TexturableData} will be
     * used to create the new {@link org.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * The {@link org.achtern.AchternEngine.core.rendering.Dimension} can be used to modify the width and height
     * of the Texture. The image will get resized.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param dimension The dimension of the new texture
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name, Dimension dimension, boolean forceLoading) throws Exception {
        return provider.getTexture(name, dimension, forceLoading);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * (uses a internal cache if the shader has been loaded previously)
     * @see #getShader(String, boolean)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static String getShader(String name) throws Exception {
        return provider.getShader(name);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * (uses a internal cache if the shader has been loaded previously)
     * This uses the default LineBasedParser ({@link org.achtern.AchternEngine.core.resource.fileparser.GLSLParser})
     * @see #getShader(String, boolean, org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return ShaderSource String
     * @throws Exception if loading/parsing/processing fails
     */
    public static String getShader(String name, boolean forceLoading) throws Exception {
        return provider.getShader(name, forceLoading);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * Calls {@link #load(String, org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader, boolean)}
     * internally and uses the {@link org.achtern.AchternEngine.core.resource.loader.ShaderSourceLoader}.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the ShaderSourceLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param parser The optional parser to modify the shader lines.
     * @return ShaderSource String
     * @throws Exception if loading/parsing/processing fails
     */
    public static String getShader(String name, boolean forceLoading, LineBasedParser parser) throws Exception {
        return provider.getShader(name, forceLoading, parser);
    }

    /**
     * Reads programm file from disk and loads the stated source files
     * (uses a internal cache if the shader has been loaded previously)
     * @see #getShaderProgram(String, boolean)
     * @param name Name of the program declaration
     * @return A GLSLProgram with loaded shader sources.
     * @throws IOException if loading fails
     */
    public static GLSLProgram getShaderProgram(String name) throws Exception {
        return provider.getShaderProgram(name);
    }

    /**
     * Reads programm file from disk and loads the stated source files
     * Calls {@link #load(String, org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader, boolean)}
     * internally and uses the {@link org.achtern.AchternEngine.core.resource.loader.GLSLProgramLoader}.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name Name of the program declaration
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A GLSLProgram with loaded shader sources.
     * @throws Exception if loading/parsing/processing fails
     */
    public static GLSLProgram getShaderProgram(String name, boolean forceLoading) throws Exception {
        return provider.getShaderProgram(name, forceLoading);
    }

    /**
     * Loads a Object from a text-file using a {@link org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader}.
     * Calls {@link #readFile(String)} internally.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the Loader itself.
     * @param name The name of the file to load
     * @param loader The loader used to convert the file into the Object
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param <T> The type of Object to load
     * @return The loaded object
     * @throws Exception if loading/parsing/processing fails
     */
    public static <T> T load(String name, AsciiFileLoader<T> loader, boolean forceLoading) throws Exception {
        return provider.load(name, loader, forceLoading);
    }

    /**
     * Loads a Object from an {@link java.io.InputStream} using a {@link org.achtern.AchternEngine.core.resource.loader.AsciiFileLoader}.
     * Calls {@link #getStream(String)} internally.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the Loader itself.
     * @param name The name of the file to load
     * @param loader The loader used to convert the file into the Object
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param <T> The type of Object to load
     * @param <C> The type to cache the data
     * @return The loaded object
     * @throws Exception if loading/parsing/processing fails
     */
    @SuppressWarnings("unchecked")
    public static <T, C> T load(String name, BinaryLoader<T, C> loader, boolean forceLoading) throws Exception {
        return provider.load(name, loader, forceLoading);
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @return The read file
     * @throws IOException if loading fails
     */
    public static String readFile(String name) throws IOException {
        return provider.readFile(name);
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return The read file
     * @throws IOException if loading fails
     */
    public static String readFile(String name, boolean forceLoading) throws IOException {
        return provider.readFile(name, forceLoading);
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * The LineBasedParser doesn't run when loaded from cache, but any modifications
     * made by the parser are stored into the cache.
     * @param name The name of the file to load
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param parser The parser gets called on every line (can be null)
     * @return The read file
     * @throws IOException if loading fails
     */
    public static String readFile(String name, boolean forceLoading, LineBasedParser parser) throws IOException {
        return provider.readFile(name, forceLoading, parser);
    }

    /**
     * Checks if a file exists in one of the search locations
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return True If resource was found in search path
     */
    public static boolean exists(String name) {
        return provider.exists(name);
    }

    /**
     * Searches through all ResourceLocations and returns readable InputStream
     * NOTE: The first occurrence of an file will get used.
     * This depends on the order of ResourceLocations of the list.
     * By default the Classpath takes precedence over bundled stuff and after that the local filesystem
     * getting searched.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static InputStream getStream(String name) throws IOException {
        return provider.getStream(name);
    }

    /**
     * Searches through all ResourceLocations and returns the full URL
     * NOTE: The first occurrence of an file will get used.
     * This depends on the order of ResourceLocations of the list.
     * By default the Classpath takes precedence over bundled stuff and after that the local filesystem
     * getting searched.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static URL getURL(String name) throws IOException {
        return provider.getURL(name);
    }
}
