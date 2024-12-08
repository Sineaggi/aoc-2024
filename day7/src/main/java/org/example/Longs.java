package org.example;

public class Longs {
    public static long multiply(long a, long b) {
        return a * b;
    }
    public static long concat(long a, long b) {
        return Long.parseLong("" + a + b);
    }

    public static long concat2(long a, long b) {
        long digits = ((long)Math.log10(b) + 1);
        return ((long)Math.pow(10, digits) * a) + b;
    }
}
