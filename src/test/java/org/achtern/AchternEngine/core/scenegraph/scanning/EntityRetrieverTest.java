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
import org.achtern.AchternEngine.core.scenegraph.entity.QuickEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityRetrieverTest {

    public EntityRetriever retriever;
    public Node root;

    @Before
    public void before() {
        retriever = new DummyRetriever();

        root = new Node("root").add(new DummyEntity());
        root.add(new Node("1").add(new Node("1->1").add(new AnotherDummyEntity())));
        root.add(new Node("2").add(new Node("2->1").add(new DummyEntity())).add(new Node("2->2").add(new AnotherDummyEntity())));

        retriever.scan(root);
    }

    @Test
    public void testGetAll() {

        int totalEntities = 4;
        int totalNodes = 6;


        assertEquals("Should retrieve all Entites", totalEntities, retriever.getAll().size());
        assertEquals("Should retrieve all Nodes", totalNodes, retriever.getNodes().size());

    }

    @Test
    public void testFilterEntites() {
        int dummy = 2;
        int anotherDummy = 2;

        assertEquals("Should retrieve filter DummyEntities by class",
                dummy,
                retriever.getAll(DummyEntity.class).size()
        );
        assertEquals("Should retrieve filter AnotherDummyEntities by class",
                anotherDummy,
                retriever.getAll(AnotherDummyEntity.class).size()
        );

    }

    @Test
    public void testReScan() {
        retriever.scan(new Node().add(new DummyEntity()));

        assertEquals("Should reset all scan Data on re-scan", 1, retriever.getAll().size());
        assertEquals("Should reset all scan Data on re-scan", 1, retriever.getNodes().size());
    }

    @Test
    public void testContained() {
        assertEquals("Should check if there are any Entities", true, retriever.contained());

        retriever.scan(new Node());

        assertEquals("Should check if there are any Entities", false, retriever.contained());

    }

    public static final class DummyRetriever extends EntityRetriever {
    }

    public static final class DummyEntity extends QuickEntity {
    }

    public static final class AnotherDummyEntity extends QuickEntity {
    }

    public static final class YetAnotherDummyEntity extends QuickEntity {
    }

    public static final class DumpDummyEntity extends QuickEntity {
    }

}
