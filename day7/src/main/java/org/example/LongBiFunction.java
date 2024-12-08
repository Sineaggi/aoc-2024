package org.example;

@FunctionalInterface
public interface LongBiFunction {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long apply(long t, long u);
}
