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

package org.achtern.AchternEngine.core.math;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2fTest {

    public Vector2f vector;
    
    @Before
    public void before() {
        vector = new Vector2f(1, 1);
    }
    
    
    @Test
    public void testLength() {
        assertEquals(
                "Should calculate the correct length of the Vector",
                new Float(1.4142135f),
                new Float(vector.length())
        );

        vector = new Vector2f(2, 0);

        assertEquals(
                "Should calculate the correct length of the Vector",
                new Float(2.0f),
                new Float(vector.length())
        );
    }

    @Test
    public void testDot() {
        vector = new Vector2f(1, 0);
        Vector2f another = new Vector2f(0, 1);
        assertEquals(
                "Should calculate the correct dot product of two Vectors",
                new Float(0),
                new Float(vector.dot(another))
        );

        assertEquals(
                "Should calculate the correct dot product of two Vectors, order should not matter",
                new Float(0),
                new Float(another.dot(vector))
        );

        another = new Vector2f(5, 6);
        vector = new Vector2f(6, 5);
        assertEquals(
                "Should calculate the correct dot product of two Vectors",
                new Float(60),
                new Float(another.dot(vector))
        );
    }

    @Test
    public void testCross() {
        vector = new Vector2f(1, 0);
        Vector2f another = new Vector2f(0, 1);
        assertEquals(
                "Should calculate the correct cross product of two Vectors",
                new Float(1),
                new Float(vector.cross(another))
        );

        assertEquals(
                "Should calculate the correct cross product of two Vectors, order should not matter",
                new Float(1),
                new Float(another.cross(vector))
        );

        another = new Vector2f(5, 6);
        vector = new Vector2f(6, 5);
        assertEquals(
                "Should calculate the correct cross product of two Vectors",
                new Float(25 + 36),
                new Float(another.cross(vector))
        );
    }

    @Test
    public void testNormalized() {
        vector = new Vector2f(2, 0);
        vector.normalize();
        assertEquals("Should normalize the internal components",
                new Vector2f(1, 0),
                vector);
    }

    @Test
    public void testNormalize() {
        Vector2f another = vector.normalized();

        assertNotEquals("Should return a new instance",
                another, vector);

        vector.normalize();

        assertEquals("Should return a normalized instance",
                another, vector);
    }

    @Test
    public void testMax() {
        vector = new Vector2f(5, 6);
        assertEquals("Should return the largest component",
                new Float(6),
                new Float(vector.max()));

        vector = new Vector2f(7, -6);
        assertEquals("Should return the largest component",
                new Float(7),
                new Float(vector.max()));
    }

    @Test
    public void testAdd() {
        Vector2f another = new Vector2f(2, 1);

        assertEquals("Should correctly add two vectors",
                new Vector2f(3, 2), another.add(vector));

        assertEquals("Should correctly add two vectors (order should not matter)",
                new Vector2f(3, 2), vector.add(another));

        another = new Vector2f(1, 2);
        assertEquals("Should correctly add two vectors",
                new Vector2f(2, 3), another.add(vector));

        Vector2f result = vector.add(another);
        assertNotEquals("Should return a new instance",
                result, vector);
    }

    @Test
    public void testAddFloat() {
        assertEquals("Should correctly add a float to a vector",
                new Vector2f(2, 2), vector.add(1));

        vector = new Vector2f(-3, -4);
        assertEquals("Should correctly add a float to a vector",
                new Vector2f(-2, -3), vector.add(1));

        vector = new Vector2f(1, 1);
        assertEquals("Should correctly add a float to a vector and handle negative values",
                new Vector2f(0, 0), vector.add(-1));
    }

    @Test
    public void testAbs() {
        vector = new Vector2f(-3, -2);
        assertEquals("Should correctly return the absolute Vector",
                new Vector2f(3, 2), vector.abs());

        vector = new Vector2f(-3, 5);
        assertEquals("Should correctly return the absolute Vector",
                new Vector2f(3, 5), vector.abs());

        Vector2f result = vector.abs();
        assertNotEquals("Should return a new instance",
                result, vector);
    }

    @Test
    public void testSet() {
        vector.set(5, 6);
        assertEquals("Should set the correct x & y components",
                new Vector2f(5, 6), vector);
    }

    @Test
    public void testSetVector() {
        vector.set(new Vector2f(5, 6));
        assertEquals("Should set the correct x & y components from a given Vector",
                new Vector2f(5, 6), vector);
    }

    @Test
    public void testGetX() {
        assertEquals("Should return the correct x component",
                new Float(1), new Float(vector.getX()));
    }

    @Test
    public void testSetX() {
        vector.setX(5);
        assertEquals("Should return the correct x component",
                new Vector2f(5, 1), vector);
    }

    @Test
    public void testGetY() {
        assertEquals("Should return the correct y component",
                new Float(1), new Float(vector.getY()));
    }

    @Test
    public void testSetY() {
        vector.setY(5);
        assertEquals("Should return the correct y component",
                new Vector2f(1, 5), vector);
    }

    @Test
    public void testEquals() {
        Vector2f another = new Vector2f(1, 1);

        assertTrue("Should be able to check if equal", another.equals(vector));
        assertTrue("Should be able to check if equal", vector.equals(another));
        assertTrue("Should be able to check if equal", vector.equals(vector));

        another.set(5, 6);
        assertFalse("Should be able to check if equal", another.equals(vector));
        assertFalse("Should be able to check if equal", vector.equals(another));


        assertTrue("Should be able to check if equal from Object",
                vector.equals((Object) new Vector2f(1, 1)));

        assertFalse("Should be able to check if equal from Object",
                vector.equals(new Object()));
    }

    @Test
    public void testIsNullVector() {
        assertFalse("Should be able to check for null vector", vector.isNullVector());
        vector = new Vector2f(1, 0);
        assertFalse("Should be able to check for null vector", vector.isNullVector());
        vector = new Vector2f(0, 0);
        assertTrue("Should be able to check for null vector", vector.isNullVector());
    }
}