/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Achtern (Christian Gärtner & Contributors)
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

package io.github.achtern.AchternEngine.core.scenegraph.entity.debug;

import io.github.achtern.AchternEngine.core.Transform;
import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.generator.ImageGenerator;
import io.github.achtern.AchternEngine.core.rendering.mesh.Arrow;
import io.github.achtern.AchternEngine.core.rendering.mesh.Mesh;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;

public class AxisDebugger extends Node {

    /**
     * Create a new AxisDebugger.
     *
     * @param name The name of the node/axisdebugger
     */
    public AxisDebugger(String name) {
        super(name);

        pushFigure(new Arrow(), Color.RED, "X-Axis");
        pushFigure(new Arrow(), Color.GREEN, "Y-Axis");
        pushFigure(new Arrow(), Color.BLUE, "Z-Axis");
    }

    /**
     * Creates an AxisDebugger.
     */
    public AxisDebugger() {
        this("AxisDebugger");
    }

    private void pushFigure(Mesh mesh, Color color, String name) {

        Figure f = new Figure("Axis", mesh);
        Material m = new Material();

        m.addTexture("diffuse", new Texture(ImageGenerator.bytesFromColor(color)));

        f.setMaterial(m);

        Node n = new Node("AxisDebugger/" + name);

        n.add(f);
        add(n);

        // Rotate, so they are laying on the corresponding axis
        if (name.equals("Z-Axis")) {
            n.getTransform().rotate(Transform.Z_AXIS, -90);
        }
        // Arrow Mesh is oriented on Y already.
        else if (name.equals("X-Axis")) {
            n.getTransform().rotate(Transform.X_AXIS, 90);
        }

    }
}
