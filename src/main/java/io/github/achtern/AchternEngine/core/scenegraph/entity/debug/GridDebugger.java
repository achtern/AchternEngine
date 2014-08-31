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

package io.github.achtern.AchternEngine.core.scenegraph.entity.debug;

import io.github.achtern.AchternEngine.core.rendering.Color;
import io.github.achtern.AchternEngine.core.rendering.Material;
import io.github.achtern.AchternEngine.core.rendering.generator.ImageGenerator;
import io.github.achtern.AchternEngine.core.rendering.mesh.Grid;
import io.github.achtern.AchternEngine.core.rendering.texture.Texture;
import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GridDebugger extends Node {

    public static final Logger LOGGER = LoggerFactory.getLogger(GridDebugger.class);

    protected Figure grid;

    /**
     * Create a new GridDebugger.
     * Uses 50x50 grid with 1 as line separation
     * (Name of this Node: 'Grid')
     */
    public GridDebugger() {
        this("Grid");
    }

    /**
     * Create a new GridDebugger.
     * Uses 50x50 grid with 1 as line separation
     * @param name The name of the node
     */
    public GridDebugger(String name) {
        this(name, 50, 50, 1);
    }

    public GridDebugger(String name, int xCount, int yCount, float lineSeparation) {
        super(name);
        try {
            this.grid = ResourceLoader.getFigure("grid");
        } catch (Exception e) {
            LOGGER.error("Error loading bundled Figure grid", e);


            this.grid = new Figure("Grid");
            this.grid.setMesh(new Grid(xCount, yCount, lineSeparation));
            Material m = new Material();
            m.addTexture("diffuse", new Texture(ImageGenerator.bytesFromColor(Color.WHITE)));
            m.addFloat("specularIntensity", 1);
            m.addFloat("specularPower", 8);
            this.grid.setMaterial(m);
        }

        if (xCount != 50 && yCount != 50 && lineSeparation != 1) {
            ((Grid) this.grid.getMesh()).generate(xCount, yCount, lineSeparation);
        }

        add(grid);

    }
}
