package io.github.achtern.AchternEngine.core.rendering.shadow;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.contracts.abstractVersion.QuickPassFilter;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
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

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT16;

public class BasicShadowRenderer extends QuickPassFilter implements RenderPass {

    protected FrameBuffer shadowMap;

    protected Camera camera;

    protected Matrix4f bias;

    @Override
    public void init(RenderEngine renderEngine) {
        shadowMap = new FrameBuffer(new Dimension(1024, 1024));
        shadowMap.setDepthTarget(new Texture(
                new Dimension(1024, 1024),
                GL_NEAREST, GL_NEAREST,
                GL_DEPTH_COMPONENT16,
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

                renderEngine.clear(false, true, false);

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
