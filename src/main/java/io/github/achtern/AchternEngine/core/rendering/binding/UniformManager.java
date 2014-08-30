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

package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.fog.Fog;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.*;

public interface UniformManager {

    public void registerUniform(Shader shader, Uniform uniform);

    public void addUniforms(Shader shader);


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
