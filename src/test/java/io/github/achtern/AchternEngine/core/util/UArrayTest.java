package io.github.achtern.AchternEngine.core.util;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UArrayTest {

    @Test
    public void testJoin() throws Exception {
        String[] array1 = new String[] {
                "1",
                "2",
                "3",
                " ",
                "4"
        };

        String[] array2 = new String[] {
                "A",
                "B",
                "C",
                " ",
                "D",
        };

        assertEquals("123 4", UArray.join(array1));
        assertEquals("ABC D", UArray.join(array2));
    }

    public void testConcat() {

        Integer[] arr1 = new Integer[] {
                1, 2, 3
        };

        Integer[] arr2 = new Integer[] {
                4, 5, 6
        };

        Integer[] expected = new Integer[] {
                1, 2, 3, 4, 5, 6
        };

        assertArrayEquals(expected, UArray.concat(arr1, arr2));

    }
}