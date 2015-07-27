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

import org.achtern.AchternEngine.core.math.Vector2f;

import java.awt.image.BufferedImage;

public class Dimension extends Vector2f {

    // -- 5 : 4 --
    public static final Dimension SXGA = new Dimension(1280, 1024);
    public static final Dimension QSXGA = new Dimension(2560, 2048);

    // -- 4 : 3 --
    public static final Dimension QVGA = new Dimension(320, 240);
    public static final Dimension VGA = new Dimension(640, 480);
    public static final Dimension PAL = new Dimension(768, 576);
    public static final Dimension SVGA = new Dimension(800, 600);
    public static final Dimension XGA = new Dimension(1024, 768);
    public static final Dimension SXGA_PLUS = new Dimension(1400, 1200);
    public static final Dimension UXGA = new Dimension(1600, 1200);
    public static final Dimension QXGA = new Dimension(2048, 1536);

    // -- 16 : 10 --
    public static final Dimension CGA = new Dimension(320, 200);
    public static final Dimension WXGA = new Dimension(1280, 800);
    public static final Dimension WXGA_PLUS = new Dimension(1680, 1050);
    public static final Dimension WUXGA = new Dimension(1920, 1200);
    public static final Dimension WQXGA = new Dimension(2560, 1600);

    // -- 5 : 3 --
    public static final Dimension WVGA = new Dimension(800, 480);
    public static final Dimension WXGA_5_3 = new Dimension(1200, 768);

    // -- 16 : 9 --
    public static final Dimension FWVGA = new Dimension(854, 480);
    public static final Dimension HD_720 = new Dimension(1280, 720);
    public static final Dimension HD_1080 = new Dimension(1920, 1080);
    public static final Dimension UHD_4K_TV = new Dimension(3840, 2160);

    // -- 17 : 9 --
    public static final Dimension UHD_2K = new Dimension(2048, 1080);
    public static final Dimension UHD_4K_CINEMA = new Dimension(3840, 2160);




    public static Dimension fromBufferedImage(BufferedImage image) {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public Dimension(Dimension copy) {
        this(copy.getWidth(), copy.getHeight());
    }

    public Dimension(int width, int height) {
        super(width, height);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Not valid dimension, width|height MUST be greater than 0");
        }
    }

    public Dimension factor2() {
        int w = 2;
        int h = 2;

        while (w < getWidth()) w *= 2;
        while (h < getHeight()) h *= 2;

        return new Dimension(w, h);
    }

    public void setHeight(int height) {
        super.setY((int) height);
    }

    public void setWidth(int width) {
        super.setX((int) width);
    }

    public int getHeight() {
        return (int) getY();
    }

    public int getWidth() {
        return (int) getX();
    }
}
