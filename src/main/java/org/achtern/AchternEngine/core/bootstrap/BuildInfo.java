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

package org.achtern.AchternEngine.core.bootstrap;

import java.io.IOException;
import java.util.Properties;

/**
 * BuildInfo provides information about the engine at runtime, like the version and properties of the latest git commit.
 *
 * This class uses 'git-commit-id-plugin' maven-plugin and reads it's output.
 */
public class BuildInfo {

    public static final String ENGINE_VERSION = "0.5-SNAPSHOT";

    private static BuildInfo instance;

    public static BuildInfo load() throws IOException {
        if (instance == null) {

            Properties properties = new Properties();
            properties.load(BuildInfo.class.getClassLoader().getResourceAsStream("git.properties"));

            instance = new BuildInfo(properties);

        }

        return instance;
    }

    public static String get() {
        try {
            return load().toString();
        } catch (IOException e) {
            // Should not happen
            e.printStackTrace();
        }
        return "Error loading git.properties";
    }

    protected String branch;
    protected String describe;
    protected String describeShort;
    protected String commitId;
    protected String buildUserName;
    protected String buildUserEmail;
    protected String buildTime;
    protected String commitUserName;
    protected String commitUserEmail;
    protected String commitMessageShort;
    protected String commitMessageFull;
    protected String commitTime;


    private BuildInfo(Properties properties) {
        this.branch = properties.get("git.branch").toString();
        this.describe = properties.get("git.commit.id.describe").toString();
        this.describeShort = properties.get("git.commit.id.describe-short").toString();
        this.commitId = properties.get("git.commit.id").toString();
        this.buildUserName = properties.get("git.build.user.name").toString();
        this.buildUserEmail = properties.get("git.build.user.email").toString();
        this.buildTime = properties.get("git.build.time").toString();
        this.commitUserName = properties.get("git.commit.user.name").toString();
        this.commitUserEmail = properties.get("git.commit.user.email").toString();
        this.commitMessageShort = properties.get("git.commit.message.short").toString();
        this.commitMessageFull = properties.get("git.commit.message.full").toString();
        this.commitTime = properties.get("git.commit.time").toString();
    }

    @Override
    public String toString() {
        return "AchternEngine v" + ENGINE_VERSION + ";\n" +
                "Build on " + getBuildTime() +
                " by " + getBuildUserName() +
                " git commit = " + getDescribe();
    }

    public String getBranch() {
        return branch;
    }

    public String getDescribe() {
        return describe;
    }

    public String getDescribeShort() {
        return describeShort;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getBuildUserName() {
        return buildUserName;
    }

    public String getBuildUserEmail() {
        return buildUserEmail;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public String getCommitUserName() {
        return commitUserName;
    }

    public String getCommitUserEmail() {
        return commitUserEmail;
    }

    public String getCommitMessageShort() {
        return commitMessageShort;
    }

    public String getCommitMessageFull() {
        return commitMessageFull;
    }

    public String getCommitTime() {
        return commitTime;
    }
}
