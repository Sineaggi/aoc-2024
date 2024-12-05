package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4 {

    private static final Logger logger = Logger.getLogger(Day4.class);

    public static void main(String[] args) throws Exception {
        Grid<String> input;
        try (var is = Day4.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            var map = Streams.index(str.lines()).flatMap(indexedLine -> {
                int y = indexedLine.index();
                return Streams.index(indexedLine.obj().chars().boxed()).map(indexedChar -> {
                        int x = indexedChar.index();
                        return Map.entry(new Grid.Point(x, y), new String(Character.toChars(indexedChar.obj())));
                });
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            input = new Grid<>(map);
            logger.debug(input);
        }
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    private static int part1(Grid<String> input) {
        return input.entries()
                .mapToInt(entry -> find(input, entry.getKey()))
                .sum();
    }

    private static int find(Grid<String> input, Grid.Point point) {
        String charAtPoint = input.get(point);
        String lookingFor = "X";
        String rest = "MAS";
        logger.debug("at point " + charAtPoint + ", looking for: " + lookingFor + ", rest: " + rest);
        if (charAtPoint != null && charAtPoint.equals(lookingFor)) {
            logger.debug("Found X at point: " + point);
            return findContinue(input, point, rest, p -> p.offset(1, 0))
                    + findContinue(input, point, rest, p -> p.offset(1, 1))
                    + findContinue(input, point, rest, p -> p.offset(0, 1))
                    + findContinue(input, point, rest, p -> p.offset(-1, 1))
                    + findContinue(input, point, rest, p -> p.offset(-1, 0))
                    + findContinue(input, point, rest, p -> p.offset(-1, -1))
                    + findContinue(input, point, rest, p -> p.offset(0, -1))
                    + findContinue(input, point, rest, p -> p.offset(1, -1))
                    ;
        } else {
            return 0;
        }
    }

    private static int findContinue(Grid<String> input, Grid.Point prevPoint, String string, Function<Grid.Point, Grid.Point> directionIncFunction) {
        Grid.Point point = directionIncFunction.apply(prevPoint);
        String charAtPoint = input.get(point);
        String lookingFor = new String(Character.toChars(string.charAt(0)));
        logger.debug(lookingFor);
        String rest = string.substring(1);
        logger.debug("looking for: " + string + ", next " + rest);
        logger.debug("at point " + charAtPoint + ", looking for: " + lookingFor + ", rest: " + rest);
        if (charAtPoint != null && charAtPoint.equals(lookingFor)) {
            if (rest.isEmpty()) {
                logger.debug("found! last index " + point);
                return 1;
            }
            return findContinue(input, point, rest, directionIncFunction);
        } else {
            return 0;
        }
    }

    private static int part2(Grid<String> input) {
        return input.entries()
                .mapToInt(entry -> find2(input, entry.getKey()))
                .sum();
    }

    private static int find2(Grid<String> input, Grid.Point point) {
        String charAtPoint = input.get(point);
        String lookingFor = "A";
        if (charAtPoint != null && charAtPoint.equals(lookingFor)) {
            logger.debug("Found A at point: " + point);
            if ((input.contains(point.offset(1, 1), "M") && input.contains(point.offset(-1, 1), "M") && input.contains(point.offset(-1, -1), "S") && input.contains(point.offset(1, -1), "S")) ||
                (input.contains(point.offset(1, 1), "M") && input.contains(point.offset(-1, 1), "S") && input.contains(point.offset(-1, -1), "S") && input.contains(point.offset(1, -1), "M")) ||
                (input.contains(point.offset(1, 1), "S") && input.contains(point.offset(-1, 1), "M") && input.contains(point.offset(-1, -1), "M") && input.contains(point.offset(1, -1), "S")) ||
                (input.contains(point.offset(1, 1), "S") && input.contains(point.offset(-1, 1), "S") && input.contains(point.offset(-1, -1), "M") && input.contains(point.offset(1, -1), "M"))) {
                return 1;
            }
        }
        return 0;
    }
}
