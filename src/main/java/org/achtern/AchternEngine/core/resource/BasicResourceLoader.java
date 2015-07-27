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

import org.achtern.AchternEngine.core.audio.openal.AudioSource;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.mesh.Mesh;
import org.achtern.AchternEngine.core.rendering.texture.Texture;
import org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import org.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import org.achtern.AchternEngine.core.resource.loader.*;
import org.achtern.AchternEngine.core.resource.loader.json.FigureLoader;
import org.achtern.AchternEngine.core.resource.locations.*;
import org.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
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
public class BasicResourceLoader implements ResourceLoaderProvider {

    /**
     * The Main Logger of the ResourceLoader
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

    /**
     * Extension used by Shader Programs
     */
    public static final String SHADER_PROGRAM_EXT = ".yaml";
    /**
     * Extension used by Figure Declarations
     */
    public static final String FIGURE_DECLARATION_EXT = ".json";

    protected ResourceCache binaryCache = new ResourceCache();
    protected ResourceCache<String> fileCache = new ResourceCache<String>();

    /**
     * This list contains locations to look for resources
     */
    protected List<ResourceLocation> locations = new ArrayList<ResourceLocation>();

    /**
     * Add default locations:
     *
     * Classpath
     *
     * Bundled Textures
     * Bundled Models
     * Bundled Figures
     * Bundled Shaders
     * Bundled Shader Programs
     *
     * Local File System
     */
    public BasicResourceLoader() {
        locations.add(new ClasspathLocation());

        locations.add(new BundledTextureLocation());
        locations.add(new BundledModelLocation());
        locations.add(new BundledFigureLocation());
        locations.add(new BundledShaderLocation());
        locations.add(new BundledShaderProgramLocation());

        locations.add(new FileSystemLocation("."));
    }

    /**
     * Add a ResourceLocation to the search path.
     * NOTE: This will insert it to the very end of the List,
     * resulting in the lowest priority when loading files.
     * Add it to the very front (highest priority)
     * with io.github.achtern.AchternEngine.core.resource.ResourceLoader#pushResourceLocation(ResourceLocation)
     * @param location This will get added to the search path.
     */
    @Override
    public void addResourceLocation(ResourceLocation location) {
        locations.add(location);
    }

    /**
     * @see org.achtern.AchternEngine.core.resource.ResourceLoader#addResourceLocation(ResourceLocation)
     * (Prepends)
     */
    @Override
    public void pushResourceLocation(ResourceLocation location) {
        locations.add(0, location);
    }

    /**
     * Removes a ResourceLocation from the search path
     * @param location Will get removed from the search path.
     */
    @Override
    public void removeResourceLocation(ResourceLocation location) {
        locations.remove(location);
    }

    /**
     * Clears all ResourceLocations from the search path.
     */
    @Override
    public void clearResourceLocations() {
        locations.clear();
    }

    /**
     * Returns all {@link org.achtern.AchternEngine.core.resource.ResourceLocation}s.
     * @return List of all ResourceLocations
     */
    @Override
    public List<ResourceLocation> getResourceLocations() {
        return locations;
    }

    /**
     * Pre Load a Mesh, good at startup, to allow getting the Mesh at runtime.
     * @see #getMesh(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    @Override
    public void preLoadMesh(String name) throws Exception {
        getMesh(name);
    }

    /**
     * Pre Load a Texture, good at startup, to allow getting the Texture at runtime.
     * @see #getTexture(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    @Override
    public void preLoadTexture(String name) throws Exception {
        getTexture(name);
    }

    /**
     * Pre Load a Shader source file.
     * Only parses the sourcefile, no binding or uniform adding at this stage!
     * @see #getShader(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException if loading fails
     */
    @Override
    public void preLoadShader(String name) throws Exception {
        getShader(name);
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
    @Override
    public Figure getFigure(String name) throws Exception {
        return getFigure(name, false);
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
    @Override
    public Figure getFigure(String name, boolean forceLoading) throws Exception {
        LOGGER.info("Loading Figure {}", name);
        return load(name + FIGURE_DECLARATION_EXT, new FigureLoader(), forceLoading);
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
    @Override
    public Mesh getMesh(String name) throws Exception {
        return getMesh(name, false);
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
    @Override
    public Mesh getMesh(String name, boolean forceLoading) throws Exception {
        LOGGER.info("Loading Mesh {}", name);
        return load(name, new MeshLoader(), forceLoading);
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
    @Override
    public Texture getTexture(String name) throws Exception {
        return getTexture(name, null, false);
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
    @Override
    public Texture getTexture(String name, Dimension dimension) throws Exception {
        return getTexture(name, dimension, false);
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
    @Override
    public Texture getTexture(String name, Dimension dimension, boolean forceLoading) throws Exception {
        LOGGER.info("Loading Texture {}", name);
        return load(name, new TextureLoader(dimension), forceLoading);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * (uses a internal cache if the shader has been loaded previously)
     * @see #getShader(String, boolean)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    @Override
    public String getShader(String name) throws Exception {
        return load(name, new ShaderSourceLoader(), false);
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
    @Override
    public String getShader(String name, boolean forceLoading) throws Exception {
        return load(name, new ShaderSourceLoader(), forceLoading);
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
    @Override
    public String getShader(String name, boolean forceLoading, LineBasedParser parser) throws Exception {
        LOGGER.info("Loading Shader Source {}", name);
        return load(name, new ShaderSourceLoader(parser), forceLoading);
    }

    /**
     * Reads programm file from disk and loads the stated source files
     * (uses a internal cache if the shader has been loaded previously)
     * @see #getShaderProgram(String, boolean)
     * @param name Name of the program declaration
     * @return A GLSLProgram with loaded shader sources.
     * @throws IOException if loading fails
     */
    @Override
    public GLSLProgram getShaderProgram(String name) throws Exception {
        return getShaderProgram(name, false);
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
    @Override
    public GLSLProgram getShaderProgram(String name, boolean forceLoading) throws Exception {
        LOGGER.info("Loading Shader Program {}", name);
        return load(name + SHADER_PROGRAM_EXT, new GLSLProgramLoader(), forceLoading);
    }

    @Override
    public AudioSource getAudioSource(String name) throws Exception {
        return getAudioSource(name, false);
    }

    @Override
    public AudioSource getAudioSource(String name, boolean forceLoading) throws Exception {
        LOGGER.info("Loading AudioSource {}", name);
        return load(name, new AudioSourceLoader(), forceLoading);
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
    @Override
    public <T> T load(String name, AsciiFileLoader<T> loader, boolean forceLoading) throws Exception {
        // read it
        String file = readFile(name, forceLoading, loader.getPreProcessor());
        // load (parse) it
        loader.load(name, file);
        // get & return it
        return loader.get();
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
    @Override
    @SuppressWarnings("unchecked")
    public <T, C> T load(String name, BinaryLoader<T, C> loader, boolean forceLoading) throws Exception {
        // Check for cache
        if (binaryCache.has(name) && !forceLoading) {
            Object cache = binaryCache.get(name);

            if (loader.getCacheType() == null) {
                throw new IllegalStateException("Missing Cache Type. BinaryLoader invalid.");
            }

            // Check if types are matching
            if (loader.getCacheType().isAssignableFrom(cache.getClass())) {
                // This is an unchecked cast (but it isn't actually!)
                return loader.fromCache((C) cache);
            }
        }

        InputStream stream;

        // Load it
        loader.load(name, stream = getStream(name));
        // get it
        T value = loader.get();
        // cache it
        binaryCache.add(name, loader.getCache());

        // close the stream
        stream.close();

        // return it
        return value;
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @return The read file
     * @throws IOException if loading fails
     */
    @Override
    public String readFile(String name) throws IOException {
        return readFile(name, false);
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return The read file
     * @throws IOException if loading fails
     */
    @Override
    public String readFile(String name, boolean forceLoading) throws IOException {
        return readFile(name, forceLoading, null);
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
    @Override
    public String readFile(String name, boolean forceLoading, LineBasedParser parser) throws IOException {
        if (fileCache.has(name) && !forceLoading) {
            return fileCache.get(name);
        }

        StringBuilder file = new StringBuilder();

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(getStream(name)));

        String line;

        long lines = 0;

        while ((line = fileReader.readLine()) != null) {
            if (LOGGER.isDebugEnabled()) lines++;

            try {

                if (parser != null) {
                    file.append(parser.parse(line)).append("\n");
                } else {
                    file.append(line).append("\n");
                }

            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw (IOException) e;
                } else {
                    LOGGER.warn("Non IOException when loading file <" + name + ">", e);
                }
            }
        }

        fileReader.close();
        LOGGER.debug("Loaded {} lines from file {}", lines, name);

        fileCache.add(name, file.toString());

        return file.toString();
    }

    /**
     * Checks if a file exists in one of the search locations
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return True If resource was found in search path
     */
    @Override
    public boolean exists(String name) {
        try {
            URL dumpVOID = getURL(name);
        } catch (IOException e) {
            // OK... not found
            return false;
        }

        // If we get here the Resource was found
        return true;
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
    @Override
    public InputStream getStream(String name) throws IOException {
        InputStream is = null;

        for (ResourceLocation location : locations) {
            is = location.getStream(name);

            if (is != null) {
                break;
            }
        }

        if (is == null) {
            throw new IOException("Resource not found in locations. <" + name + ">");
        }

        return new BufferedInputStream(is);
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
    @Override
    public URL getURL(String name) throws IOException {
        URL url = null;

        for (ResourceLocation location : locations) {
            url = location.getURL(name);

            if (url != null) {
                break;
            }
        }

        if (url == null) {
            throw new IOException("Resource not found in locations. <" + name + ">");
        }

        return url;
    }

}
