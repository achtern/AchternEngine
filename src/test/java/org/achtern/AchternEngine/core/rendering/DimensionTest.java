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

package org.achtern.AchternEngine.core.rendering;

import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DimensionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidWidth() throws Exception {
        Dimension fails0 = new Dimension(0, 3);
        Dimension fails1 = new Dimension(-1, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidHeight() throws Exception {
        Dimension fails0 = new Dimension(4, 0);
        Dimension fails1 = new Dimension(4, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidDimension() throws Exception {
        Dimension fails0 = new Dimension(0, 0);
        Dimension fails1 = new Dimension(-1, -1);
    }

    @Test
    public void testCopyConstructor() throws Exception {
        Dimension d0 = new Dimension(4, 6);

        Dimension result = new Dimension(d0);

        assertNotEquals("Should be two distinct objects", System.identityHashCode(d0), System.identityHashCode(result));

        assertEquals("Should hold the same value", d0, result);
    }

    @Test
    public void testFromBufferedImage() throws Exception {
        BufferedImage img = new BufferedImage(4, 6, BufferedImage.TYPE_INT_RGB);
        Dimension expected = new Dimension(4, 6);

        assertEquals("Should get the correct dimensions from a BufferedImage",
                expected, Dimension.fromBufferedImage(img));

    }

    @Test
    public void testFactor2() throws Exception {
        Dimension d0 = new Dimension(3, 6);

        Dimension expected = new Dimension(4, 8);
        Dimension result = d0.factor2();

        assertNotEquals("Should return a new instance",
                d0, result
        );

        assertEquals("Should not modify the inital instance",
            d0, d0
        );


        assertEquals("Should find the next largest width&height, which are a factor of 2",
            expected, result
        );

    }

    @Test
    public void testValueMapping() throws Exception {

        Dimension d0 = new Dimension(4, 6);

        assertEquals("Should map width to X",
                d0.getX(), d0.getWidth(), 0);

        assertEquals("Should map height to Y",
                d0.getY(), d0.getHeight(), 0);

    }

    @Test
    public void testSetters() throws Exception {
        Dimension orgD0 = new Dimension(4, 6);
        Dimension d0 = new Dimension(4, 6);

        d0.setWidth(5);
        d0.setHeight(7);

        Dimension expected = new Dimension(5, 7);

        assertNotEquals("Should modify the instance",
            orgD0, d0
        );

        assertEquals("Should set width & height",
            expected, d0
        );
    }
}