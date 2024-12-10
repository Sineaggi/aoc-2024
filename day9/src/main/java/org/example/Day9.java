package org.example;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

public class Day9 {

    private static final Logger logger = Logger.getLogger(Day9.class);

    public static void main(String[] args) throws Exception {
      Drive input;
        try (var is = Day9.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = parse(str);
        }
        logger.info("part 1: " + part1(input));
        //logger.info("part 2: " + part2(input));
    }

    public static Drive parse(String input) {
        //input.
        return Drive.parse(input);
    }

    public static long part1(Drive input) {
        //System.out.println("input " + input);
        var reorder = input.reorder();
        //System.out.println(reorder);
        return reorder.checksum();
    }
}
