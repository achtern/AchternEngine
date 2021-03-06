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
import org.achtern.AchternEngine.core.resource.ResourceLoader;
import org.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShadowGenerator extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(ShadowGenerator.class);

    private static final ShadowGenerator instance = new ShadowGenerator();

    public static ShadowGenerator getInstance() {
        return instance;
    }

    private ShadowGenerator() {
        try {
            this.program = ResourceLoader.getShaderProgram("shadow.basic");
        } catch (Exception e) {
            LOGGER.warn("Error Loading Bundled Basic Shadow Shader GLSL files.", e);
        }

    }

    @Override
    protected void handle(Uniform uniform, Transform transform, Material material, RenderEngine renderEngine, Matrix4f projection) {
    }


}
