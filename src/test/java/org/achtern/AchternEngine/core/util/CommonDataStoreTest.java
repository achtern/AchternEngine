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

package org.achtern.AchternEngine.core.util;

import org.achtern.AchternEngine.core.math.Matrix4f;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.rendering.Color;
import org.achtern.AchternEngine.core.rendering.Dimension;
import org.achtern.AchternEngine.core.rendering.texture.Texture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonDataStoreTest {

    public CommonDataStore store;

    @Before
    public void before() {
        store = new CommonDataStore();
    }

    @Test
    public void testBasicAddingAndGetting() {

        Texture tex1 = new Texture(new Dimension(1, 1));
        Texture tex2 = new Texture(new Dimension(2, 1));

        Vector3f vec1 = new Vector3f(0, 0, 0);
        Vector3f vec2 = new Vector3f(1, 0, 0);

        Color c1 = new Color(0, 0, 0);
        Color c2 = new Color(1, 0, 0);

        int i1 = 0;
        int i2 = 1;

        float f1 = 0;
        float f2 = 1;

        Matrix4f m1 = new Matrix4f();
        Matrix4f m2 = new Matrix4f();

        store.addTexture("tex1", tex1);
        store.addTexture("tex2", tex2);

        store.addVector("vec1", vec1);
        store.addVector("vec2", vec2);

        store.addColor("c1", c1);
        store.addColor("c2", c2);

        store.addInteger("i1", i1);
        store.addInteger("i2", i2);

        store.addFloat("f1", f1);
        store.addFloat("f2", f2);

        store.addMatrix("m1", m1);
        store.addMatrix("m2", m2);


        assertEquals("Should return the same Texture Object for the same key",
                tex1,
                store.getTexture("tex1")
        );

        assertEquals("Should return the same Texture Object for the same key",
                tex2,
                store.getTexture("tex2")
        );



        assertEquals("Should return the same Vector Object for the same key",
                vec1,
                store.getVector("vec1")
        );

        assertEquals("Should return the same Vector Object for the same key",
                vec2,
                store.getVector("vec2")
        );


        assertEquals("Should return the same Color Object for the same key",
                c1,
                store.getColor("c1")
        );

        assertEquals("Should return the same Color Object for the same key",
                c2,
                store.getColor("c2")
        );


        assertEquals("Should return the same Integer value for the same key",
                i1,
                store.getInteger("i1")
        );

        assertEquals("Should return the same Integer value for the same key",
                i2,
                store.getInteger("i2")
        );


        assertEquals("Should return the same Float value for the same key",
                new Float(f1),
                new Float(store.getFloat("f1"))
        );

        assertEquals("Should return the same Float value for the same key",
                new Float(f2),
                new Float(store.getFloat("f2"))
        );


        assertEquals("Should return the same Matrix Object for the same key",
                m1,
                store.getMatrix("m1")
        );

        assertEquals("Should return the same Matrix Object for the same key",
                m2,
                store.getMatrix("m2")
        );

    }

    @Test
    public void testDifferentNameSpacesPerType() {
        Texture tex1 = new Texture(new Dimension(1, 1));
        Vector3f vec1 = new Vector3f(0, 0, 0);

        store.addTexture("name", tex1);
        store.addVector("name", vec1);

        assertEquals("Should manage different name spaces for different object type",
                tex1,
                store.getTexture("name")
        );

        assertEquals("Should manage different name spaces for different object type",
                vec1,
                store.getVector("name")
        );
    }

    @Test
    public void testHasCheck() {

        Texture tex1 = new Texture(new Dimension(1, 1));
        Vector3f vec1 = new Vector3f(0, 0, 0);
        Color c1 = new Color(0, 0, 0);
        int i1 = 0;
        float f1 = 0;
        Matrix4f m1 = new Matrix4f();

        store.addTexture("k", tex1);
        store.addVector("k", vec1);
        store.addColor("k", c1);
        store.addInteger("k", i1);
        store.addFloat("k", f1);
        store.addMatrix("k", m1);

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasTexture("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasTexture("f")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasVector("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasVector("f")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasColor("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasColor("f")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasInteger("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasInteger("f")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasFloat("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasFloat("f")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                true,
                store.hasMatrix("k")
        );

        assertEquals("Should be able to check if it contains a value for the given key",
                false,
                store.hasMatrix("f")
        );

    }

    @Test
    public void testPrimitiveReturnZero() {
        assertEquals("Should return 0 when an int is not present (instead of null)",
                0,
                store.getInteger("no_key")
        );

        assertEquals("Should return 0 when a float is not present (instead of null)",
                new Float(0),
                new Float(store.getFloat("no_key"))
        );
    }

    @Test
    public void testReturnNullVectorInsteadOfNull() {
        assertEquals("Should return a NULL Vector when a Vector is not present (instead of null)",
                true,
                store.getVector("no_key").isNullVector()
        );
    }

}