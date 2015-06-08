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

package org.achtern.AchternEngine.core.audio.openal;

import org.achtern.AchternEngine.core.Transform;
import org.achtern.AchternEngine.core.math.Quaternion;
import org.achtern.AchternEngine.core.math.Vector3f;
import org.achtern.AchternEngine.core.scenegraph.Node;
import org.achtern.AchternEngine.core.scenegraph.entity.Entity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.*;

public class AudioListenerTest {

    Vector3f v1;
    Vector3f v2;
    Vector3f v3;
    Vector3f v4;

    Quaternion q1;

    @Before
    public void before() {
        v1 = new Vector3f(0, 0, 1);
        v2 = new Vector3f(0, 0, 2);
        v3 = new Vector3f(0, 0, 3);
        v4 = new Vector3f(0, 0, 4);

        q1 = new Quaternion(1, 2, 3, 4);
    }

    @Test
    public void testConstructor() {



        AudioListener al1 = new AudioListener();
        AudioListener al2 = new AudioListener(v1, v2, v3, v4);


        assertArrayEquals("Should use the given values",
                new Vector3f[] {
                        v1, v2, v3, v4
                }, new Vector3f[] {
                        al2.getPosition(),
                        al2.getVelocity(),
                        al2.getUp(),
                        al2.getForward(),
                }
        );

        assertArrayEquals("Should use sane default values",
                new Vector3f[] {
                        Vector3f.ZERO.get(),
                        Vector3f.ZERO.get(),
                        Vector3f.UNIT_Y.get(),
                        Vector3f.UNIT_Z.get()
                }, new Vector3f[] {
                        al1.getPosition(),
                        al1.getVelocity(),
                        al1.getUp(),
                        al1.getForward(),
                }
        );

    }

    @Test
    public void testFromTransformFactory() {
        Transform t = new Transform();
        t.setPosition(v1);
        t.setRotation(q1);

        AudioListener al1 = AudioListener.fromTransform(t);

        assertArrayEquals("Should use the values from the Transform and zero velocity",
                new Vector3f[] {
                        v1, Vector3f.ZERO,
                        q1.getUp(), q1.getForward()
                }, new Vector3f[] {
                        al1.getPosition(),
                        al1.getVelocity(),
                        al1.getUp(),
                        al1.getForward(),
                }
        );

        Transform parent = new Transform();
        parent.setPosition(Vector3f.ONE);
        parent.setRotation(new Quaternion(1, 0, 0, 0));

        t.setParent(parent);

        AudioListener al2 = AudioListener.fromTransform(t);

        assertArrayEquals("Should use the transformed values from the Transform and zero velocity",
                new Vector3f[]{
                        t.getTransformedPosition(),
                        Vector3f.ZERO,
                        t.getTransformedRotation().getUp(),
                        t.getTransformedRotation().getForward()
                }, new Vector3f[]{
                        al2.getPosition(),
                        al2.getVelocity(),
                        al2.getUp(),
                        al2.getForward(),
                }
        );
    }

    @Test
    public void testUpdateFromTransform() {
        Transform t = new Transform();
        t.setPosition(v1);
        t.setRotation(q1);

        AudioListener al1 = new AudioListener();
        al1.updateFrom(t);

        assertArrayEquals("Should use the values from the Transform and zero velocity",
                new Vector3f[]{
                        v1, Vector3f.ZERO,
                        q1.getUp(), q1.getForward()
                }, new Vector3f[]{
                        al1.getPosition(),
                        al1.getVelocity(),
                        al1.getUp(),
                        al1.getForward(),
                }
        );

        Transform parent = new Transform();
        parent.setPosition(Vector3f.ONE);
        parent.setRotation(new Quaternion(1, 0, 0, 0));

        t.setParent(parent);

        AudioListener al2 = new AudioListener();
        al2.updateFrom(t);

        assertArrayEquals("Should use the transformed values from the Transform and zero velocity",
                new Vector3f[]{
                        t.getTransformedPosition(),
                        Vector3f.ZERO,
                        t.getTransformedRotation().getUp(),
                        t.getTransformedRotation().getForward()
                }, new Vector3f[]{
                        al2.getPosition(),
                        al2.getVelocity(),
                        al2.getUp(),
                        al2.getForward(),
                }
        );
    }

    @Test
    public void testUpdateFromEntity() {
        Entity entity = mock(Entity.class);
        when(entity.getTransform()).thenAnswer(new Answer<Transform>() {
            @Override
            public Transform answer(InvocationOnMock invocation) throws Throwable {
                return new Transform();
            }
        });

        new AudioListener().updateFrom(entity);
        verify(entity).getTransform();
    }

    @Test
    public void testUpdateFromNode() {
        Node node = mock(Node.class);
        when(node.getTransform()).thenAnswer(new Answer<Transform>() {
            @Override
            public Transform answer(InvocationOnMock invocation) throws Throwable {
                return new Transform();
            }
        });

        new AudioListener().updateFrom(node);
        verify(node).getTransform();
    }

}
