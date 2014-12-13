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

package org.achtern.AchternEngine.core.scenegraph;

import org.achtern.AchternEngine.core.util.Callback;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShortLifeNodeTest {

    protected ShortLifeNode node;
    protected ParentNode parent;

    @Before
    public void before() {
        node = new ShortLifeNode(2);
        parent = new ParentNode();

        parent.add(node);
    }

    @Test
    public void nodeCallsRemove() {
        node.update(0.5f);
        node.update(0.5f);
        node.update(0.5f);
        node.update(0.4f);

        assertFalse("Shouldn't call remove() on parent to early", parent.removeCalled);

        node.update(0.1f);

        assertTrue("Should call remove() on parent when the maxLifeTime is greater or equal", parent.removeCalled);

    }

    @Test
    public void nodeCallsCallback() {
        CustomCallback cb = new CustomCallback();

        node.setDeathCallback(cb);

        node.update(2);

        assertEquals("Should pass itself to the deathcallback", node, cb.passedItem);
    }


    private static final class ParentNode extends Node {

        public boolean removeCalled;

        /**
         * Removes child Node from this Node.
         *
         * @param node Node to remove
         * @return Whether the remove was successful (false if Node did not exist in children List)
         */
        @Override
        public boolean remove(Node node) {
            this.removeCalled = true;
            return true;
        }
    }

    private static final class CustomCallback implements Callback<ShortLifeNode> {
        public ShortLifeNode passedItem;

        @Override
        public void call(ShortLifeNode item) {
            passedItem = item;
        }
    }

}