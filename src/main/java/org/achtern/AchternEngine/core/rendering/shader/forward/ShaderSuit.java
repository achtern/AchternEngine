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

package org.achtern.AchternEngine.core.rendering.shader.forward;

import org.achtern.AchternEngine.core.rendering.RenderPass;
import org.achtern.AchternEngine.core.rendering.shader.Shader;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight;

import java.util.HashMap;
import java.util.Map;

/**
 * A ShaderSuit holds a collection of {@link org.achtern.AchternEngine.core.rendering.shader.Shader}s to render
 *  for all (or a subset of) Light-types.
 *  @see org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.Light
 *
 * This adds a default Shader for {@link org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.AmbientLight}
 */
public abstract class ShaderSuit {

    protected Map<Class<? extends RenderPass>, Shader> shaders;

    protected ShaderSuit() {
        this(new HashMap<Class<? extends RenderPass>, Shader>());
    }

    protected ShaderSuit(Map<Class<? extends RenderPass>, Shader> shaders) {
        this.shaders = shaders;

        setShaderFor(AmbientLight.class, Ambient.getInstance());
    }

    /**
     * Returns the Shader for the given type.
     * Throws a NullPointerException, if no shader exists for the given LightType
     * @param light Light to retrieve Shader based on
     * @return a shader (cannot be null)
     */
    public Shader getFor(Class<? extends RenderPass> light) throws NullPointerException {

        Shader s = shaders.get(light);

        if (s == null) {
            throw new NullPointerException("No shader for Light {" + light.toString() + "}");
        }

        return s;
    }

    public void setShaderFor(Class<? extends RenderPass> type, Shader shader) {
        this.shaders.put(type, shader);
    }

}
