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

package org.achtern.AchternEngine.core.scenegraph.entity;

import org.achtern.AchternEngine.core.scenegraph.Node;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class QuickEntityTest {

    @Test
    public void testConstructor() {

        QuickEntity e = new ImplQuickEntity();

        assertEquals("Should apply a default name", QuickEntity.NAME_UNTITLED_ENTITY, e.getName());

        e = new ImplQuickEntity("some name");

        assertEquals("Should set the given name", "some name", e.getName());

    }

    @Test
    public void testRemoved() {
        QuickEntity e = new ImplQuickEntity();

        e.setParent(Mockito.mock(Node.class));

        e.removed();

        assertNull("Should set the parent to null on #removed()", e.getParent());
    }

    @Test
    public void testGetTransform() {
        QuickEntity e = new ImplQuickEntity();
        Node n = new Node();

        e.setParent(n);

        assertEquals("Should get the transform from the parent node", n.getTransform(), e.getTransform());
    }

    @Test
    public void testBoxed() {
        QuickEntity e = new ImplQuickEntity();

        assertEquals("Should use the simple name of the class, when the Entity is untitled",
                ImplQuickEntity.class.getSimpleName(), e.boxed().getName());

        e = new ImplQuickEntity("some name");

        assertEquals("Should use the name of the Entity", "some name", e.boxed().getName());


        assertEquals("Should only contain the Entity itself", 1, e.boxed().getEntities().size());
        assertTrue("Should only contain the Entity itself and no children", e.boxed().getChildren().isEmpty());

    }

    public final class ImplQuickEntity extends QuickEntity {
        /**
         * Create an "Untitled Entity"
         */
        public ImplQuickEntity() {
            super();
        }

        /**
         * Create a entity with a specifc name
         *
         * @param name The name
         */
        public ImplQuickEntity(String name) {
            super(name);
        }
    }

}