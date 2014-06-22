package io.github.achtern.AchternEngine.core.util;

/**
 * Utility for Integers
 */
public class UInteger {

    /**
     * Convert Integer[] array to int[] array
     * @param data The Integer array
     * @return The int array
     */
    public static int[] toIntArray(Integer[] data)
    {
        int[] result = new int[data.length];

        for(int i = 0; i < data.length; i++)
            result[i] = data[i];

        return result;
    }

}
