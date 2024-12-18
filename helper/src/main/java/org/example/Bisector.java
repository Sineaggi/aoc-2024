package org.example;

import java.util.function.Function;
import java.util.function.Predicate;

public class Bisector {
    // todo: genrify, this should return the Point class for me
    public static <E> int bisect(int start, int stop, Function<Integer, E> function, Predicate<E> predicate) {
        //boolean found = false;
        while (true) {
            int range = stop - start;
            //if (range == 0) {
            //    found = true;
            //    throw new RuntimeException("found");
            //}
            int test = start + (range / 2);
            if (predicate.test(function.apply(test))) {
                if (range == 0) {
                    throw new RuntimeException("Bisect failed at test " + test);
                }
                stop = test;
            } else {
                if (range == 0) {
                    return test;
                }
                start = test;
            }
        }
    }
}
