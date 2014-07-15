package io.github.achtern.AchternEngine.core.resource.fileparser;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GLSLProgram {

    protected int id = -1;

    protected String name;

    protected String source;

    protected Map parsed;

    protected List<GLSLScript> scripts;


    protected GLSLScript vertex;
    protected GLSLScript fragment;

    /**
     * Instance to parse yaml
     */
    private Yaml yaml;


    public GLSLProgram(String name, String program) throws IOException {
        this.name = name;
        this.source = program;
        this.yaml = new Yaml();
        this.scripts = new ArrayList<GLSLScript>();

        parse();

    }

    protected void parse() throws IOException {

        this.parsed = (Map) yaml.load(source);

        validate(parsed);

        GLSLScript tmp;

        // Vertex Shader
        String text = ResourceLoader.getShader((String) parsed.get("vertex"));
        pushShader(text, GLSLScript.Type.VERTEX_SHADER);

        // Fragment Shader
        text = ResourceLoader.getShader((String) parsed.get("fragment"));
        pushShader(text, GLSLScript.Type.FRAGMENT_SHADER);

        // Geometry Shader only if specified
        if (parsed.containsKey("geometry")) {
            text = ResourceLoader.getShader((String) parsed.get("geometry"));
            pushShader(text, GLSLScript.Type.GEOMETRY_SHADER);
        }

    }

    protected void pushShader(String source, GLSLScript.Type type) {
        GLSLScript shader = new GLSLScript(this.name, type);
        shader.setSource(source);
        this.scripts.add(shader);

        if (type.equals(GLSLScript.Type.VERTEX_SHADER)) {
            vertex = shader;
        }

        if (type.equals(GLSLScript.Type.FRAGMENT_SHADER)) {
            fragment = shader;
        }
    }

    protected void validate(Map map) {

        checkAndThrow("version", map);
        checkAndThrow("vertex", map);
        checkAndThrow("fragment", map);

    }

    protected void checkAndThrow(String key, Map map) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("'" + key + "' key missing in ShaderProgram <" + name + ">.");
        }
    }

    public List<GLSLScript> getScripts() {
        return scripts;
    }

    public String getName() {
        return name;
    }

    public Map getParsed() {
        return parsed;
    }

    public String getSource() {
        return source;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public GLSLScript getVertex() {
        return vertex;
    }

    public GLSLScript getFragment() {
        return fragment;
    }
}
