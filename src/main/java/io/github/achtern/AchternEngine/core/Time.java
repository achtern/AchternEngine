package io.github.achtern.AchternEngine.core;

public class Time {

    private static final long SECOND = 1000000000L;
    private static double delta;

    public static double getTime() {
        return (double) getNanoTime() / (double) SECOND;
    }

    public static long getNanoTime() {
        return System.nanoTime();
    }
}
