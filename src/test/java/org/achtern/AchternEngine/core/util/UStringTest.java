package org.achtern.AchternEngine.core.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UStringTest {

    @Test
    public void testRemoveEmptyFromArray() throws Exception {

        String[] data = new String[] {
                "A",
                "A",
                " ",
                "   ",
                "\t",
                "\n",
                "\r\n",
                ""
        };

        String[] expected = new String[] {
                "A",
                "A",
                " ",
                "   ",
                "\t",
                "\n",
                "\r\n",
        };

        assertEquals(expected.length, UString.removeEmptyFromArray(data).length);



    }
}