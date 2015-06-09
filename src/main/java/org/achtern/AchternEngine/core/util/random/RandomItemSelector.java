/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.achtern.AchternEngine.core.util.random;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomItemSelector {

    /**
     * Returns a random item from the given list
     * @param items source
     * @param <T> type of the items
     * @return random item from the given items
     */
    public static <T> T getRandom(List<T> items) {
        return items.get(RandomNumberGenerator.getRandom(items.size()));
    }

    public static <T> T getRandom(Map<T, Float> items) {
        return getRandom(items, 100);
    }

    /**
     * This method accepts a map of items and a float value.
     *  This float value represents a probability.
     *
     * This is very similar to {@link #getRandom(java.util.List)}, but now
     *  you can define a probability for each item. This float value should be
     *  in the interval of [0,1], where 0 is 0% and 1 is 100%
     *
     * The resolution parameter defines how fine the percentage can be.
     *
     * For example: with a resolution of 100, the lowest percentage can be 1% and
     *  with 100 0.1% and so forth.
     *
     * NOTE: This method works by creating a List of the size of the resolution. Therefor
     *  large resolutions will create large lists on the heap!
     *
     * @param items items and their probability
     * @param resolution resolution the random getting
     * @param <T> type of the items
     * @return random item from the given items
     */
    public static <T> T getRandom(Map<T, Float> items, int resolution) {
        List<T> source = new ArrayList<T>(resolution);

        for (Map.Entry<T, Float> entry : items.entrySet()) {
            for (int i = 0; i < (int) (resolution * entry.getValue()); i++) {
                source.add(entry.getKey());
            }
        }

        return getRandom(source);
    }

}
