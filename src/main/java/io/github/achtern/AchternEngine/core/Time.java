package io.github.achtern.AchternEngine.core;

public class Time {

    public static final long SECOND = 1000000000L;
    private static double delta;

    public static double getTime() {
        return (double) getNanoTime() / (double) SECOND;
    }

    public static long getNanoTime() {
        return System.nanoTime();
    }

    public static String getNanoString() {

        return "NanoTime: " + getNanoTime();

    }
}
