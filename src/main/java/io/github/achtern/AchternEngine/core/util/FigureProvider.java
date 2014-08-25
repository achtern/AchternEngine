package io.github.achtern.AchternEngine.core.util;

import io.github.achtern.AchternEngine.core.resource.ResourceLoader;
import io.github.achtern.AchternEngine.core.scenegraph.Node;
import io.github.achtern.AchternEngine.core.scenegraph.entity.Figure;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link io.github.achtern.AchternEngine.core.util.FigureProvider} can be used
 *  to quickly add a lot of {@link io.github.achtern.AchternEngine.core.scenegraph.entity.Figure}s
 *  to a scene, from [figure].json files.
 *
 * Exceptions thrown from the {@link io.github.achtern.AchternEngine.core.resource.ResourceLoader} will
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
     * @param name Name of the root Node returned
     * @param names Names of json figure declarations
     */
    public FigureProvider(String name, String... names) {
        this(name, Arrays.asList(names));
    }

    /**
     * Returns the following Node Structure:
     * FigureProvider f = new FigureProvider("name", "floor", "another");
     * f.get();
     * <code>
     *     | Node(name)
     *     |
     *     | -> | Node(floor)
     *     |    | -> Figure(floor)
     *     |
     *     | -> | Node(another)
     *     |    | -> Figure(another)
     *
     *
     *
     * </code>
     *
     * @return Node Tree
     *
     * @throws java.lang.RuntimeException when an Excpetion is thrown from the ResourceLoader
     */
    @Override
    public Node get() {

        final Node n = new Node(this.name);

        for (String name : this.names) {
            try {
                Figure f = ResourceLoader.getFigure(name);
                n.add(f.boxed());
            } catch (Exception e) {
                // We just a rethrow a checked excretion.
                throw new RuntimeException(e);
            }
        }

        return n;
    }
}
