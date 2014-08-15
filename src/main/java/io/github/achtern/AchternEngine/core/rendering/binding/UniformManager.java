package io.github.achtern.AchternEngine.core.rendering.binding;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.math.Vector2f;
import io.github.achtern.AchternEngine.core.math.Vector3f;
import io.github.achtern.AchternEngine.core.math.Vector4f;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.light.Attenuation;
import io.github.achtern.AchternEngine.core.rendering.shader.Shader;
import io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses.Uniform;
import io.github.achtern.AchternEngine.core.scenegraph.entity.renderpasses.light.*;

public interface UniformManager {

    public void registerUniform(Shader shader, Uniform uniform);

    public void addUniforms(Shader shader);


    public void setUniform(Shader shader, Uniform uniform);

    public void setUniform(Shader shader, String name, Vector3f vec);

    public void setUniform(Shader shader, String name, Vector4f vec);

    public void setUniform(Shader shader, String name, Color color);

    public void setUniform(Shader shader, String name, Vector2f vec);

    public void setUniform(Shader shader, String name, Matrix4f matrix);

    public void setUniform(Shader shader, String name, int value);

    public void setUniform(Shader shader, String name, float value);

    public void setUniform(Shader shader, String name, DirectionalLight directionalLight);

    public void setUniform(Shader shader, String name, AmbientLight ambientLight);

    public void setUniform(Shader shader, String name, BaseLight baseLight);

    public void setUniform(Shader shader, String name, PointLight pointLight);

    public void setUniform(Shader shader, String name, Attenuation attenuation);

    public void setUniform(Shader shader, String name, SpotLight spotLight);


}
