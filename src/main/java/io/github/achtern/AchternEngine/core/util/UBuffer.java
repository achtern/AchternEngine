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

package io.github.achtern.AchternEngine.core.util;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Utility for Buffers
 */
public class UBuffer {


    /**
     * Creates a float buffer from {@link io.github.achtern.AchternEngine.core.rendering.Vertex} array;
     * compatible with LWJGL OpenGL binding.
     * @param vertices The vertices to buffer
     * @return the buffer
     */
    public static FloatBuffer create(Vertex[] vertices) {

        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            // Positions
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());

            // Texture Coordinates
            buffer.put(vertex.getTexCor().getX());
            buffer.put(vertex.getTexCor().getY());

            // Normals
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
        }

        return buffer;

    }

    /**
     * Create IntBuffer from integers (or int array)
     * @param integers the integers to buffer
     * @return the buffer
     */
    public static IntBuffer create(int... integers) {
        IntBuffer buffer = createIntBuffer(integers.length);
        buffer.put(integers);

        return buffer;
    }

    /**
     * Creates a float buffer from {@link io.github.achtern.AchternEngine.core.math.Matrix4f};
     * compatible with LWJGL OpenGL binding.
     * @param matrix The matrix to buffer
     * @return the buffer
     */
    public static FloatBuffer create(Matrix4f matrix) {
        FloatBuffer buffer = createFloatBuffer(16); // 4 * 4

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                buffer.put(matrix.get(x, y));
            }
        }

        return buffer;
    }

    /**
     * Create an integer buffer.
     * The size will get << shifted by 2 (integers allocate 2 bytes!)
     * @param size the size of the buffer (amount of integers!)
     * @return the buffer
     */
    public static IntBuffer createIntBuffer(int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    /**
     * Create an float buffer.
     * The size will get << shifted by 2 (floats allocate 2 bytes!)
     * @param size the size of the buffer (amount of integers!)
     * @return the buffer
     */
    public static FloatBuffer createFloatBuffer(int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    /**
     * Create a simple ByteBuffer.
     * @param size The size of the buffer
     * @return the buffer
     */
    public static ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

}
