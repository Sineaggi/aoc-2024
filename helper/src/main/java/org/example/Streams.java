package org.example;

import java.util.Collection;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    // copied from https://stackoverflow.com/a/46230233
    public static <L, R, T> Stream<T> zip(Stream<L> leftStream, Stream<R> rightStream, BiFunction<L, R, T> combiner) {
        Spliterator<L> lefts = leftStream.spliterator();
        Spliterator<R> rights = rightStream.spliterator();
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(Long.min(lefts.estimateSize(), rights.estimateSize()), lefts.characteristics() & rights.characteristics()) {
            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                return lefts.tryAdvance(left->rights.tryAdvance(right->action.accept(combiner.apply(left, right))));
            }
        }, false);
    }

    public value record Indexed<T>(T obj, int index) {

    }

    public static <T> Stream<Indexed<T>> index(Collection<T> collection) {
        var indices = IntStream.range(0, collection.size()).boxed();
        Stream<Indexed<T>> indexed = zip(indices, collection.stream(), (integer, t) -> new Indexed(t, integer));
        return indexed;
    }
}
