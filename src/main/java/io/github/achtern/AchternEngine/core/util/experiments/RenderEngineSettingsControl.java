package io.github.achtern.AchternEngine.core.util.experiments;

import io.github.achtern.AchternEngine.core.rendering.RenderEngine;
import io.github.achtern.AchternEngine.core.rendering.shadow.BasicShadowRenderer;
import io.github.achtern.AchternEngine.core.util.SettingsControl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RenderEngineSettingsControl extends SettingsControl<RenderEngine> {

    public static final String BASIC_SHADOWS = "shadows";

    protected Map<String, Object> dataStore = new HashMap<String, Object>();

    public RenderEngineSettingsControl(RenderEngine object) {
        super(object);
    }

    @Override
    public void fromProperties(Properties properties) {
    }

    public void enableShadows(boolean enable) {
        if (getBoolean(BASIC_SHADOWS)) {
            // it is enabled already!
            if (!enable) {
                get().removePassFilter((io.github.achtern.AchternEngine.core.contracts.PassFilter) dataStore.get(BASIC_SHADOWS));
            }
        } else {
            if (enable) {
                dataStore.put(BASIC_SHADOWS, new BasicShadowRenderer());
                get().addPassFilter((io.github.achtern.AchternEngine.core.contracts.PassFilter) dataStore.get(BASIC_SHADOWS));
            }
        }
    }
}
