package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    private static final Logger logger = Logger.getLogger(Day7.class);

    public static void main(String[] args) throws Exception {
        Map<Long, List<Long>> input;
        try (var is = Day7.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = parse(str);
        }
        logger.info("part 1: " + part1(input)); // 4722
        logger.info("part 2: " + part2(input)); // 1602
    }

    public static Map<Long, List<Long>> parse(String input) {
        return input.lines().map(indexedLine -> {
            var split = indexedLine.split(": ");
            var values = Arrays.stream(split[1].split(" ")).map(Long::valueOf).toList();
            return Map.entry(Long.valueOf(split[0]), values);
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static long part1(Map<Long, List<Long>> input) {
        System.out.println(input);
        return test(input, List.of(Long::sum, Longs::multiply));
    }

    public static long part2(Map<Long, List<Long>> input) {
        System.out.println(input);
        return test(input, List.of(Long::sum, Longs::multiply, Longs::concat2));
    }

    public static long test(Map<Long, List<Long>> input, List<LongBiFunction> ops) {
        long sum = 0;
        for (Map.Entry<Long, List<Long>> entry : input.entrySet()) {
            long testValue = entry.getKey();
            List<Long> numbers = entry.getValue();
            Set<Long> combinations = new HashSet<>();
            for (var number : numbers) {
                if (combinations.isEmpty()) {
                    combinations.add(number);
                } else {
                    Set<Long> newCombinations = new HashSet<>();
                    for (var existingCombination : combinations) {
                        for (var op : ops) {
                            newCombinations.add(op.apply(existingCombination, number));
                        }
                    }
                    combinations = newCombinations;
                }
            }
            if (combinations.contains(testValue)) {
                sum += testValue;
            }
        }
        return sum;
    }
}
