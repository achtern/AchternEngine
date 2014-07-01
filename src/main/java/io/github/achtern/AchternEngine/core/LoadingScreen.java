package io.github.achtern.AchternEngine.core;

import io.github.achtern.AchternEngine.core.contracts.RenderPass;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Camera;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.Texture;
import io.github.achtern.AchternEngine.core.rendering.mesh.Quad;
import io.github.achtern.AchternEngine.core.rendering.shader.BasicShader;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoadingScreen {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoadingScreen.class);

    public static void show(CoreEngine engine) {
        try {
            show(engine, ResourceLoader.getTexture("loading.v0.0.1.png"));
        } catch (IOException e) {
            LOGGER.error("Error Loading bundled LoadingScreen image.");
        }
    }

    public static void show(CoreEngine engine, Texture loadingImage) {
        if (loadingImage == null) {
            show(engine);
            return;
        }

        // Create the Material
        Material material = new Material();
        material.addTexture("diffuse", loadingImage);
        material.setColor(Color.WHITE);

        // Create a 2 * 2 Quad to display the image
        // 2 * 2, because the screen goes from -1 to 1 on both
        // axis with an identiy projection
        Figure figure = new Figure(new Quad(2, 2), material);

        // The main holder node (replaces the rootNode)
        Node holder = new Node("LoadingScreen");
        // Make sure to inject the engine
        holder.setEngine(engine);

        // The Quad needs its own node
        // because we need to center the image.
        Node quad = new Node("Image").add(figure);
        quad.setEngine(engine);

        // Center it!
        quad.getTransform().getPosition().subLocal(new Vector3f(1f, 1f, 0));

        // Add our identiy Camera to render the image
        holder.add(new Node("Camera").add(new Camera(new Matrix4f().initIdentiy())));
        holder.add(quad);

        // Sync the transforms
        holder.update(0);

        // Store our basic RenderPass in a variable,
        // to allow removing of it afterwards.
        RenderPass basic;

        engine.getRenderEngine().addRenderPass(basic = new RenderPass() {
            @Override
            public Shader getShader() {
                return BasicShader.getInstance();
            }
        });

        // Render the "scene"
        engine.getRenderEngine().render(holder);

        // Sync the image to the Window
        Window.render();

        // Make sure to delete this after render,
        // so the end-user doesn't see it!
        engine.getRenderEngine().removeRenderPass(basic);
        engine.getRenderEngine().addCamera(null);

    }



}
