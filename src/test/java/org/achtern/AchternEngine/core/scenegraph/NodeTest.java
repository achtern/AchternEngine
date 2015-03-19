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

import org.achtern.AchternEngine.core.CoreEngine;
import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.rendering.RenderEngine;
import org.achtern.AchternEngine.core.scenegraph.entity.Entity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NodeTest {

    @Mock
    CoreEngine engine;

    @Mock
    RenderEngine renderEngine;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructor() {

        Node n0 = new Node();
        Node n1 = new Node("Name");

        assertNull("Should create a node without a name", n0.getName());
        assertEquals("Should create a node with a name", "Name", n1.getName());

    }

    @Test
    public void testEngineHolder() {
        Node n0 = new Node();

        n0.setEngine(engine);

        assertEquals("Should store the engine", engine, n0.getEngine());
    }

    @Test
    public void testAddNode() {
        Node childNoName = new Node();
        Node childWithName = new Node("child with name");
        Node childWithName2 = new Node("child with name");

        Transform tParent = Mockito.mock(Transform.class);
        Transform tcNN = Mockito.mock(Transform.class);
        Transform tcWN = Mockito.mock(Transform.class);
        Transform tcWN2 = Mockito.mock(Transform.class);


        childNoName.setTransform(tcNN);
        childWithName.setTransform(tcWN);
        childWithName2.setTransform(tcWN2);

        Node parent = new Node("parent");

        parent.setEngine(engine);
        parent.setTransform(tParent);

        Entity entity = Mockito.mock(Entity.class);

        childNoName.add(entity);



        parent.add(childNoName);
        assertEquals("Should add child to children's list", 1, parent.getChildren().size());

        parent.add(childWithName);
        assertEquals("Should add child to children's list", 2, parent.getChildren().size());

        assertEquals("Should not change the name of named children",
                "child with name", childWithName.getName());

        assertNotNull("Should give children without a name a name", childNoName.getName());
        assertTrue("Should contain the parent's name", childNoName.getName().contains("parent"));

        parent.add(childWithName2);

        assertNotEquals("Should rename children, when there is already a child w/ the same name",
                "child with name", childWithName2.getName());
        assertTrue("Should contain the parent's name", childWithName2.getName().contains("parent"));

        assertEquals("Should give the engine to it's children {0}", engine, childNoName.getEngine());
        assertEquals("Should give the engine to it's children {1}", engine, childWithName.getEngine());
        assertEquals("Should give the engine to it's children {2}", engine, childWithName2.getEngine());

        // Two times here, since it gets called when adding the entity to the node itself!
        verify(entity, times(2)).setEngine(engine);
        verify(entity).attached();


        assertEquals("Should set itself as the parent {0}", parent, childNoName.getParent());
        assertEquals("Should set itself as the parent {1}", parent, childWithName.getParent());
        assertEquals("Should set itself as the parent {2}", parent, childWithName2.getParent());

        verify(tcNN).setParent(tParent);
        verify(tcWN).setParent(tParent);
        verify(tcWN2).setParent(tParent);
    }

    @Test
    public void testUpdateAndRender() {
        Node parent = new Node();

        Node child0 = new Node();
        Node child1 = new Node();

        Entity entity0 = Mockito.mock(Entity.class);
        Entity entity1 = Mockito.mock(Entity.class);

        child0.add(entity0);
        child1.add(entity1);

        parent.add(child0);
        parent.add(child1);

        parent.update(3.2f);
        parent.render(renderEngine);

        verify(entity0).update(3.2f);
        verify(entity1).update(3.2f);
        verify(entity0).render(renderEngine);
        verify(entity1).render(renderEngine);

    }

    @Test
    public void testRemove() {
        Node parent = new Node();
        Node child = new Node();

        parent.add(child);

        assert !parent.getChildren().isEmpty();

        child.remove();

        assertTrue("Should remove itself from the parent", parent.getChildren().isEmpty());
    }

    @Test
    public void testRemoveNode() {
        Node parent = new Node();

        Node child = new Node();

        parent.add(child);

        boolean return0 = parent.remove(child);

        assertEquals("Should remove the child from the list", 0, parent.getChildren().size());

        assertTrue("Should indicate whether the given Node has been removed", return0);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNullNode() {
        Node n = new Node();

        n.remove((Node) null);
    }

    @Test
    public void testRemoveByName() {
        Node parent = new Node();
        Node child = new Node("child");

        parent.add(child);
        boolean return0 = parent.remove(child.getName());
        boolean return1 = parent.remove("nope");
        boolean return2 = parent.remove((String) null);

        assertEquals("Should remove the child from the list", 0, parent.getChildren().size());

        assertTrue("Should indicate whether the given Node has been removed", return0);
        assertFalse("Should indicate whether the given Node has been removed", return1);
        assertFalse("Should return null, when the given string is null", return2);
    }

    @Test
    public void testAddEntity() {
        Node n = new Node();
        n.setEngine(engine);

        Entity e = Mockito.mock(Entity.class);

        n.add(e);

        verify(e).setParent(n);
        verify(e).setEngine(engine);

        assertEquals("Should add the entity to the entities list", 1, n.getEntities().size());
        assertEquals("Should store the correct entity in the list", e, n.getEntities().get(0));
    }

    @Test
    public void testRemoveEntity() {
        Node n = new Node();
        Entity e0 = Mockito.mock(Entity.class);
        Entity e1 = Mockito.mock(Entity.class);

        n.add(e0);
        boolean return0 = n.remove(e0);
        boolean return1 = n.remove(e1);

        verify(e0).removed();

        assertEquals("Entity has been removed", 0, n.getEntities().size());

        assertTrue("Should indicate whether the given Entity has been removed", return0);
        assertFalse("Should indicate whether the given Entity has been removed", return1);
    }

    @Test
    public void testSetEngine() {
        CoreEngine engine1 = Mockito.mock(CoreEngine.class);

        Node parent = new Node();
        Node child = new Node();

        Entity e = Mockito.mock(Entity.class);

        parent.add(child);
        parent.add(e);

        // Inject it multiple times, so that we can check, if it does only pass it once to its children
        parent.setEngine(engine1);
        parent.setEngine(engine1);
        parent.setEngine(engine1);

        assertEquals("Should pass the engine to its children", engine1, child.getEngine());

        verify(e, times(1)).setEngine(engine1);

        parent.setEngine(engine);

        assertEquals("Should pass the new engine to its children", engine, child.getEngine());

        verify(e).setEngine(engine);


    }


}