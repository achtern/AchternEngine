package io.github.achtern.AchternEngine.core.rendering.shader;

import io.github.achtern.AchternEngine.core.RenderEngine;
import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.DirectionalLight;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.PointLight;
import io.github.achtern.AchternEngine.core.entity.renderpasses.light.SpotLight;
import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PhongShader extends Shader {

    public static final Logger LOGGER = LoggerFactory.getLogger(BasicShader.class);

    public static final int MAX_POINT_LIGHTS = 4;
    public static final int MAX_SPOT_LIGHTS = 4;

    private static final PhongShader instance = new PhongShader();

    public static PhongShader getInstance() {
        return instance;
    }

    private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
    private static DirectionalLight directionalLight = new DirectionalLight(new Color(1, 1, 1, 1), 0);
    private static PointLight[] pointLights = new PointLight[] {};
    private static SpotLight[] spotLights = new SpotLight[] {};

    private PhongShader() {
        super();

        try {
            addVertexShader(ResourceLoader.getShader("phongVertex.gvs"));
            addFragmentShader(ResourceLoader.getShader("phongFragment.gfs"));
        } catch (IOException e) {
            LOGGER.warn("Error Loading Bundled Phong Shader GLSL files.", e);
        }
        compile();

        addUniform("transform");
        addUniform("transformProjected");
        addUniform("baseColor");
        addUniform("ambientLight");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            addUniform("pointLights[" + i + "].base.color");
            addUniform("pointLights[" + i + "].base.intensity");
            addUniform("pointLights[" + i + "].attenuation.constant");
            addUniform("pointLights[" + i + "].attenuation.linear");
            addUniform("pointLights[" + i + "].attenuation.exponent");
            addUniform("pointLights[" + i + "].position");
            addUniform("pointLights[" + i + "].range");
        }

        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            addUniform("spotLights[" + i + "].pointLight.base.color");
            addUniform("spotLights[" + i + "].pointLight.base.intensity");
            addUniform("spotLights[" + i + "].pointLight.attenuation.constant");
            addUniform("spotLights[" + i + "].pointLight.attenuation.linear");
            addUniform("spotLights[" + i + "].pointLight.attenuation.exponent");
            addUniform("spotLights[" + i + "].pointLight.position");
            addUniform("spotLights[" + i + "].pointLight.range");
            addUniform("spotLights[" + i + "].direction");
            addUniform("spotLights[" + i + "].cutoff");
        }
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderEngine renderEngine) {

        super.updateUniforms(transform, material, renderEngine);

        Matrix4f worldMat = transform.getTransformation();
        Matrix4f projectedMat = renderEngine.getMainCamera().getViewProjection().mul(worldMat);

        setUniform("transform", worldMat);
        setUniform("transformProjected", projectedMat);
        setUniform("baseColor", material.getVector("color"));
        setUniform("ambientLight", ambientLight);

        setUniform("specularIntensity", material.getFloat("specularIntensity"));
        setUniform("specularPower", material.getFloat("specularPower"));
        setUniform("eyePos", renderEngine.getMainCamera().getTransform().getPosition());

        setUniform("directionalLight", directionalLight);

        for (int i = 0; i < pointLights.length; i++) {
            setUniform("pointLights[" + i + "]", pointLights[i]);
        }

        for (int i = 0; i < spotLights.length; i++) {
            setUniform("spotLights[" + i + "]", spotLights[i]);
        }


    }

    public static void setPointLights(PointLight[] pointLights) {
        if (pointLights.length > MAX_POINT_LIGHTS) {
            LOGGER.warn("Max Number of Point Lights reached. Not setting anything. Max is: " +  MAX_POINT_LIGHTS + "; Got: " + pointLights.length);
            return;
        }
        PhongShader.pointLights = pointLights;
    }

    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_SPOT_LIGHTS) {
            LOGGER.warn("Max Number of Spot Lights reached. Not setting anything. Max is: " +  MAX_SPOT_LIGHTS + "; Got: " + spotLights.length);
            return;
        }
        PhongShader.spotLights = spotLights;
    }

    public static DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }
}
