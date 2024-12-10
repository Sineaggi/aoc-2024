package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Day2 {

    private static final Logger logger = Logger.getLogger(Day2.class);

    public static void main(String[] args) throws Exception {
        List<List<Integer>> input;
        try (var is = Day2.class.getResourceAsStream("/input")) {
            input = parse(new String(is.readAllBytes()));
        }
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static List<List<Integer>> parse(String input) {
        return input.lines()
                .map(line -> {
                    var pattern = Pattern.compile("\\s");
                    return pattern.splitAsStream(line).map(Integer::parseInt).toList();
                }).toList();
    }

    private static int part1(List<List<Integer>> input) {
        return input.stream().mapToInt(Day2::safe).sum();
    }

    public static long part1impl2(List<List<Integer>> input) {
        return input.stream().filter(i -> {
            logger.debug("working on " + i);
            var diffs = i.stream().gather(Gatherers.windowSliding(2))
                    .map(l -> l.getFirst() - l.getLast()).toList();
            logger.debug(diffs);
            var safe = diffs.stream().allMatch(f -> f > 0 && safe(f)) ||
                    diffs.stream().allMatch(f -> f < 0 && safe(f));
            logger.debug(safe);
            return safe;
        }).count();
    }

    private static boolean safe(int i) {
        var safe = Math.abs(i) >= 1 && Math.abs(i) <= 3;
        logger.debug("safe(" + i + ")=" + safe);
        return safe;
    }

    private static int safe(List<Integer> list) {
        logger.trace(list);
        boolean inc = list.get(0) < list.get(1);
        logger.trace("inc = " + inc);
        for (int i = 0; i < list.size() - 1; i++) {
            logger.trace(i);
            int me = list.get(i);
            int next = list.get(i + 1);
            boolean contInc = me < next;
            if (Math.abs(next - me) < 1 || Math.abs(next - me) > 3 || (inc != contInc)) {
                return 0;
            }
        }
        return 1;
    }

    private static int part2(List<List<Integer>> input) {
        return input.stream().mapToInt(f -> {
            if (safe(f) == 1) {
                return 1;
            } else {
                for (int i = 0; i < f.size(); i++) {
                    if (safe(Lists.filterIndex(f, i)) == 1) {
                        return 1;
                    }
                }
                return 0;
            }
        }).sum();
    }
}
