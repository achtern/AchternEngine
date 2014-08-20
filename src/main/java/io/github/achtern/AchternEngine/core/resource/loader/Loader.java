package io.github.achtern.AchternEngine.core.resource.loader;

import io.github.achtern.AchternEngine.core.resource.fileparser.LineBasedParser;
import io.github.achtern.AchternEngine.core.resource.fileparser.ParsingException;

import java.lang.reflect.Constructor;

public abstract class Loader<T> {

    public abstract void parse(String name, String file) throws ParsingException;

    public abstract T get() throws Exception;

    public LineBasedParser getPreProcessor() {
        return null;
    }

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
