package io.github.achtern.AchternEngine.core.resource;

import io.github.achtern.AchternEngine.core.contracts.TexturableData;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJParser;
import io.github.achtern.AchternEngine.core.resource.loader.GLSLProgramLoader;
import io.github.achtern.AchternEngine.core.resource.loader.Loader;
import io.github.achtern.AchternEngine.core.resource.loader.MeshLoader;
import io.github.achtern.AchternEngine.core.resource.loader.ShaderSourceLoader;
import io.github.achtern.AchternEngine.core.resource.locations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link io.github.achtern.AchternEngine.core.resource.ResourceLoader} is the main entry point
 * to the FileSystem and other Resources.
 * The ResourceLoader allows you to load {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh}es and
 * {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Figure}s,
 * {@link io.github.achtern.AchternEngine.core.rendering.shader.Shader}s
 * ({@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram}),
 * {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}s and
 * any type of String from a file.
 * You can easily load your own resources from any type of file.
 * You can get {@link java.net.URL}s and {@link java.io.InputStream}s for any filename or write your
 * own {@link io.github.achtern.AchternEngine.core.resource.loader.Loader} to load text-based files.
 * The ResourceLoader looks into given {@link io.github.achtern.AchternEngine.core.resource.ResourceLocation}s in
 * order to get URLs and InputStreams for the given name.
 * By default the Classpath, Bundled Textures, Meshes, Figures, Shaders and the Local File System
 * are added as ResourceLocation by default. You can add your own by using {@link #addResourceLocation(ResourceLocation)}
 * or remove all {@link #clearResourceLocations()}.
 *
 * The ResourceLoader uses a very basic {@link io.github.achtern.AchternEngine.core.resource.ResourceCache}s in order
 * to avoid re-reading data from disk. These caches - however - are not caching object, only the data.
 */
public class ResourceLoader {

    /**
     * The Main Logger of the ResourceLoader
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

    /**
     * Extension used by Shader Programs
     */
    public static final String SHADER_PROGRAM_EXT = ".yaml";

    private static ResourceCache<TexturableData> textureCache = new ResourceCache<TexturableData>();
    private static ResourceCache<String> fileCache = new ResourceCache<String>();
    private static ResourceCache<OBJParser> meshCache = new ResourceCache<OBJParser>();

    /**
     * This list contains locations to look for resources
     */
    private static List<ResourceLocation> locations = new ArrayList<ResourceLocation>();

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
    static {
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
    public static void addResourceLocation(ResourceLocation location) {
        locations.add(location);
    }

    /**
     * @see io.github.achtern.AchternEngine.core.resource.ResourceLoader#addResourceLocation(ResourceLocation)
     * (Prepends)
     */
    public static void pushResourceLocation(ResourceLocation location) {
        locations.add(0, location);
    }

    /**
     * Removes a ResourceLocation from the search path
     * @param location Will get removed from the search path.
     */
    public static void removeResourceLocation(ResourceLocation location) {
        locations.remove(location);
    }

    /**
     * Clears all ResourceLocations from the search path.
     */
    public static void clearResourceLocations() {
        locations.clear();
    }

    /**
     * Pre Load a Mesh, good at startup, to allow getting the Mesh at runtime.
     * @see #getMesh(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException
     */
    public static void preLoadMesh(String name) throws Exception {
        getMesh(name);
    }

    /**
     * Pre Load a Texture, good at startup, to allow getting the Texture at runtime.
     * @see #getTexture(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException
     */
    public static void preLoadTexture(String name) throws IOException {
        getTexture(name);
    }

    /**
     * Pre Load a Shader source file.
     * Only parses the sourcefile, no binding or uniform adding at this stage!
     * @see #getShader(String)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @throws IOException
     */
    public static void preLoadShader(String name) throws Exception {
        getShader(name);
    }

    /**
     * Loads a .obj file, parses it, optimises it and converts it into renderable {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh}
     * When loading from cache only the data will get reused, not the object itself and it
     * will have to use new buffers (handled automatically).
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return Mesh (new instance)
     * @throws Exception
     */
    public static Mesh getMesh(String name) throws Exception {
        return getMesh(name, false);
    }

    /**
     * Loads a .obj file, parses it, optimises it and converts it into renderable {@link io.github.achtern.AchternEngine.core.rendering.mesh.Mesh}
     * When loading from cache only the data will get reused, not the object itself and it
     * will have to use new buffers (handled automatically).
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return Mesh (new instance)
     * @throws Exception
     */
    public static Mesh getMesh(String name, boolean forceLoading) throws Exception {
        // With Mesh we have to be carefull. The LineBasedParser parse,
        // doesn't run when loading from cache. This means, the OBJParser
        // is empty and therefor the Mesh as well.
        // If we force load, we just can proceed as usual,
        // and cache just the OBJParser.
        if (!meshCache.has(name) || forceLoading) {
            MeshLoader l = new MeshLoader();
            Mesh m = load(name, l, true); // we do not have the mesh in the cache ALWAYS forceLoad
            meshCache.add(name, (OBJParser) l.getPreProcessor());
            return m;
        }

        // Now we are using the cached value. and do not need to load the file
        // we can just access the MeshLoader directly.
        return (new MeshLoader(meshCache.get(name))).get();
    }

    /**
     * Loads a image file and converts it into a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link io.github.achtern.AchternEngine.core.contracts.TexturableData} will be
     * used to create the new {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name) throws IOException {
        return getTexture(name, null, false);
    }

    /**
     * Loads a image file and converts it into a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link io.github.achtern.AchternEngine.core.contracts.TexturableData} will be
     * used to create the new {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * The {@link io.github.achtern.AchternEngine.core.rendering.Dimension} can be used to modify the width and height
     * of the Texture. The image will get resized.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param dimension The dimension of the new texture
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name, Dimension dimension) throws IOException {
        return getTexture(name, dimension, false);
    }

    /**
     * Loads a image file and converts it into a {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture}.
     * If the same image has been loaded previously the
     * {@link io.github.achtern.AchternEngine.core.contracts.TexturableData} will be
     * used to create the new {@link io.github.achtern.AchternEngine.core.rendering.texture.Texture} object.
     * The {@link io.github.achtern.AchternEngine.core.rendering.Dimension} can be used to modify the width and height
     * of the Texture. The image will get resized.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param dimension The dimension of the new texture
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A new Texture Object
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name, Dimension dimension, boolean forceLoading) throws IOException {

        if (textureCache.has(name) && !forceLoading) {
            // We only store the TexturableData, in order to avoid a clone.
            return new Texture(textureCache.get(name));
        }

        BufferedImage image = ImageIO.read(getStream(name));

        Texture texture;
        if (dimension != null) {
            texture = new Texture(image, dimension);
        } else {
            texture = new Texture(image);
        }

        textureCache.add(name, texture);

        return texture;

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
        return load(name, new ShaderSourceLoader(), false);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * (uses a internal cache if the shader has been loaded previously)
     * This uses the default LineBasedParser ({@link io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser})
     * @see #getShader(String, boolean, io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return ShaderSource String
     * @throws Exception
     */
    public static String getShader(String name, boolean forceLoading) throws Exception {
        return load(name, new ShaderSourceLoader(), forceLoading);
    }

    /**
     * Loads a shader File and prepares it for OpenGL.
     * Calls {@link #load(String, io.github.achtern.AchternEngine.core.resource.loader.Loader, boolean)}
     * internally and uses the {@link io.github.achtern.AchternEngine.core.resource.loader.ShaderSourceLoader}.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the ShaderSourceLoader itself.
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param parser The optional parser to modify the shader lines.
     * @return ShaderSource String
     * @throws Exception
     */
    public static String getShader(String name, boolean forceLoading, LineBasedParser parser) throws Exception {
        return load(name, new ShaderSourceLoader(parser), forceLoading);
    }

    /**
     * Reads programm file from disk and loads the stated source files
     * (uses a internal cache if the shader has been loaded previously)
     * @see #getShaderProgram(String, boolean)
     * @param name Name of the program declaration
     * @return A GLSLProgram with loaded shader sources.
     * @throws IOException
     */
    public static GLSLProgram getShaderProgram(String name) throws Exception {
        return getShaderProgram(name, false);
    }

    /**
     * Reads programm file from disk and loads the stated source files
     * Calls {@link #load(String, io.github.achtern.AchternEngine.core.resource.loader.Loader, boolean)}
     * internally and uses the {@link io.github.achtern.AchternEngine.core.resource.loader.GLSLProgramLoader}.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the GLSLProgramLoader itself.
     * @param name Name of the program declaration
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A GLSLProgram with loaded shader sources.
     * @throws Exception
     */
    public static GLSLProgram getShaderProgram(String name, boolean forceLoading) throws Exception {
        return load(name + SHADER_PROGRAM_EXT, new GLSLProgramLoader(), forceLoading);
    }

    /**
     * Loads a Object from a text-file using a {@link io.github.achtern.AchternEngine.core.resource.loader.Loader}.
     * Calls {@link #readFile(String)} internally.
     * An IOException is thrown on read errors and other Exceptions might be
     * thrown from the Loader itself.
     * @param name The name of the file to load
     * @param loader The loader used to convert the file into the Object
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param <T> The type of Object to load
     * @return The loaded object
     * @throws Exception
     */
    public static <T> T load(String name, Loader<T> loader, boolean forceLoading) throws Exception {
        String file = readFile(name, forceLoading, loader.getPreProcessor());

        loader.parse(name, file);

        return loader.get();
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @return The read file
     * @throws IOException
     */
    public static String readFile(String name) throws IOException {
        return readFile(name, false);
    }

    /**
     * Reads a file into a String. Lines are separated by <code>\n</code>.
     * If a file with the same name (the same file?) a cached value will be used.
     * @param name The name of the file to load
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return The read file
     * @throws IOException
     */
    public static String readFile(String name, boolean forceLoading) throws IOException {
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
     * @throws IOException
     */
    public static String readFile(String name, boolean forceLoading, LineBasedParser parser) throws IOException {
        if (fileCache.has(name) && !forceLoading) {
            return fileCache.get(name);
        }

        StringBuilder file = new StringBuilder();

        BufferedReader fileReader = new BufferedReader(new InputStreamReader(getStream(name)));

        String line;

        while ((line = fileReader.readLine()) != null) {

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

        fileCache.add(name, file.toString());

        return file.toString();
    }

    /**
     * Checks if a file exists in one of the search locations
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return True If resource was found in search path
     */
    public static boolean exists(String name) {
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
    public static InputStream getStream(String name) throws IOException {
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
    public static URL getURL(String name) throws IOException {
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
