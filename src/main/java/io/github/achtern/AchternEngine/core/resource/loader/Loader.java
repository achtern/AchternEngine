package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;

import java.lang.reflect.Constructor;

/**
 * A {@link io.github.achtern.AchternEngine.core.resource.loader.Loader} can be used
 * to convert a basic text file into any Object. The Loader is used mainly in the
 * {@link io.github.achtern.AchternEngine.core.resource.ResourceLoader}.
 * @param <T> Type of object which can be loaded by the Loader
 */
public abstract class Loader<T> {

    /**
     * This performs any type of parsing.
     * This should parse the resource, but should not constructed it,
     * just parsing and preparations to create the object
     * @param name The name of the original file
     * @param file The contents of the file to parse
     * @throws ParsingException when the parsing fails
     */
    public abstract void parse(String name, String file) throws ParsingException;

    /**
     * This should used the information, generated during
     * parsing and construct an Object.
     * @return The new object
     * @throws Exception
     */
    public abstract T get() throws Exception;

    /**
     * The preprocessor is used during reading.
     * There is no guarantee that this {@link io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser}
     * will get called. (When reading from cache for example, no PreProcessor is getting called).
     * @return LineBasedParser
     */
    public LineBasedParser getPreProcessor() {
        return null;
    }

    /**
     * Wrapper to instantiate a {@link java.lang.Class} from full classname.
     * This method will find a suitable constructor for the given args
     * @param name Full classname
     * @param args array of arguments (or null, if none)
     * @return Object
     * @throws Exception (Lots of Reflection Exceptions)
     */
    @SuppressWarnings("unchecked")
    protected Object getObject(String name, Object[] args) throws Exception {
        Object obj;
        Class clazz = Class.forName(name);

        if (args == null || args.length == 0) {
            obj = clazz.newInstance();
        } else {
            Class[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                Class<?> type = args[i].getClass();

                if (type.isAssignableFrom(Integer.class)) {
                    type = Integer.TYPE;
                } else if (type.isAssignableFrom(Float.class)) {
                    type = Float.TYPE;
                } else if (type.isAssignableFrom(Double.class)) {
                    type = Double.TYPE;
                }

                parameterTypes[i] = type;
            }
            Constructor c = clazz.getConstructor(parameterTypes);
            obj = c.newInstance(args);
        }


        return obj;
    }
}
