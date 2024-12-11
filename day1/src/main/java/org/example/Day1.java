package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {

    private static final Logger logger = Logger.getLogger(Day1.class);

    value record Pair(int left, int right) {
        int diff() {
            return Math.abs(left - right);
        }
    }

    public static void main(String[] args) {
        List<Pair> input = parse(load());
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static String load() {
        return Classes.loadResource(Day1.class, "/input");
    }

    public static List<Pair> parse(String str) {
        return str.lines()
                .map(line -> {
                    var split = line.split(" {3}");
                    return new Pair(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                }).toList();
    }

    public static int part1(List<Pair> input) {
        //var lefts = new ArrayList<>();
        //var rights = new ArrayList<>();
        //// var parsedInput = new ArrayList<Pair>();
        //var parsedInput = input.lines().map(line -> {
        //    var split = line.split(" {3}");
        //    //split[0]
        //    return new Pair(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        //}).toList();
        var left = input.stream().sorted(Comparator.comparingInt(f -> f.left()));
        var right = input.stream().sorted(Comparator.comparingInt(f -> f.right()));
        var sorted = Streams.zip(left, right, (l, r) -> new Pair(l.left(), r.right()));
        logger.trace(sorted);
        int out = sorted.mapToInt(Pair::diff).sum();
        logger.trace(out);
        return out;
    }

    public static int part2(List<Pair> input) {
        //var lefts = new ArrayList<>();
        //var rights = new ArrayList<>();
        //// var parsedInput = new ArrayList<Pair>();
        //var parsedInput = input.lines().map(line -> {
        //    var split = line.split(" {3}");
        //    //split[0]
        //    return new Pair(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        //}).toList();
        //var left = input.stream().sorted(Comparator.comparingInt(f -> f.left()));
        //var right = input.stream().sorted(Comparator.comparingInt(f -> f.right()));
        //var sorted = zip(left, right, (l, r) -> {
        //    return new Pair(l.left(), r.right());
        //}).toList();
        //System.out.println(sorted);
        //int out = sorted.stream().collect(Collectors.summingInt(Pair::diff));
        //System.out.println(out);
        //Set<Integer> inRight = input.stream().map(Pair::right).collect(Collectors.toSet());
        Set<Integer> inLeft = input.stream().map(Pair::left).collect(Collectors.toSet());
        Map<Integer, List<Integer>> out = input.stream().map(Pair::left)//.filter(inRight::contains)
                .collect(Collectors.groupingBy(Function.identity()));
        //Map<Integer, Integer> lefts = foo(input, Pair::left);
        Map<Integer, Integer> rights = foo(input, Pair::right);
        //trace(lefts);
        logger.trace(rights);
        // input.
        int ret = input.stream().mapToInt(f -> {
            int value = f.left();
            Integer count = rights.get(value);
            if (count == null) {return 0;}
            return value * count;
        }).sum();
        return ret;
    }

    public static Map<Integer, Integer> foo(List<Pair> input, Function<Pair, Integer> map) {
        Map<Integer, List<Integer>> out = input.stream().map(map)//.filter(inRight::contains)
                .collect(Collectors.groupingBy(Function.identity()));
        Map<Integer, Integer> outs = out.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return outs;
    }
}
