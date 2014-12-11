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

package org.achtern.AchternEngine.core.scenegraph.scanning;

import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Entity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingleEntityRetrieverTest {

    public SingleEntityRetriever retriever;

    public Entity[] entities;

    @Before
    public void before() {
        entities = new Entity[] {
                new EntityRetrieverTest.AnotherDummyEntity(),
                new EntityRetrieverTest.AnotherDummyEntity(),
                new EntityRetrieverTest.DummyEntity(),
                new EntityRetrieverTest.YetAnotherDummyEntity()
        };

        retriever = new SingleEntityRetriever();
        Node root = new Node();
        root.add(entities[0]);
        root.add(entities[1]);
        root.add(entities[2]);
        root.add(new Node().add(entities[3]));

        retriever.scan(root);
    }

    @Test
    public void testGet() {
        assertEquals("Should return the first occurrence of the Entity",
                entities[0],
                retriever.get(EntityRetrieverTest.AnotherDummyEntity.class));

        assertEquals("Should return the first occurrence of the Entity (strict class check!)",
                entities[2],
                retriever.get(EntityRetrieverTest.DummyEntity.class));

        assertEquals("Should return the first occurrence of the Entity (strict class check!)",
                entities[3],
                retriever.get(EntityRetrieverTest.YetAnotherDummyEntity.class));

        assertEquals("Should return null if no Entity for the given Type exists",
                null,
                retriever.get(EntityRetrieverTest.DumpDummyEntity.class));
    }

    @Test
    public void testConstructor() {
        Entity e = new EntityRetrieverTest.DummyEntity();
        Node n = new Node().add(e);
        SingleEntityRetriever r = new SingleEntityRetriever(n);

        assertEquals("Should scan the Node given in the constructor",
                e,
                r.get(EntityRetrieverTest.DummyEntity.class));
    }
}