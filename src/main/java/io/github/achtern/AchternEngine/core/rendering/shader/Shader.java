package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.BaseLight;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.DirectionalLight;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.PointLight;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.SpotLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private GLSLParser parser = new GLSLParser();

    private GLSLScript vertexShader;
    private GLSLScript geometryShader;
    private GLSLScript fragmentShader;

    private int program;

    private HashMap<String, Integer> uniforms;
    private RenderEngine renderEngine;

    public Shader() {
        this.program = glCreateProgram();
        this.uniforms = new HashMap<String, Integer>();

        if (program == 0) {
            LOGGER.error("Shader Program creation failed. '0' is not a valid memory address.");
        }
    }

    public void finish() {
        compile();
        addUniforms();
    }

    public void addVertexShader(String text) {
        vertexShader = new GLSLScript(this.getClass().getSimpleName(), GLSLScript.Type.VERTEX_SHADER);
        vertexShader.setSource(text);
        this.addProgram(text, GL_VERTEX_SHADER);
    }

    public void addGeometryShader(String text) {
        geometryShader = new GLSLScript(this.getClass().getSimpleName(), GLSLScript.Type.GEOMETRY_SHADER);
        geometryShader.setSource(text);
        this.addProgram(text, GL_GEOMETRY_SHADER);
    }

    public void addFragmentShader(String text) {
        fragmentShader = new GLSLScript(this.getClass().getSimpleName(), GLSLScript.Type.FRAGMENT_SHADER);
        fragmentShader.setSource(text);
        this.addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void bind() {
        glUseProgram(this.program);
    }

    public void addUniforms() {

        if (vertexShader != null) {
            magicUniforms(vertexShader);
        }
        if (geometryShader != null) {
            magicUniforms(geometryShader);
        }
        if (fragmentShader != null) {
            magicUniforms(fragmentShader);
        }

    }

    public void addUniform(String name) {

        int uniformLoc = glGetUniformLocation(this.program, name);

        if (uniformLoc == 0xFFFFFFFF) {
            LOGGER.warn("Could not find uniform location for '{}'", name);
        }

        this.uniforms.put(name, uniformLoc);

    }

    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine) {
        material.getTexture("diffuse").bind();
    }

    public void compile() {
        glLinkProgram(this.program);

        if (glGetProgrami(this.program, GL_LINK_STATUS) == 0) {
            LOGGER.warn(glGetProgramInfoLog(this.program, 1024) + " @ {}", this.getClass().getSimpleName());
        }

        glValidateProgram(this.program);

        if (glGetProgrami(this.program, GL_VALIDATE_STATUS) == 0) {
            LOGGER.warn(glGetProgramInfoLog(this.program, 1024) + " @ {}", this.getClass().getSimpleName());
        }

    }

    /**
     * Tries to load both vertex and fragment shader, compiles and setUniforms!
     * @param name Name of the file (without .ext)
     * @throws IOException
     */
    public void setUpFromFile(String name) throws IOException {
        setUpFromFile(name, true, false, true);
    }

    /**
     * Tries to load the shaders from file, compiles and setUniforms!
     * Using extensions gvs, ggs, gfs for vertex, geometry and fragment
     * shaders respectively.
     * @param name The name of the shader file
     * @param vs Whether to load a vertex shader
     * @param gs Whether to load a geometry shader
     * @param fs Whether to load a fragment shader
     * @throws IOException
     */
    public void setUpFromFile(String name, boolean vs, boolean gs, boolean fs) throws IOException {
        if (vs) addVertexShader(ResourceLoader.getShader(name + ".gvs"));
        if (gs) addGeometryShader(ResourceLoader.getShader(name + ".ggs"));
        if (fs) addFragmentShader(ResourceLoader.getShader(name + ".gfs"));

        finish();
    }

    /**
     * Binds the Attribute Location (as specified in the GLSL shader sources)
     * @param name The name of the attribute
     * @param location The location id
     */
    public void setAttribLocation(String name, int location) {
        glBindAttribLocation(program, location, name);
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            LOGGER.error("Shader creation failed. '{}' is not a valid memory address.", shader);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            LOGGER.warn(glGetShaderInfoLog(shader, 1024));
        }

        glAttachShader(this.program, shader);
    }

    public void setUniform(String name, Vector3f vec) {
        glUniform3f(this.uniforms.get(name), vec.getX(), vec.getY(), vec.getZ());
    }

    public void setUniform(String name, Vector2f vec) {
        glUniform2f(this.uniforms.get(name), vec.getX(), vec.getY());
    }

    public void setUniform(String name, Matrix4f matrix) {
        glUniformMatrix4(this.uniforms.get(name), true, (FloatBuffer) UBuffer.create(matrix).flip());
    }

    public void setUniform(String name, int value) {
        glUniform1i(this.uniforms.get(name), value);
    }

    public void setUniform(String name, float value) {
        glUniform1f(this.uniforms.get(name), value);
    }

    public void setUniform(String name, DirectionalLight directionalLight) {
        setUniform(name + ".base", (BaseLight) directionalLight);
        setUniform(name + ".direction", directionalLight.getDirection());
    }

    public void setUniform(String name, BaseLight baseLight) {
        setUniform(name + ".color", baseLight.getColor().getColor());
        setUniform(name + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String name, PointLight pointLight) {
        setUniform(name + ".base", (BaseLight) pointLight);
        setUniform(name + ".attenuation", pointLight.getAttenuation());
        setUniform(name + ".position", pointLight.getTransform().getTransformedPosition());
        setUniform(name + ".range", pointLight.getRange());
    }

    public void setUniform(String name, Attenuation attenuation) {
        setUniform(name + ".constant", attenuation.getConstant());
        setUniform(name + ".linear", attenuation.getLinear());
        setUniform(name + ".exponent", attenuation.getExponent());
    }

    public void setUniform(String name, SpotLight spotLight) {
        setUniform(name + ".pointLight", (PointLight) spotLight);
        setUniform(name + ".direction", spotLight.getDirection());
        setUniform(name + ".cutoff", spotLight.getCutoff());
    }

    private void magicUniforms(GLSLScript script) {

        script = getParser().process(script);

        for (String uName : script.getExpandedUniforms()) {
            LOGGER.trace("{}: uniform {} got added", this.getClass().getSimpleName(), uName);
            addUniform(uName);
        }

    }

    public GLSLParser getParser() {
        return parser;
    }

    public void setParser(GLSLParser parser) {
        this.parser = parser;
    }
}
