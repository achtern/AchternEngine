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
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepthFirstWalkerTest {

    public DepthFirstWalker walker;

    public CountVisitor countVisitor1;
    public CountVisitor countVisitor2;

    @Before
    public void before() throws Exception {
        walker = new DepthFirstWalker();
        walker.addVisitor(countVisitor1 = new CountVisitor());
        walker.addVisitor(countVisitor2 = new CountVisitor());
    }


    @Test
    public void testVisitorSystemBasic() {

        assertEquals("Should keep track of visitors",
                2,
                walker.getVisitors().size());

        walker.clearVisitors();

        assertEquals("Should be able to clear vistors",
                0,
                walker.getVisitors().size());

    }

    @Test
    public void testBasicWalking() {
        walker.scan(new Node()
                        .add(new Node()
                                .add(new Node())
                                .add(new Node())
                            )
                        .add(new Node())
        );


        assertEquals("Should visit each Node and pass it to each visitor",
                5,
                countVisitor1.count);

        assertEquals("Should visit each Node and pass it to each visitor",
                5,
                countVisitor2.count);
    }

    @Test
    public void testDepthFirst() {
        walker.clearVisitors();

        NameKeeperVisitor v = new NameKeeperVisitor();
        walker.addVisitor(v);

        walker.scan(new Node("A")
                        .add(new Node("A.A")
                                .add(new Node("A.A.A"))
                                .add(new Node("A.A.B"))
                        )
                        .add(new Node("A.B")
                                .add(new Node("A.B.A")))
        );

        List<String> expected0 = new LinkedList<String>();
        expected0.add("A");
        expected0.add("A.A");
        expected0.add("A.A.B");
        expected0.add("A.A.A");
        expected0.add("A.B");
        expected0.add("A.B.A");

        List<String> expected1 = new LinkedList<String>();
        expected1.add("A");
        expected1.add("A.A");
        expected1.add("A.A.A");
        expected1.add("A.A.B");
        expected1.add("A.B");
        expected1.add("A.B.A");

        boolean equals = expected0.equals(v.nodeNames) || expected1.equals(v.nodeNames);

        assertTrue("Should visit the first node and the first child and all its children and" +
                        " then the second from the main Node, etc.", equals);

    }

    public static final class CountVisitor implements SceneGraphWalker.Visitor {

        public int count;

        @Override
        public void on(Node node) {
            count++;
        }
    }

    public static final class NameKeeperVisitor implements SceneGraphWalker.Visitor {

        public List<String> nodeNames = new LinkedList<String>();

        @Override
        public void on(Node node) {
            nodeNames.add(node.getName());
        }
    }


}
