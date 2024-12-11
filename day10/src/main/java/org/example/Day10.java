package org.example;

import java.util.*;

public class Day10 {

    private static final Logger logger = Logger.getLogger(Day10.class);

    public static void main(String[] args) {
        Grid<Integer> input = parse(load());
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static String load() {
        return Classes.loadResource(Day10.class, "/input");
    }

    public static Grid<Integer> parse(String input) {
        return Streams.index(input.lines()).flatMap(indexedLine -> {
            int y = indexedLine.index();
            String line = indexedLine.obj();
            return Streams.index(line.codePoints().mapToObj(Character::toString)).map(indexedChar -> {
                int x = indexedChar.index();
                String ch = indexedChar.obj();
                if (ch.equals(".")) {
                    return Map.entry(new Grid.Point(x, y), -1);
                }
                return Map.entry(new Grid.Point(x, y), Integer.parseInt(ch));
            });
        }).collect(Grid.toGrid());
    }

    public static long part1(Grid<Integer> input) {
        // return -1;
        var trailheads = input.findAll(0);
        var sum = 0;
        for (var trailhead : trailheads) {
            var ends = trailheads(input, 0, trailhead);
            sum += ends.size();
        }
        return sum;
    }

    public static Set<Grid.Point> trailheads(Grid<Integer> input, int index, Grid.Point currentLoc) {
        if (!input.contains(currentLoc, index)) {
            return Collections.emptySet();
        } else {
            if (index == 9) {
                return Set.of(currentLoc);
            }
            Set<Grid.Point> set = new HashSet<>();
            set.addAll(trailheads(input, index + 1, currentLoc.offset(0, 1)));
            set.addAll(trailheads(input, index + 1, currentLoc.offset(0, -1)));
            set.addAll(trailheads(input, index + 1, currentLoc.offset(1, 0)));
            set.addAll(trailheads(input, index + 1, currentLoc.offset(-1, 0)));
            return set;
        }
    }

    public static int trails(Grid<Integer> input, int index, Grid.Point currentLoc) {
        if (!input.contains(currentLoc, index)) {
            return 0;
        } else {
            if (index == 9) {
                return 1;
            }
            int sum = 0;
            sum += trails(input, index + 1, currentLoc.offset(0, 1));
            sum += trails(input, index + 1, currentLoc.offset(0, -1));
            sum += trails(input, index + 1, currentLoc.offset(1, 0));
            sum += trails(input, index + 1, currentLoc.offset(-1, 0));
            return sum;
        }
    }

    public static long part2(Grid<Integer> input) {
        var trailheads = input.findAll(0);
        var sum = 0;
        for (var trailhead : trailheads) {
            sum += trails(input, 0, trailhead);
        }
        return sum;
    }
}
