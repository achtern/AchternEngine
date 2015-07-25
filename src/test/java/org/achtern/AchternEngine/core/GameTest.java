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

package org.achtern.AchternEngine.core;

import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {

    @Mock
    CoreEngine engine;

    Game game;

    public GameTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void before() {
        game = new ImplGame();
        game.setEngine(engine);
    }

    @Test
    public void testAdd() {
        Node n = new Node("name");

        // Should not throw any Excpetions
        game.add(n);

        assertEquals("Should pass the Game's engine to the child nodes", engine, n.getEngine());
    }

    @Test
    public void testHas() {
        Node n0 = new Node();
        Node n1 = new Node();

        n0.add(n1);

        game.add(n0);

        assertTrue("Should check if the root node contains the given node", game.has(n0));
        assertFalse("Should check if the root node contains the given node", game.has(new Node()));
        assertFalse("Should check if the root node contains the given node", game.has(n1));
    }

    public final class ImplGame extends Game {
        @Override
        public Dimension getWindowDimensions() {
            return null;
        }
        @Override
        public String getWindowTitle() {
            return null;
        }
        @Override
        public void init(CoreEngine engine) {

        }
        @Override
        public void render(RenderEngine renderEngine) {

        }
        @Override
        public void update(float delta) {

        }
    }

}