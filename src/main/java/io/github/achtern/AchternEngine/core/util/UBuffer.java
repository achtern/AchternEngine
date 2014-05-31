package io.github.achtern.AchternEngine.core.util;

import io.github.achtern.AchternEngine.core.math.Matrix4f;
import io.github.achtern.AchternEngine.core.rendering.Vertex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class UBuffer {


    public static FloatBuffer create(Vertex[] vertices) {

        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());

            buffer.put(vertex.getTexCor().getX());
            buffer.put(vertex.getTexCor().getY());

            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
        }

        return buffer;

    }

    public static IntBuffer create(int... integers) {
        IntBuffer buffer = createIntBuffer(integers.length);
        buffer.put(integers);

        return buffer;
    }

    public static FloatBuffer create(Matrix4f matrix) {
        FloatBuffer buffer = createFloatBuffer(16); // 4 * 4

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                buffer.put(matrix.get(x, y));
            }
        }

        return buffer;
    }

    public static IntBuffer createIntBuffer(int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    public static FloatBuffer createFloatBuffer(int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    public static ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

}
