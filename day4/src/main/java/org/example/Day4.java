package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4 {

    private static final Logger logger = Logger.getLogger(Day4.class);

    public static void main(String[] args) throws Exception {
        Map<Point, String> input;
        try (var is = Day4.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = Streams.index(str.lines()).flatMap(indexedLine -> {
                int y = indexedLine.index();
                return Streams.index(indexedLine.obj().chars().boxed()).map(indexedChar -> {
                        int x = indexedChar.index();
                        return Map.entry(new Point(x, y), new String(Character.toChars(indexedChar.obj())));
                });
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            logger.debug(input);
        }
        logger.info("part 1: " + part1(input));
    }

    value record Point(int x, int y) {
        Point offset(int xOffset, int yOffset) {
            return new Point(x + xOffset, y + yOffset);
        }
    }

    private static int part1(Map<Point, String> input) {
        return input.entrySet()
                .stream()
                .mapToInt(entry -> find(input, entry.getKey(), "XMAS"))
                .sum();
    }

    private static int find(Map<Point, String> input, Point point, String string) {
        String charAtPoint = input.get(point);
        String lookingFor = new String(Character.toChars(string.charAt(0)));
        //System.out.println(lookingFor);
        String rest = string.substring(1);
        logger.debug("at point " + charAtPoint + ", looking for: " + lookingFor + ", rest: " + rest);
        //System.out.println("looking for: " + string + ", next " + rest);
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

    private static int findContinue(Map<Point, String> input, Point prevPoint, String string, Function<Point, Point> directionIncFunction) {
        Point point = directionIncFunction.apply(prevPoint);
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
}
