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

import lombok.Getter;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Getter
public class ColorTest {

    @Test
    public void testToAwt() {

        Color color0 = new Color(1, 1, 1, 1);
        java.awt.Color awt0 = new java.awt.Color(1f, 1f, 1f, 1f);

        Color color1 = new Color(1, 0, 0.5f, 1);
        java.awt.Color awt1 = new java.awt.Color(1f, 0f, 0.5f, 1f);

        Color color2 = new Color(0.1f, 0.2f, 0.3f, 0.4f);
        java.awt.Color awt2 = new java.awt.Color(0.1f, 0.2f, 0.3f, 0.4f);

        assertEquals("Should create a correct AWT Color object {0}",
                awt0, color0.toAwt()
        );

        assertEquals("Should create a correct AWT Color object {1}",
                awt1, color1.toAwt()
        );

        assertEquals("Should create a correct AWT Color object {2}",
                awt2, color2.toAwt()
        );


    }

    @Test
    public void testGetRGB() {

        Color color0 = new Color(1, 1, 1, 1);
        Color color1 = new Color(0.5f, 0.5f, 0.5f, 0.5f);

        Vector3f rgb0 = new Vector3f(1, 1, 1);
        Vector3f rgb1 = new Vector3f(0.5f, 0.5f, 0.5f);

        assertEquals("Should return the raw RGB values (ignoring the Alpha value) {0}",
                rgb0, color0.getRGB()
        );

        assertEquals("Should return the raw RGB values (ignoring the Alpha value) {1}",
                rgb1, color1.getRGB()
        );


    }

    @Test
    public void testGetColor() {
        Color color0 = new Color(1, 1, 1, 1);
        Color color1 = new Color(0.5f, 0.5f, 0.5f, 0.5f);

        Vector3f rgb0 = new Vector3f(1, 1, 1);
        Vector3f rgb1 = new Vector3f(0.5f, 0.5f, 0.5f).mul(0.5f);

        assertEquals("Should return the actual color values {0}",
                rgb0, color0.getColor()
        );

        assertEquals("Should return the actual color values {1}",
                rgb1, color1.getColor()
        );
    }

    @Test
    public void testRGBXYZMapping() {

        Color color = new Color(1, 2, 3, 4);

        assertEquals("Should map red to X",
                color.getX(), color.getRed(), 0
        );

        assertEquals("Should map green to Y",
                color.getY(), color.getGreen(), 0
        );

        assertEquals("Should map blue to Z",
                color.getZ(), color.getBlue(), 0
        );

        assertEquals("Should map alpha to W",
                color.getW(), color.getAlpha(), 0
        );
    }

}