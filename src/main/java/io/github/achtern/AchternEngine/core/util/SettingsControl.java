package io.github.achtern.AchternEngine.core.util;

import java.util.Properties;

public abstract class SettingsControl<T> {

    private T object;

    private Properties settings;

    public SettingsControl(T object) {

    }

    public abstract void fromProperties(Properties properties);

    protected boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(settings.getProperty(key, Boolean.toString(defaultValue)));
    }

    protected boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    protected void setBoolean(String key, boolean value) {
        settings.setProperty(key, Boolean.toString(value));
    }

    protected float getFloat(String key, float defaultValue) {
        return Float.parseFloat(settings.getProperty(key, Float.toString(defaultValue)));
    }

    protected int getInt(String key, int defaultValue) {
        return Integer.parseInt(settings.getProperty(key, Integer.toString(defaultValue)));
    }

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }

    public Properties getSettings() {
        return settings;
    }

    public void setSettings(Properties settings) {
        this.settings = settings;
    }
}
