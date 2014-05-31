package io.github.achtern.AchternEngine.core.util;

import java.util.ArrayList;
import java.util.List;

public class UList {

    public static List<String> flatten(List<List<String>> tree) {

        ArrayList<String> flat = new ArrayList<String>();

        for(List<String> sL : tree) {
            flat.addAll(sL);
        }
        return flat;
    }
}
