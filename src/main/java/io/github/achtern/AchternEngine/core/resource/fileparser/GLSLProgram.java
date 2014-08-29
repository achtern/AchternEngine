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

package io.github.achtern.AchternEngine.core.resource.fileparser;

import io.github.achtern.AchternEngine.core.bootstrap.NativeObject;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GLSLProgram extends NativeObject {

    protected String name;

    protected String source;

    protected Map parsed;

    protected List<GLSLScript> scripts;

    protected Map<String, Uniform> cachedUniforms;

    protected Map<String, Uniform> cachedExpandedUniforms;

    protected GLSLParser parser;

    /**
     * Instance to load yaml
     */
    private Yaml yaml;


    public GLSLProgram(String name, String program) {
        this.name = name;
        this.source = program;
        this.yaml = new Yaml();
        this.scripts = new ArrayList<GLSLScript>();
    }

    public Uniform getUniform(String name) {
        getUniforms();

        return cachedUniforms.get(name);
    }

    public Uniform getExpandedUniform(String name) {
        getExpandedUniforms();

        return cachedExpandedUniforms.get(name);
    }

    public List<Uniform> getUniforms() {
        if (cachedUniforms != null) {
            return new ArrayList<Uniform>(cachedUniforms.values());
        }

        Map<String, Uniform> uniforms = new HashMap<String, Uniform>();

        for (GLSLScript s : getScripts()) {
            for (Uniform u : s.getUniforms()) {
                uniforms.put(u.getName(), u);
            }
        }

        cachedUniforms = uniforms;

        return getUniforms();
    }

    public List<Uniform> getExpandedUniforms() {
        if (cachedExpandedUniforms != null) {
            return new ArrayList<Uniform>(cachedExpandedUniforms.values());
        }

        Map<String, Uniform> uniforms = new HashMap<String, Uniform>();

        for (GLSLScript s : getScripts()) {
            for (Uniform u : s.getExpandedUniforms()) {
                uniforms.put(u.getName(), u);
            }
        }

        cachedExpandedUniforms = uniforms;

        return getExpandedUniforms();
    }

    public void parse(GLSLParser parser) throws Exception {
        setParser(parser);
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
        getParser().process(shader);
        this.scripts.add(shader);
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

    public void setParser(GLSLParser parser) {
        this.parser = parser;
    }

    public GLSLParser getParser() {
        return parser;
    }
}
