package io.github.achtern.AchternEngine.core.rendering.shadow;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.abstractVersion.QuickPassFilter;
import io.github.achtern.AchternEngine.core.rendering.Dimension;
import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.framebuffer.FrameBuffer;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.rendering.shader.ShadowGenerator;
import io.github.achtern.AchternEngine.core.rendering.texture.Format;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.BaseLight;

import static org.lwjgl.opengl.GL11.*;

public class BasicShadowRenderer extends QuickPassFilter implements RenderPass {

    //TMP will be proteceted after testing!
    public FrameBuffer shadowMap;

    @Override
    public void init(RenderEngine renderEngine) {
        shadowMap = new FrameBuffer(new Dimension(1024, 1024));
        shadowMap.setDepthTarget(new Texture(
                new Dimension(1024, 1024),
                GL_NEAREST, GL_NEAREST,
                GL_DEPTH_COMPONENT,
                Format.DEPTH,
                false
        ));

        renderEngine.addTexture("shadowMap", shadowMap.getDepthTarget().getTexture());
    }

    @Override
    public void pre(Node node, RenderPass pass, RenderEngine renderEngine) {

        if (pass instanceof BaseLight) {
            ShadowInfo shadowInfo = ((BaseLight) pass).getShadowInfo();

            if (shadowInfo != null) {
                renderEngine.getDataBinder().bindAsRenderTarget(shadowMap);
                renderEngine.clear(false, true, false);

                Node holder = new Node();

                Camera shadowCamera = new Camera(shadowInfo.getMatrix());
                holder.add(shadowCamera);

                shadowCamera.getTransform().setPosition(
                        ((BaseLight) pass).getTransform().getTransformedPosition()
                );
                shadowCamera.getTransform().setRotation(
                        ((BaseLight) pass).getTransform().getTransformedRotation()
                );

                renderEngine.addMatrix("shadowMatrix", shadowCamera.getViewProjection());

                // Store a copy of the main camera/renderpass
                Camera mainC = renderEngine.getMainCamera();
                RenderPass mainRP = renderEngine.getActiveRenderPass();
                {
                    renderEngine.setMainCamera(shadowCamera);
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
