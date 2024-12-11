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
                return Map.entry(new Grid.Point(x, y), Integer.parseInt(ch));
            });
        }).collect(Grid.toGrid());
    }

    public static long part1(Grid<Integer> input) {
        return -1;
    }

    public static long part2(Grid<Integer> input) {
        return -1;
    }
}
