package org.example;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Streams {
    // copied from https://stackoverflow.com/a/46230233
    // todo: support either list terminating early
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
        Stream<Indexed<T>> indexed = zip(collection.stream(), indices, (t, index) -> new Indexed(t, index));
        return indexed;
    }

    public static <T> Stream<Indexed<T>> index(Stream<T> stream) {
        var indices = IntStream.iterate(0, i -> i + 1).boxed();
        Stream<Indexed<T>> indexed = zip(stream, indices, (t, index) -> new Indexed(t, index));
        return indexed;
    }

    // https://stackoverflow.com/a/34159174
    public static <T> Stream<List<T>> sliding(List<T> list, int size) {
        if(size > list.size())
            return Stream.empty();
        return IntStream.range(0, list.size()-size+1)
                .mapToObj(start -> list.subList(start, start+size));
    }

    // https://stackoverflow.com/a/42177178
    public static <T> Stream<List<T>> combinations(List<T> list) {
        return list.stream()
                .flatMap(obj1 -> list.stream().map(obj2 -> List.of(obj1, obj2)));
    }

    public static <T> Stream<Set<T>> unorderedCombinations(List<T> list) {
        return list.stream()
                .flatMap(obj1 -> list.stream().map(obj2 -> {
                    if (obj1.equals(obj2)) {
                        return null;
                    } else {
                        return Set.of(obj1, obj2);
                    }
                }).filter(Objects::nonNull))
                .collect(Collectors.toSet())
                .stream();
    }
}
