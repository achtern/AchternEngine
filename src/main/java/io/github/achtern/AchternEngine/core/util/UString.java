package io.github.achtern.AchternEngine.core.util;

import java.util.ArrayList;

public class UString {

    public static String[] removeEmptyFromArray(String[] strings) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].equals("")) {
                result.add(strings[i]);
            }
        }

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }
}
