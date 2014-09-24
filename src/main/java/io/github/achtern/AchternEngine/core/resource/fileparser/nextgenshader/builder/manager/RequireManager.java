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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.builder.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class RequireManager {

    /**
     * This keeps track off all require statements.
     * Maps name to type (name => type)
     * This order seems confusing, but is the only way
     * to avoid overrides when requiring multiple values
     * of the same type. The name however is unique!
     */
    @Getter protected Map<String, String> requires;

    public RequireManager() {
        this.requires = new HashMap<String, String>();
    }

    /**
     * Adds a new require statement!
     * @param type The Type!
     * @param name The Name!
     */
    public void add(String type, String name) {
        getRequires().put(name, type);
    }
}
