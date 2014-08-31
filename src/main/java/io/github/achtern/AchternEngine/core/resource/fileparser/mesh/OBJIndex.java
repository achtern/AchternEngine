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

package io.github.achtern.AchternEngine.core.resource.fileparser.mesh;

public class OBJIndex {

    private int vertex;
    private int texCoord;
    private int normal;


    public boolean equals(OBJIndex index) {

        return (
                index.getVertex() == getVertex() &&
                index.getTexCoord() == getTexCoord() &&
                index.getNormal() == getNormal()
        );

    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof OBJIndex && equals((OBJIndex) obj));
    }

    @Override
    public int hashCode() {
        /**
         * Taken from http://stackoverflow.com/a/3934220/1933324
         */
        int hash = 17;
        hash = ((hash + getVertex()) << 5) - (hash + getVertex());
        hash = ((hash + getTexCoord()) << 5) - (hash + getTexCoord());
        hash = ((hash + getNormal()) << 5) - (hash + getNormal());
        return hash;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getTexCoord() {
        return texCoord;
    }

    public void setTexCoord(int texCoord) {
        this.texCoord = texCoord;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }
}
