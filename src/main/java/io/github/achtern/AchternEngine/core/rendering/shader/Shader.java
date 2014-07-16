package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Variable;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.*;
import io.github.achtern.AchternEngine.core.util.UBuffer;
import org.lwjgl.opengl.GL32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private GLSLParser parser = new GLSLParser();

    private GLSLProgram program;

    protected static Map<Class<?>, Method> classMethodMap;

    static {
        classMethodMap = new HashMap<Class<?>, Method>();
        /**
         * To the future developer:
         * I'm very sorry I've wrote the code below.
         * It's in a static block and no error checking.
         * It works an it only costs time during startup.
         * and it WORKS!
         * Feel free to improve it!
         * Regards,
         * Christian
         *
         * This code puts all setUniform methods into the hashmap,
         * with the corresponding type. This is used in #updateUniforms()
         * in order to dynamiclly set uniforms.
         */
        Class c = Shader.class;
        for (Method m : c.getDeclaredMethods()) {
            if (m.getName().equalsIgnoreCase("setUniform")) {
                Class type = m.getParameterTypes()[1];
                classMethodMap.put(type, m);
            }
        }
    }

    /**
     * For subclasses.
     */
    public Shader() {
    }

    public Shader(GLSLProgram program) {
        setup(program);
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
            int type;
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

    public void addUniform(Uniform uniform) {

        int uniformLoc = glGetUniformLocation(this.program.getID(), uniform.getName());

        if (uniformLoc == 0xFFFFFFFF) {
            // Just trace, cause the uniform might be removed by the GLSL compiler, if un-used.
            LOGGER.trace("{}: Could not find uniform location for '{}'",
                    this.getClass().getSimpleName(), uniform.getName());
        }

        uniform.setLocation(uniformLoc);

    }

    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {

//        material.getTexture("diffuse").bind();

        for (Uniform u : this.program.getUniforms()) {
            String n = u.getName(); // Just a quick access to the name!

            // textures aka sampler2Ds
            if (u.getType().equalsIgnoreCase("sampler2D")) {

                // material takes precedence over the renderengine
                if (material.hasTexture(n)) {
                    // Bind it to the sampler slot. this sampler slot comes from the RenderEngine
                    material.getTexture(n).bind(renderEngine.getSamplerSlot(n));

                } else if (renderEngine.hasTexture(n)) {
                    renderEngine.getTexture(n).bind(renderEngine.getSamplerSlot(n));

                } else {
                    LOGGER.warn("{}: texture '{}' not found in material nor RenderEngine.",
                            this.getClass().getSimpleName(), n);
                    // If the texture has not been found, bind the missing texture from
                    // Material
                    material.getTexture(n).bind(renderEngine.getSamplerSlot(n));
                    n = "diffuse"; // Default to diffuse!
                }

                // and set the value of the uniform to the sampler slot
                u.setValue(int.class, renderEngine.getSamplerSlot(n));

                // Now common structs like DirectionalLight, PointLight, SpotLight, AmbientLight
            } else if (u.getType().equalsIgnoreCase("DirectionalLight")) {
                u.setValue(DirectionalLight.class, (DirectionalLight) renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("SpotLight")) {
                u.setValue(SpotLight.class, (SpotLight) renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("PointLight")) {
                u.setValue(PointLight.class, (PointLight) renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("AmbientLight")) {
                u.setValue(AmbientLight.class, (AmbientLight) renderEngine.getActiveRenderPass());


                // other stuff below
                // currently supporting
                // color, MVP, (shadowMatrix=TEMP)
            } else if (n.equalsIgnoreCase("color")) {
                u.setValue(Color.class, material.getColor());

            } else if (n.equalsIgnoreCase("MVP")) {
                u.setValue(Matrix4f.class, projection);

            } else if (n.equalsIgnoreCase("model")) {
                u.setValue(Matrix4f.class, transform.getTransformation());

            } else if (n.equalsIgnoreCase("eyePos")) {
                u.setValue(Vector3f.class, renderEngine.getMainCamera().getTransform().getTransformedPosition());

            } else if (n.equalsIgnoreCase("shadowMatrix")) {
                // TEMPORARAY CODE
                Matrix4f m = new Matrix4f().initIdentiy();
                if (renderEngine.getMatrix("shadowMatrix") != null) {
                    m = renderEngine.getMatrix("shadowMatrix");
                }
                u.setValue(Matrix4f.class, m);

            } else {
                // In the last step we try to find the value in the material,
                // otherwise leave it to the user!
                if (u.getType().equalsIgnoreCase("float") && material.hasFloat(n)) {
                    u.setValue(float.class, material.getFloat(n));
                } else if ((u.getType().equalsIgnoreCase("vec3") && material.hasVector(n))) {
                    u.setValue(Vector3f.class, material.getVector(n));
                } else if ((u.getType().equalsIgnoreCase("mat4") && material.hasMatrix(n))) {
                    u.setValue(Matrix4f.class, material.getMatrix(n));
                } else if ((u.getType().equalsIgnoreCase("vec4") && material.hasColor(n))) {
                    u.setValue(Vector4f.class, material.getColor(n));
                }
            }

            // If the value is null, the developer has to fill it,
            // and has the change override values.
            handle(u, transform, material, renderEngine, projection);

            if (!u.shouldSet()) return;

            if (u.getValue() != null) {
                try {
                    Class<?> type = u.getValueType();
                    classMethodMap.get(type).invoke(this, u.getName(), u.getValue());
                } catch (IllegalAccessException e) {
                    LOGGER.error("Error invoking method on shader " + this + ";", e);
                } catch (InvocationTargetException e) {
                    LOGGER.error("Error invoking method on shader " + this + ";", e);
                } catch (NullPointerException e) {
                    LOGGER.error("Cannot set uniform of type {}.", u.getValueType().getName());
                }
            } else {
                throw new IllegalStateException("Uniform value cannot be null for " + u);
            }
        }
    }

    protected void handle(Uniform uniform, Transform transform, Material material,
                                   RenderEngine renderEngine, Matrix4f projection) {
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
        glUniform3f(program.getExpandedUniform(name).getLocation(), vec.getX(), vec.getY(), vec.getZ());
    }

    public void setUniform(String name, Vector4f vec) {
        glUniform4f(program.getExpandedUniform(name).getLocation(), vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    public void setUniform(String name, Color color) {
        setUniform(name, (Vector4f) color);
    }

    public void setUniform(String name, Vector2f vec) {
        glUniform2f(program.getExpandedUniform(name).getLocation(), vec.getX(), vec.getY());
    }

    public void setUniform(String name, Matrix4f matrix) {
        glUniformMatrix4(program.getExpandedUniform(name).getLocation(), true, (FloatBuffer) UBuffer.create(matrix).flip());
    }

    public void setUniform(String name, int value) {
        glUniform1i(program.getExpandedUniform(name).getLocation(), value);
    }

    public void setUniform(String name, float value) {
        glUniform1f(program.getExpandedUniform(name).getLocation(), value);
    }

    public void setUniform(String name, DirectionalLight directionalLight) {
        setUniform(name + ".base", (BaseLight) directionalLight);
        setUniform(name + ".direction", directionalLight.getDirection());
    }

    public void setUniform(String name, AmbientLight ambientLight) {
        setUniform(name + ".color", ambientLight.getColor());
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

        for (Uniform u : script.getExpandedUniforms()) {
            LOGGER.trace("{}: uniform {} got added", this.getClass().getSimpleName(), u.getName());
            addUniform(u);
        }

        return script;

    }

    private GLSLScript magicAttributes(GLSLScript script) {

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
