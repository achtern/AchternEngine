package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    private GLSLParser parser = new GLSLParser();

    protected GLSLProgram program;

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
        this.program = program;
    }

    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {

        for (Uniform u : this.program.getUniforms()) {
            String n = u.getName(); // Just a quick access to the name!

            // textures aka sampler2Ds
            if (u.getType().equalsIgnoreCase("sampler2D")) {

                // material takes precedence over the renderengine
                if (material.hasTexture(n)) {
                    // Bind it to the sampler slot. this sampler slot comes from the RenderEngine
                    renderEngine.getDataBinder().bind(material.getTexture(n), renderEngine.getSamplerSlot(n));

                } else if (renderEngine.hasTexture(n)) {
                    renderEngine.getDataBinder().bind(renderEngine.getTexture(n), renderEngine.getSamplerSlot(n));

                } else {
                    LOGGER.warn("{}: texture '{}' not found in material nor RenderEngine.",
                            this.getClass().getSimpleName(), n);
                    // If the texture has not been found, bind the missing texture from
                    // Material
                    renderEngine.getDataBinder().bind(material.getTexture(n), renderEngine.getSamplerSlot(n));
                    n = "diffuse"; // Default to diffuse!
                }

                // and set the value of the uniform to the sampler slot
                u.setValue(renderEngine.getSamplerSlot(n));

                // Now common structs like DirectionalLight, PointLight, SpotLight, AmbientLight
            } else if (u.getType().equalsIgnoreCase("DirectionalLight")) {
                u.setValue(renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("SpotLight")) {
                u.setValue(renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("PointLight")) {
                u.setValue(renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("AmbientLight")) {
                u.setValue(renderEngine.getActiveRenderPass());


                // other stuff below
                // currently supporting
                // color, MVP, (shadowMatrix=TEMP)
            } else if (n.equalsIgnoreCase("color")) {
                u.setValue(material.getColor());

            } else if (n.equalsIgnoreCase("MVP")) {
                u.setValue(projection);

            } else if (n.equalsIgnoreCase("model")) {
                u.setValue(transform.getTransformation());

            } else if (n.equalsIgnoreCase("eyePos")) {
                u.setValue(renderEngine.getMainCamera().getTransform().getTransformedPosition());

            } else if (n.equalsIgnoreCase("shadowMatrix")) {
                // TEMPORARAY CODE
                Matrix4f m = new Matrix4f().initIdentiy();
                if (renderEngine.getMatrix("shadowMatrix") != null) {
                    m = renderEngine.getMatrix("shadowMatrix");
                }
                u.setValue(m);

            } else {
                // In the last step we try to find the value in the material,
                // otherwise leave it to the user!
                if (u.getType().equalsIgnoreCase("float") && material.hasFloat(n)) {
                    u.setValue(material.getFloat(n));
                } else if ((u.getType().equalsIgnoreCase("vec3") && material.hasVector(n))) {
                    u.setValue(material.getVector(n));
                } else if ((u.getType().equalsIgnoreCase("mat4") && material.hasMatrix(n))) {
                    u.setValue(material.getMatrix(n));
                } else if ((u.getType().equalsIgnoreCase("vec4") && material.hasColor(n))) {
                    u.setValue(material.getColor(n));
                }
            }

            // If the value is null, the developer has to fill it,
            // and has the change override values.
            handle(u, transform, material, renderEngine, projection);

            if (!u.shouldSet()) return;

            if (u.getValue() != null) {
                renderEngine.getDataBinder().getUniformManager().setUniform(this, u);
            } else {
                throw new IllegalStateException("Uniform value cannot be null for " + u);
            }
        }
    }

    protected void handle(Uniform uniform, Transform transform, Material material,
                                   RenderEngine renderEngine, Matrix4f projection) {
    }

    public GLSLProgram getProgram() {
        return program;
    }

    public GLSLParser getParser() {
        return parser;
    }

    public void setParser(GLSLParser parser) {
        this.parser = parser;
    }
}
