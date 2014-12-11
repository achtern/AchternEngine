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

package org.achtern.AchternEngine.lwjgl.rendering.binding;

import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.math.Vector4f;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.binding.UniformManager;
import org.achtern.AchternEngine.core.rendering.fog.Fog;
import org.achtern.AchternEngine.core.rendering.light.Attenuation;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.GLSLScript;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.*;
import org.achtern.AchternEngine.core.util.UBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class LWJGLUniformManager implements UniformManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(LWJGLUniformManager.class);

    @Override
    public void registerUniform(Shader shader, Uniform uniform) {
        int uniformLoc = glGetUniformLocation(shader.getProgram().getID(), uniform.getName());

        if (uniformLoc == 0xFFFFFFFF) {
            // Just trace, cause the uniform might be removed by the GLSL compiler, if un-used.
            LOGGER.trace("{}: Could not find uniform location for '{}'",
                    shader.getClass().getSimpleName(), uniform.getName());
        }

        uniform.setLocation(uniformLoc);
    }

    @Override
    public void addUniforms(Shader shader) {
        for (GLSLScript script : shader.getProgram().getScripts()) {
            for (Uniform u : script.getExpandedUniforms()) {
                LOGGER.trace("{}: uniform {} got added", this.getClass().getSimpleName(), u.getName());
                registerUniform(shader, u);
            }
        }
    }

    @Override
    public void setUniform(Shader shader, Uniform uniform) {
        if (!uniform.shouldSet()) {
            return;
        }

        boolean primitive = uniform.getValue() instanceof Float ||
                            uniform.getValue() instanceof Integer ||
                            uniform.getValue() instanceof Double;

        if (!primitive && uniform.getValue() == null) {
            throw new IllegalStateException("Uniform value cannot be null for " + uniform);
        }

        if (uniform.getValue() instanceof Vector3f) {
            setUniform(shader, uniform.getName(), (Vector3f) uniform.getValue());
        } else if (uniform.getValue() instanceof Color) {
            setUniform(shader, uniform.getName(), (Color) uniform.getValue());
        } else if (uniform.getValue() instanceof Vector4f) {
            setUniform(shader, uniform.getName(), (Vector4f) uniform.getValue());
        } else if (uniform.getValue() instanceof Vector2f) {
            setUniform(shader, uniform.getName(), (Vector2f) uniform.getValue());
        } else if (uniform.getValue() instanceof Matrix4f) {
            setUniform(shader, uniform.getName(), (Matrix4f) uniform.getValue());
        } else if (uniform.getValue() instanceof Integer) {
            setUniform(shader, uniform.getName(), (Integer) uniform.getValue());
        } else if (uniform.getValue() instanceof Float) {
            setUniform(shader, uniform.getName(), (Float) uniform.getValue());
        } else if (uniform.getValue() instanceof Double) {
            setUniform(shader, uniform.getName(), (Double) uniform.getValue());
        } else if (uniform.getValue() instanceof DirectionalLight) {
            setUniform(shader, uniform.getName(), (DirectionalLight) uniform.getValue());
        } else if (uniform.getValue() instanceof AmbientLight) {
            setUniform(shader, uniform.getName(), (AmbientLight) uniform.getValue());
        } else if (uniform.getValue() instanceof SpotLight) {
            setUniform(shader, uniform.getName(), (SpotLight) uniform.getValue());
        } else if (uniform.getValue() instanceof PointLight) {
            setUniform(shader, uniform.getName(), (PointLight) uniform.getValue());
        } else if (uniform.getValue() instanceof BaseLight) {
            setUniform(shader, uniform.getName(), (BaseLight) uniform.getValue());
        } else if (uniform.getValue() instanceof Attenuation) {
            setUniform(shader, uniform.getName(), (Attenuation) uniform.getValue());
        } else if (uniform.getValue() instanceof Fog) {
            setUniform(shader, uniform.getName(), (Fog) uniform.getValue());
        } else {

            Uniform.SetStrategy strategy = uniform.getSetStrategy();
            if (strategy == null) {
                throw new UnsupportedOperationException("Cannot set uniform and no SetStrategy provided");
            }

            strategy.set(uniform, this);
        }
    }

    @Override
    public void setUniform(Shader shader, String name, Vector3f vec) {
        glUniform3f(shader.getProgram().getExpandedUniform(name).getLocation(), vec.getX(), vec.getY(), vec.getZ());
    }

    @Override
    public void setUniform(Shader shader, String name, Vector4f vec) {
        glUniform4f(shader.getProgram().getExpandedUniform(name).getLocation(), vec.getX(), vec.getY(), vec.getZ(), vec.getW());
    }

    @Override
    public void setUniform(Shader shader, String name, Color color) {
        setUniform(shader, name, (Vector4f) color);
    }

    @Override
    public void setUniform(Shader shader, String name, Vector2f vec) {
        glUniform2f(shader.getProgram().getExpandedUniform(name).getLocation(), vec.getX(), vec.getY());
    }

    @Override
    public void setUniform(Shader shader, String name, Matrix4f matrix) {
        glUniformMatrix4(shader.getProgram().getExpandedUniform(name).getLocation(), true, (FloatBuffer) UBuffer.create(matrix).flip());
    }

    @Override
    public void setUniform(Shader shader, String name, int value) {
        glUniform1i(shader.getProgram().getExpandedUniform(name).getLocation(), value);
    }

    @Override
    public void setUniform(Shader shader, String name, float value) {
        glUniform1f(shader.getProgram().getExpandedUniform(name).getLocation(), value);
    }

    @Override
    public void setUniform(Shader shader, String name, double value) {
        setUniform(shader, name, (float) value);
    }

    @Override
    public void setUniform(Shader shader, String name, Fog fog) {
        setUniform(shader, name + ".mode", fog.getMode().getID());
        if (fog.getMode().equals(Fog.Mode.DISABLED)) {
            return;
        }

        setUniform(shader, name + ".color", fog.getColor());
        if (fog.getMode().equals(Fog.Mode.LINEAR)) {
            setUniform(shader, name + ".start", fog.getRange().getX());
            setUniform(shader, name + ".end", fog.getRange().getY());
        } else {
            setUniform(shader, name + ".density", fog.getDensity());
        }
    }

    @Override
    public void setUniform(Shader shader, String name, DirectionalLight directionalLight) {
        setUniform(shader, name + ".base", (BaseLight) directionalLight);
        setUniform(shader, name + ".direction", directionalLight.getDirection());
    }

    @Override
    public void setUniform(Shader shader, String name, AmbientLight ambientLight) {
        setUniform(shader, name + ".color", ambientLight.getColor());
    }

    @Override
    public void setUniform(Shader shader, String name, BaseLight baseLight) {
        setUniform(shader, name + ".color", baseLight.getColor().getColor());
        setUniform(shader, name + ".intensity", baseLight.getIntensity());
    }

    @Override
    public void setUniform(Shader shader, String name, PointLight pointLight) {
        setUniform(shader, name + ".base", (BaseLight) pointLight);
        setUniform(shader, name + ".attenuation", pointLight.getAttenuation());
        setUniform(shader, name + ".position", pointLight.getTransform().getTransformedPosition());
        setUniform(shader, name + ".range", pointLight.getRange());
    }

    @Override
    public void setUniform(Shader shader, String name, Attenuation attenuation) {
        setUniform(shader, name + ".constant", attenuation.getConstant());
        setUniform(shader, name + ".linear", attenuation.getLinear());
        setUniform(shader, name + ".exponent", attenuation.getExponent());
    }

    @Override
    public void setUniform(Shader shader, String name, SpotLight spotLight) {
        setUniform(shader, name + ".pointLight", (PointLight) spotLight);
        setUniform(shader, name + ".direction", spotLight.getDirection());
        setUniform(shader, name + ".cutoff", spotLight.getCutoff());
    }
}
