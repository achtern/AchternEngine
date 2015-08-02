/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Christian Gärtner
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

package org.achtern.AchternEngine.core.util.scenegraph;

import org.achtern.AchternEngine.core.audio.openal.AudioListener;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Camera;
import org.achtern.AchternEngine.core.scenegraph.entity.controller.FlyMover;
import org.achtern.AchternEngine.core.scenegraph.entity.controller.HumanMover;
import org.achtern.AchternEngine.core.scenegraph.entity.controller.MouseLook;

/**
 * The BasicCamera bundles a
 * <br>
 * - {@link org.achtern.AchternEngine.core.scenegraph.entity.Camera} <br>
 * - {@link org.achtern.AchternEngine.core.audio.openal.AudioListener} <br>
 * - {@link org.achtern.AchternEngine.core.scenegraph.entity.controller.FlyMover} <br>
 * - {@link org.achtern.AchternEngine.core.scenegraph.entity.controller.HumanMover} <br>
 * - {@link org.achtern.AchternEngine.core.scenegraph.entity.controller.MouseLook} <br>
 * <br>
 * By default it is named "Camera"
 */
public class BasicCamera extends Node {

    /**
     * Create a new BasicCamera.
     *
     * @param name The name of the camera
     */
    public BasicCamera(String name) {
        super(name);
        Camera camera = new Camera();
        camera.setName(name);

        add(camera);
        add(new HumanMover(10));
        add(new FlyMover(10));
        add(new MouseLook(1));
        add(new AudioListener());

    }

    /**
     * Creates a BasicCamera with the name "Camera".
     */
    public BasicCamera() {
        this("Camera");
    }
}
