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

package io.github.achtern.AchternEngine.core.rendering.shadow;

import io.github.achtern.AchternEngine.core.rendering.RenderPass;
import io.github.achtern.AchternEngine.core.rendering.QuickPassFilter;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.shader.ShadowGenerator;
import io.github.achtern.AchternEngine.core.rendering.texture.Filter;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.InternalFormat;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.BaseLight;

public class BasicShadowRenderer extends QuickPassFilter implements RenderPass {

    protected FrameBuffer shadowMap;

    protected Camera camera;

    protected Matrix4f bias;

    @Override
    public void init(RenderEngine renderEngine) {
        shadowMap = new FrameBuffer(new Dimension(1024, 1024));
        shadowMap.setDepthTarget(new Texture(
                new Dimension(1024, 1024),
                Filter.NEAREST, Filter.NEAREST,
                InternalFormat.DEPTH_COMPONENT,
                Format.DEPTH,
                false
        ));

        renderEngine.addTexture("shadowMap", shadowMap.getDepthTarget().getTexture());

        Node holder = new Node();
        camera = new Camera();
        holder.add(camera);

        bias = new Matrix4f().initScale(0.5f, 0.5f, 0.5f);
        bias = bias.mul(new Matrix4f().initTranslation(1, 1, 1));

    }

    @Override
    public void pre(Node node, RenderPass pass, RenderEngine renderEngine) {

        if (pass instanceof BaseLight) {
            ShadowInfo shadowInfo = ((BaseLight) pass).getShadowInfo();

            if (shadowInfo != null) {

                renderEngine.getDataBinder().bindAsRenderTarget(shadowMap);

                renderEngine.getState().clear(false, true, false);

                camera.setProjection(shadowInfo.getMatrix());

                camera.getTransform().setPosition(
                        ((BaseLight) pass).getTransform().getTransformedPosition()
                );
                camera.getTransform().setRotation(
                        ((BaseLight) pass).getTransform().getTransformedRotation()
                );

                camera.getTransform().rotate(camera.getTransform().getRotation().getUp(), 180);

                renderEngine.addMatrix("shadowMatrix", bias.mul(camera.getViewProjection()));

                // Store a copy of the main camera/renderpass
                Camera mainC = renderEngine.getMainCamera();
                RenderPass mainRP = renderEngine.getActiveRenderPass();
                {
                    renderEngine.setMainCamera(camera);
                    renderEngine.setActiveRenderPass(this);
                    renderEngine.getDataBinder().bind(ShadowGenerator.getInstance());
                    node.render(renderEngine);
                }
                // Reset it
                renderEngine.setMainCamera(mainC);
                renderEngine.setActiveRenderPass(mainRP);


            }

            renderEngine.getRenderTarget().bindAsRenderTarget(renderEngine.getDataBinder());

        }
    }

    /**
     * Returns the shader to get set on draw.
     *
     * @return the shader
     */
    @Override
    public Shader getShader() {
        return ShadowGenerator.getInstance();
    }
}
