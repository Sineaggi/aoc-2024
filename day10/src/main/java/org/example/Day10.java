package org.example;

import java.util.Map;
import java.util.stream.Collectors;

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
            sum += trailhead(input, 0, trailhead);
        }
        return sum;
    }

    public static int trailhead(Grid<Integer> input, int index, Grid.Point currentLoc) {
        if (index == 9) {
            return 1;
        }
        var sum = 0;
        var up = currentLoc.offset(0, 1);
        if (input.contains(up, index + 1)) {
            sum += trailhead(input, index + 1, up);
        }
        var down = currentLoc.offset(0, -1);
        if (input.contains(down, index + 1)) {
            sum += trailhead(input, index + 1, down);
        }
        var right = currentLoc.offset(1, 0);
        if (input.contains(right, index + 1)) {
            sum += trailhead(input, index + 1, right);
        }
        var left = currentLoc.offset(-1, 0);
        if (input.contains(left, index + 1)) {
            sum += trailhead(input, index + 1, left);
        }
        return sum;
    }

    public static long part2(Grid<Integer> input) {
        return -1;
    }
}
