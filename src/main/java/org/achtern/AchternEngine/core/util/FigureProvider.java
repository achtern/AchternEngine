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

package org.achtern.AchternEngine.core.util;

import org.achtern.AchternEngine.core.resource.ResourceLoader;
import org.achtern.AchternEngine.core.scenegraph.Node;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link org.achtern.AchternEngine.core.util.FigureProvider} can be used
 *  to quickly add a lot of {@link org.achtern.AchternEngine.core.scenegraph.entity.Figure}s
 *  to a scene, from [figure].json files.
 *
 * Exceptions thrown from the {@link org.achtern.AchternEngine.core.resource.ResourceLoader} will
 *  get catched and rethrown as {@link java.lang.RuntimeException}.
 */
public class FigureProvider implements NodeProvider {

    protected String name;
    protected List<String> names;

    /**
     * Construct a new FigureProvider...
     * @param name Name of the root Node returned
     * @param names Names of json figure declarations
     */
    public FigureProvider(String name, List<String> names) {
        this.name = name;
        this.names = names;
    }

    /**
     * Construct a new FigureProvider...
     *
     * The first name parameter can be ignored, if one is using {@link #inject(Node)} exclusively.
     * @param name Name of the root Node returned
     * @param names Names of json figure declarations
     */
    public FigureProvider(String name, String... names) {
        this(name, Arrays.asList(names));
    }

    /**
     * Returns the following Node Structure:<br>
     * <code>FigureProvider f = new FigureProvider("name", "floor", "another");</code>
     * <code>f.get();</code>
     * <br>
     * <pre>
     * {@code
     * | Node(name)
     * |
     * | -> | Node(floor)
     * |    | -> Figure(floor)
     * |
     * | -> | Node(another)
     * |    | -> Figure(another)
     *
     *
     * }
     * </pre>
     *
     * @return Node Tree
     *
     * @throws java.lang.RuntimeException when an exception is thrown from the ResourceLoader
     */
    @Override
    public Node get() {
        return inject(new Node(this.name));
    }

    /**
     * The figure names were given in the constructor.
     *
     * This method will load all the given figures and inject them into the target node:
     * <br>
     * <code>
     * target.add(ResourceLoader.getFigure(name).boxed());
     * </code>
     *
     * This method just returns the given target, for easier method chaining.
     *
     * @param target all loaded boxed figures will be added to the target's children
     * @return target parameter
     *
     * @throws java.lang.RuntimeException when an exception is thrown from the ResourceLoader
     */
    public Node inject(Node target) {
        for (String name : this.names) {
            try {
                target.add(ResourceLoader.getFigure(name).boxed());
            } catch (Exception e) {
                // We just a rethrow a checked exception.
                throw new RuntimeException(e);
            }
        }

        return target;
    }
}
