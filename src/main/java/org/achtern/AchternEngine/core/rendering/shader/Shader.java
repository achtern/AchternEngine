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

package org.achtern.AchternEngine.core.rendering.shader;

import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.rendering.Material;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.rendering.fog.Fog;
import org.achtern.AchternEngine.core.rendering.mesh.MeshData;
import org.achtern.AchternEngine.core.resource.fileparser.GLSLParser;
import org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.achtern.AchternEngine.core.scenegraph.entity.GlobalEntity;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Shader wraps a {@link org.achtern.AchternEngine.core.resource.fileparser.GLSLProgram},
 *  this class is meant to be extended by all shader one might write.
 *  This allows the sub-classes to load the GLSLProgram and inject it all by themselves.
 * <br />
 * This Shader class (abstract) also handles uniform setting aka getting the correct values from the
 *  {@link org.achtern.AchternEngine.core.rendering.Material}, {@link org.achtern.AchternEngine.core.rendering.RenderEngine}
 *  and {@link org.achtern.AchternEngine.core.scenegraph.Node}. The actual sending of the data to the GPU is getting
 *  handled by the implementation.
 */
public abstract class Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    @Getter @Setter private GLSLParser parser = new GLSLParser();

    @Getter protected GLSLProgram program;

    /**
     * For subclasses.
     */
    public Shader() {
    }

    public Shader(GLSLProgram program) {
        this.program = program;
    }

    public void updateUniforms(RenderEngine renderEngine, Figure figure) {

        Material material = figure.getMaterial();
        Transform transform = figure.getTransform();

        Matrix4f worldMat = transform.getTransformation();
        Matrix4f projection = renderEngine.getCamera().getViewProjection().mul(worldMat);

        // Keeps track of the current SamplerSlot in use.
        int samplerSlot = 0;

        for (Uniform u : this.program.getUniforms()) {
            String n = u.getName(); // Just a quick access to the name!

            // textures aka sampler2Ds
            if (u.getType().equalsIgnoreCase("sampler2D")) {

                // material takes precedence over the renderengine
                if (material.hasTexture(n)) {
                    // Bind it to the sampler slot. this sampler slot comes from the RenderEngine
                    renderEngine.getDataBinder().bind(material.getTexture(n), samplerSlot);

                } else if (renderEngine.hasTexture(n)) {
                    renderEngine.getDataBinder().bind(renderEngine.getTexture(n), samplerSlot);
                } else if (!figure.getMesh().getData().getMode().equals(MeshData.Mode.TRIANGLES)) {
                    // we do not need a texture anyway.
                    u.setShouldSet(false);
                } else {
                    LOGGER.warn("{}: texture '{}' not found in material nor RenderEngine.",
                            this.getClass().getSimpleName(), n);
                    // If the texture has not been found, set the missing texture from
                    // Material
                    renderEngine.getDataBinder().bind(material.getTexture(n), samplerSlot);
                    n = "diffuse"; // Default to diffuse!
                }

                // and set the value of the uniform to the sampler slot
                u.setValue(samplerSlot);
                samplerSlot++;

                // Now common structs like DirectionalLight, PointLight, SpotLight, AmbientLight
            } else if (u.getType().equalsIgnoreCase("DirectionalLight") ||
                    u.getType().equalsIgnoreCase("SpotLight") ||
                    u.getType().equalsIgnoreCase("PointLight") ||
                    u.getType().equalsIgnoreCase("AmbientLight"))
            {
                u.setValue(renderEngine.getActiveRenderPass());

            } else if (u.getType().equalsIgnoreCase("Fog")) {

                GlobalEntity<Fog> gE = renderEngine.getGlobal(Fog.class);
                if (gE != null) {
                    u.setValue(gE.getObject());
                } else {
                    // use disabled fog
                    u.setValue(Fog.DISABLED);
                }

                // other stuff below
                // currently supporting
                // color, MVP, (shadowMatrix=TEMP)
            } else if (n.equalsIgnoreCase("color")) {
                u.setValue(material.getColor());

            } else if (n.equalsIgnoreCase("MVP")) {
                u.setValue(projection);

            } else if (n.equalsIgnoreCase("model")) {
                u.setValue(transform.getTransformation());

            } else if (n.equalsIgnoreCase("modelView")) {
                u.setValue(renderEngine.getCamera().getView().mul(transform.getTransformation()));

            } else if (n.equalsIgnoreCase("eyePos")) {
                u.setValue(renderEngine.getCamera().getTransform().getTransformedPosition());

            } else if (n.equalsIgnoreCase("shadowMatrix")) {
                Matrix4f m = new Matrix4f().initIdentiy();
                if (renderEngine.getMatrix("shadowMatrix") != null) {
                    m = renderEngine.getMatrix("shadowMatrix").mul(transform.getTransformation());
                }
                u.setValue(m);

            } else {
                // In the last step we try to find the value in the material,
                // otherwise leave it to the user!
                if (u.getType().equalsIgnoreCase("float")) {
                    u.setValue(material.getFloat(n));
                } else if ((u.getType().equalsIgnoreCase("vec3") && material.hasVector(n))) {
                    u.setValue(material.getVector(n));
                } else if ((u.getType().equalsIgnoreCase("mat4") && material.hasMatrix(n))) {
                    u.setValue(material.getMatrix(n));
                } else if ((u.getType().equalsIgnoreCase("vec4") && material.hasColor(n))) {
                    u.setValue(material.getColor(n));
                } else if (u.getType().equalsIgnoreCase("int")) {
                    u.setValue(material.getInteger(n));
                }
            }

            // If the value is null, the developer has to fill it,
            // and has the change override values.
            handle(u, transform, material, renderEngine, projection);

            // Finally set it.
            renderEngine.getDataBinder().getUniformManager().setUniform(this, u);
        }
    }

    protected void handle(Uniform uniform, Transform transform, Material material,
                          RenderEngine renderEngine, Matrix4f projection) {
    }
}
