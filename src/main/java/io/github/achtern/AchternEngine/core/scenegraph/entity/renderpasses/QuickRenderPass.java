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

package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses;

import io.github.achtern.AchternEngine.core.CoreEngine;
import io.github.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import io.github.achtern.AchternEngine.core.rendering.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;

public class QuickRenderPass extends QuickEntity implements RenderPass {

    private Shader shader;

    @Override
    public void setEngine(CoreEngine engine) {
        if (engine != null && engine != getEngine()) {
            super.setEngine(engine);
            engine.getRenderEngine().addRenderPass(this);
        } else if (engine == null) {
            super.setEngine(null);
        }
    }

    @Override
    public void removed() {
        getEngine().getRenderEngine().removeRenderPass(this);
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

}
