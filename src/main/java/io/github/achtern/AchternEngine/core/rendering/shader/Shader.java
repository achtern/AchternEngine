package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.BaseLight;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.DirectionalLight;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.PointLight;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.SpotLight;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.lwjgl.opengl.GL32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private GLSLParser parser = new GLSLParser();

    private GLSLScript vertexShader;
    private GLSLScript geometryShader;
    private GLSLScript fragmentShader;

    private GLSLProgram program;

    private HashMap<String, Integer> uniforms;

    public Shader(GLSLProgram program) {
        this();
        setup(program);
    }

    public Shader() {
        this.uniforms = new HashMap<String, Integer>();
    }

    public void setup(GLSLProgram program) {
        this.program = program;
        this.program.setID(glCreateProgram());

        if (this.program.getID() == 0) {
            LOGGER.error("Shader Program creation failed. '0' is not a valid memory address.");
        }

        init();
        compile();
        addUniforms();
    }

    protected void init() {
        for (GLSLScript script : program.getScripts()) {
            int type = 0;
            switch (script.getType()) {
                case VERTEX_SHADER:
                    type = GL_VERTEX_SHADER;
                    break;
                case FRAGMENT_SHADER:
                    type = GL_FRAGMENT_SHADER;
                    break;
                case GEOMETRY_SHADER:
                    type = GL32.GL_GEOMETRY_SHADER;
                    break;
                default:
                    throw new UnsupportedOperationException("Shader type not supported.");
            }

            this.addProgram(script.getSource(), type);
        }
    }

    @Deprecated
    public void addVertexShader(String text) {
        vertexShader = new GLSLScript(this.getClass().getSimpleName(), GLSLScript.Type.VERTEX_SHADER);
        vertexShader.setSource(text);
        this.addProgram(text, GL_VERTEX_SHADER);
    }

    @Deprecated
    public void addFragmentShader(String text) {
        vertexShader = new GLSLScript(this.getClass().getSimpleName(), GLSLScript.Type.FRAGMENT_SHADER);
        vertexShader.setSource(text);
        this.addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void bind() {
        glUseProgram(this.program.getID());
    }

    public void addUniforms() {
        for (GLSLScript s : program.getScripts()) {
          magicUniforms(s);
        }
    }

    public void addAttributes() {
        for (GLSLScript s : program.getScripts()) {
            magicAttributes(s);
        }
    }

    public void addUniform(String name) {

        int uniformLoc = glGetUniformLocation(this.program.getID(), name);

        if (uniformLoc == 0xFFFFFFFF) {
            LOGGER.warn("Could not find uniform location for '{}'", name);
        }

        this.uniforms.put(name, uniformLoc);

    }

    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
        material.getTexture("diffuse").bind();

        int numTex = 0;
        for (Variable v : program.getFragment().getUniforms()) {
            String name = v.getName();
            if (v.getType().equalsIgnoreCase("sampler2D") && !name.equalsIgnoreCase("diffuse")) {
                setUniform(v.getName(), numTex);

                int activeTexture;
                switch (numTex) {
                    case 0:
                        activeTexture = GL_TEXTURE0;
                        break;
                    case 1:
                        activeTexture = GL_TEXTURE1;
                        break;
                    case 2:
                        activeTexture = GL_TEXTURE2;
                        break;
                    case 3:
                        activeTexture = GL_TEXTURE3;
                        break;
                    case 4:
                        activeTexture = GL_TEXTURE4;
                        break;
                    default:
                        throw new UnsupportedOperationException(
                                "This engine doesn't allow for this " + numTex + " sampler slots!"
                        );
                }
                glActiveTexture(activeTexture);
                renderEngine.getTexture(name).bind();

                numTex++;
            }
        }

        if (program.getFragment().getExpandedUniforms().contains("color")) {
            setUniform("color", material.getColor());
        }

        if (program.getFragment().getExpandedUniforms().contains("shadowMatrix")) {
            setUniform("shadowMatrix", renderEngine.getShadowMatrix());
        }

        if (program.getFragment().getExpandedUniforms().contains("MVP")) {
            setUniform("MVP", projection);
        }
    }

    public void compile() {
        int id = this.program.getID();
        glLinkProgram(id);

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            LOGGER.warn("Link Status: {} @ {}", glGetProgramInfoLog(id, 1024), this.getClass().getSimpleName());
        }

        glValidateProgram(id);

        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            String error = glGetProgramInfoLog(this.program.getID(), 1024);
            // This is a hack to prevent error message on every shader load.
            // If we load the shaders before the mesh loading, there are not any
            // VAO loaded. Works fine everytime, so just ignore this case specific error...
            if (!error.contains("Validation Failed: No vertex array object bound")) {
                LOGGER.warn("Validation Status: {} @ {}", error, this.getClass().getSimpleName());
            }
        }
    }

    /**
     * Binds the Attribute Location (as specified in the GLSL shader sources)
     * @param name The name of the attribute
     * @param location The location id
     */
    public void setAttribLocation(String name, int location) {
        glBindAttribLocation(this.program.getID(), location, name);
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

        glAttachShader(this.program.getID(), shader);
    }

    public void setUniform(String name, Vector3f vec) {
        glUniform3f(this.uniforms.get(name), vec.getX(), vec.getY(), vec.getZ());
    }

    public void setUniform(String name, Vector4f vec) {
        glUniform4f(this.uniforms.get(name), vec.getX(), vec.getY(), vec.getZ(), vec.getW());
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

    private GLSLScript magicUniforms(GLSLScript script) {

        if (!script.isProcessed()) {
            script = getParser().process(script);
        }

        for (String uName : script.getExpandedUniforms()) {
            LOGGER.trace("{}: uniform {} got added", this.getClass().getSimpleName(), uName);
            addUniform(uName);
        }

        return script;

    }

    private GLSLScript magicAttributes(GLSLScript script) {

        if (!script.isProcessed()) {
            script = getParser().process(script);
        }

        int loc = 0;

        for (Variable aName : script.getAttributes()) {
            LOGGER.trace("{}: attribute {} got added at {}", this.getClass().getSimpleName(), aName.getName(), loc);
            setAttribLocation(aName.getName(), loc);
            loc++;
        }

        return script;

    }

    public GLSLParser getParser() {
        return parser;
    }

    public void setParser(GLSLParser parser) {
        this.parser = parser;
    }
}
