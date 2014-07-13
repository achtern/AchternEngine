package io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.shader.forward.Directional;
import io.github.achtern.AchternEngine.core.rendering.shadow.ShadowInfo;

public class DirectionalLight extends BaseLight {

    public DirectionalLight(Color color, float intensity) {
        super(color, intensity);

        setShader(Directional.getInstance());

        setShadowInfo(new ShadowInfo(new Matrix4f().initOrthographic(-40, 40, -40, 40, -40, 40)));
    }

    public Vector3f getDirection() {
        return getTransform().getTransformedRotation().getForward();
    }
}
