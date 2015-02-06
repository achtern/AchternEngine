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

package org.achtern.AchternEngine.core.rendering.shader.forward.suits.phong;

import org.achtern.AchternEngine.core.rendering.shader.forward.ShaderSuit;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.DirectionalLight;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.PointLight;
import org.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.SpotLight;

/**
 * Holds Shaders for the Phong lighting model,
 * currently supporting:
 * * Directional Lights
 * * Point Lights
 * * Spot Lights
 *
 * Use {@link #get()} to get the instance!
 */
public class PhongShaderSuit extends ShaderSuit {

    private static final PhongShaderSuit instance = new PhongShaderSuit();

    public static PhongShaderSuit get() { return instance; }

    private PhongShaderSuit() {
        super();

        setShaderFor(DirectionalLight.class, PhongDirectional.getInstance());
        setShaderFor(PointLight.class, PhongPoint.getInstance());
        setShaderFor(SpotLight.class, PhongSpot.getInstance());
    }
}
