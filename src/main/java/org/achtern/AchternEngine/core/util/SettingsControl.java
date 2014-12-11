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

package org.achtern.AchternEngine.core.util;

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
