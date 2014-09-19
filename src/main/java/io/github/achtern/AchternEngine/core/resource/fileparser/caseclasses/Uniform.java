/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Christian Gärtner
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

package io.github.achtern.AchternEngine.core.resource.fileparser.caseclasses;

import io.github.achtern.AchternEngine.core.rendering.binding.UniformManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class Uniform extends Variable {

    protected int location = -1;

    protected Object value;

    protected boolean shouldSet = true;

    protected SetStrategy setStrategy;


    public Uniform(Variable from) {
        this(from.getType(), from.getName());
    }

    public Uniform(String type, String name) {
        super(type, name);
    }


    /**
     * Equivalent to {@link #isShouldSet()}
     * @return shouldSet
     */
    public boolean shouldSet() {
        return isShouldSet();
    }

    @Override
    public String toString() {
        return "[" + getType() + " " + getName() + "]";
    }

    public static interface SetStrategy {

        public void set(Uniform uniform, UniformManager uniformManager);

    }
}
