package io.github.achtern.AchternEngine.core.resource;

import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Texture;
import io.github.achtern.AchternEngine.core.rendering.Vertex;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.mesh.MeshData;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.IndexedModel;
import io.github.achtern.AchternEngine.core.resource.fileparser.mesh.OBJModel;
import io.github.achtern.AchternEngine.core.resource.locations.*;
import io.github.achtern.AchternEngine.core.util.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ResourceLoader {

    public static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

    private static ResourceCache<MeshData> meshCache = new ResourceCache<MeshData>();
    private static ResourceCache<Texture> textureCache = new ResourceCache<Texture>();
    private static ResourceCache<String> shaderCache = new ResourceCache<String>();

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
     * Bundled Shaders
     *
     * Local File System
     */
    static {
        locations.add(new ClasspathLocation());

        locations.add(new BundledTextureLocation());
        locations.add(new BundledModelLocation());
        locations.add(new BundledShaderLocation());

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
     * Loads a obj file, parses it, optimises it and converts it into renderable Mesh
     * (uses a internal cache if the texture has been loaded previously)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static Mesh getMesh(String name) throws IOException {
        return getMesh(name, false);
    }

    /**
     * Loads a obj file, parses it, optimises it and converts it into renderable Mesh
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static Mesh getMesh(String name, boolean forceLoading) throws IOException {

        if (meshCache.has(name)) {
            return new Mesh(meshCache.get(name));
        }

        BufferedReader meshReader = new BufferedReader(new InputStreamReader(getStream(name)));

        OBJModel objModel = new OBJModel();
        objModel.parse(meshReader);
        IndexedModel model = objModel.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();

        for (int i = 0; i < model.getPositions().size(); i++) {
            Vector3f position = model.getPositions().get(i);
            Vector2f texCoord = model.getTexCoord().get(i);
            Vector3f normal = model.getNormal().get(i);

            vertices.add(new Vertex(position, texCoord, normal));
        }

        Vertex[] vData = new Vertex[vertices.size()];
        vertices.toArray(vData);

        Integer[] iData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(iData);

        Mesh mesh = new Mesh(vData, UInteger.toIntArray(iData));

        meshCache.add(name, mesh.getData());

        return mesh;
    }

    /**
     * Loads a image file and converts it into a Texture
     * (uses a internal cache if the texture has been loaded previously)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name) throws IOException {
        return getTexture(name, false);
    }

    /**
     * Loads a image file and converts it into a Texture
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static Texture getTexture(String name, boolean forceLoading) throws IOException {

        if (textureCache.has(name) && !forceLoading) {
            return textureCache.get(name);
        }

        BufferedImage image = ImageIO.read(getStream(name));

        Texture texture = new Texture(image);

        textureCache.add(name, texture);

        return texture;

    }

    /**
     * Loads a shader File and prepares it for OpenGL
     * (uses a internal cache if the shader has been loaded previously)
     * @param name The relative path (to various ResourceLocations) of the filename
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static String getShader(String name) throws IOException {
        return getShader(name, false, new GLSLParser());
    }

    /**
     * Loads a shader File and prepares it for OpenGL
     * @param name The relative path (to various ResourceLocations) of the filename
     * @param forceLoading if set to true the file will get read again and not read from cache
     * @param parser The optional parser to modify the shader lines.
     * @return A readable Stream | null if not exists
     * @throws IOException if resource not found
     */
    public static String getShader(String name, boolean forceLoading, LineBasedParser parser) throws IOException {

        if (shaderCache.has(name) && !forceLoading) {
            return shaderCache.get(name);
        }

        StringBuilder shaderSourceFile = new StringBuilder();

        BufferedReader shaderReader = new BufferedReader(new InputStreamReader(getStream(name)));

        String line;

        while ((line = shaderReader.readLine()) != null) {

            try {

                if (parser != null) {
                    shaderSourceFile.append(parser.parse(line)).append("\n");
                } else {
                    shaderSourceFile.append(line).append("\n");
                }

            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw (IOException) e;
                } else {
                    LOGGER.warn("Non IOException when loading Shader file.", e);
                }
            }
        }

        shaderReader.close();

        String shaderSource = shaderSourceFile.toString();

        shaderCache.add(name, shaderSource);

        return shaderSource;
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
    protected static InputStream getStream(String name) throws IOException {
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
    protected static URL getURL(String name) throws IOException {
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
