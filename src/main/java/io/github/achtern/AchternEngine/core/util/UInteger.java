package io.github.achtern.AchternEngine.core.util;

public class UInteger {

    public static int[] toIntArray(Integer[] data)
    {
        int[] result = new int[data.length];

        for(int i = 0; i < data.length; i++)
            result[i] = data[i];

        return result;
    }

}
