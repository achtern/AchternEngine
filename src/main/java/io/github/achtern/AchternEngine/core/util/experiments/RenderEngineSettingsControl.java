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

package io.github.achtern.AchternEngine.core.util.experiments;

import io.github.achtern.AchternEngine.core.rendering.PassFilter;
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
                get().removePassFilter((PassFilter) dataStore.get(BASIC_SHADOWS));
            }
        } else {
            if (enable) {
                dataStore.put(BASIC_SHADOWS, new BasicShadowRenderer());
                get().addPassFilter((PassFilter) dataStore.get(BASIC_SHADOWS));
            }
        }
    }
}
