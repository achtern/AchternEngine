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

package io.github.achtern.AchternEngine.core.resource.fileparser.nextgenshader.statement;


import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BasicStatementParser implements StatementParser {

    public interface GroupProvider {

        public int get();

    }

    @Getter protected final Pattern pattern;

    @Getter protected String target;
    private Matcher targetMatcher;

    protected BasicStatementParser(Pattern pattern) {
        this.pattern = pattern;
    }

    protected BasicStatementParser(Pattern pattern, String target) {
        this(pattern);
        this.target = target;
    }

    @Override
    public boolean test(String line) {
        return this.pattern.matcher(line).find();
    }

    public boolean test() {
        return getTargetMatcher().find();
    }

    protected String getGroup(String input, int i) {
        Matcher m = this.pattern.matcher(input);
        return getGroup(m, i);
    }

    protected String getGroup(String input, GroupProvider provider) {
        return getGroup(input, provider.get());
    }

    protected String getGroup(int i) {
        return getGroup(getTargetMatcher(), i);
    }

    protected String getGroup(GroupProvider provider) {
        return getGroup(provider.get());
    }

    public Matcher getTargetMatcher() {
        if (targetMatcher == null) {
            targetMatcher = getPattern().matcher(getTarget());
        }

        return targetMatcher;
    }

    private String getGroup(Matcher m, int i) {
        if (m.matches()) {
            return m.group(i);
        } else {
            return "";
        }
    }
}
