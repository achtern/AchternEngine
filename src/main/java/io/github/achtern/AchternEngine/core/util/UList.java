package io.github.achtern.AchternEngine.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility for Lists
 */
public class UList {

    /**
     * Flatten a list of 2 times nested Items (T)
     * @param tree The tree
     * @return the flattend list
     */
    public static <T> List<T> flattenS(List<List<T>> tree) {
        ArrayList<T> flat = new ArrayList<T>();

        for (List<T> l : tree) {
            flat.addAll(l);
        }

        return flat;
    }
}
