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

package io.github.achtern.AchternEngine.core.rendering.state;

public enum Feature {

    DEPTH_CLAMP(320),
    ALPHA_TEST(0),
    AUTO_NORMAL(0),
    BLEND(0),
    COLOR_LOGIC_OP(110),
    COLOR_MATERIAL(0),
    COLOR_SUM(0),
    CULL_FACE(0),
    DEPTH_TEST(0),
    DITHER(0),
    FOG(0),
    INDEX_LOGIC_OP(0),
    LIGHTING(0),
    LINE_SMOOTH(0),
    LINE_STIPPLE(0),
    MAP1_COLOR_4(0),
    MAP1_INDEX(0),
    MAP1_NORMAL(0),
    MAP1_TEXTURE_COORD_1(0),
    MAP1_TEXTURE_COORD_2(0),
    MAP1_TEXTURE_COORD_3(0),
    MAP1_TEXTURE_COORD_4(0),
    MAP1_VERTEX_3(0),
    MAP1_VERTEX_4(0),
    MAP2_COLOR_4(0),
    MAP2_INDEX(0),
    MAP2_NORMAL(0),
    MAP2_TEXTURE_COORD_1(0),
    MAP2_TEXTURE_COORD_2(0),
    MAP2_TEXTURE_COORD_3(0),
    MAP2_TEXTURE_COORD_4(0),
    MAP2_VERTEX_3(0),
    MAP2_VERTEX_4(0),
    MULTISAMPLE(130),
    NORMALIZE(0),
    POINT_SMOOTH(0),
    POINT_SPRITE(200),
    POLYGON_OFFSET_FILL(110),
    POLYGON_OFFSET_LINE(110),
    POLYGON_OFFSET_POINT(110),
    POLYGON_SMOOTH(0),
    POLYGON_STIPPLE(0),
    RESCALE_NORMAL(120),
    SAMPLE_ALPHA_TO_COVERAGE(130),
    SAMPLE_ALPHA_TO_ONE(130),
    SAMPLE_COVERAGE(130),
    SCISSOR_TEST(0),
    STENCIL_TEST(0),
    TEXTURE_1D(0),
    TEXTURE_2D(0),
    TEXTURE_3D(120),
    TEXTURE_CUBE_MAP(130),
    TEXTURE_GEN_Q(0),
    TEXTURE_GEN_R(0),
    TEXTURE_GEN_S(0),
    TEXTURE_GEN_T(0),
    VERTEX_PROGRAM_POINT_SIZE(200),
    VERTEX_PROGRAM_TWO_SIDE(200);

    private int version;

    Feature(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }




}
