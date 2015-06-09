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

package org.achtern.AchternEngine.core.rendering.binding;

import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Vector2f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.math.Vector4f;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.fog.Fog;
import org.achtern.AchternEngine.core.rendering.light.Attenuation;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.*;

/**
 * The UniformManager is responsible for adding, registering and setting uniform and it's data.
 */
public interface UniformManager {

    /**
     * This should register the uniform to the given shader on the graphics card
     * @param shader assoc. shader
     * @param uniform uniform to register
     */
    public void registerUniform(Shader shader, Uniform uniform);

    /**
     * This method will call
     *  {@link #registerUniform(org.achtern.AchternEngine.core.rendering.shader.Shader, org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform)}
     *  for all uniforms in the Shader.
     * @param shader shader to add all uniforms
     */
    public void addUniforms(Shader shader);

    /**
     * This will determine if the uniform should be set and if and only if
     *  will determine the type of value and set the data accordingly.
     * @param shader assoc. shader
     * @param uniform uniform to set its data
     */
    public void setUniform(Shader shader, Uniform uniform);

    public void setUniform(Shader shader, String name, Vector3f vec);

    public void setUniform(Shader shader, String name, Vector4f vec);

    public void setUniform(Shader shader, String name, Color color);

    public void setUniform(Shader shader, String name, Vector2f vec);

    public void setUniform(Shader shader, String name, Matrix4f matrix);

    public void setUniform(Shader shader, String name, int value);

    public void setUniform(Shader shader, String name, float value);

    public void setUniform(Shader shader, String name, double value);

    public void setUniform(Shader shader, String name, Fog fog);

    public void setUniform(Shader shader, String name, DirectionalLight directionalLight);

    public void setUniform(Shader shader, String name, AmbientLight ambientLight);

    public void setUniform(Shader shader, String name, BaseLight baseLight);

    public void setUniform(Shader shader, String name, PointLight pointLight);

    public void setUniform(Shader shader, String name, Attenuation attenuation);

    public void setUniform(Shader shader, String name, SpotLight spotLight);


}
