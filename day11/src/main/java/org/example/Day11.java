package org.example;

import java.util.regex.Pattern;

public class Day11 {

    private static final Logger logger = Logger.getLogger(Day11.class);

    public static void main(String[] args) {
        Stones input = parse(load());
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static String load() {
        return Classes.loadResource(Day11.class, "/input");
    }

    public static Stones parse(String input) {
        var pattern = Pattern.compile("\\s");
        var longs = pattern.splitAsStream(input).map(Long::parseLong).toList();
        return Stones.ofLongs(longs);
    }

    public static long part1(Stones input) {
        // System.out.println(input);
        if (false) {
            return input.blink(25).count();
        } else {
            return input.blonk(25);
        }
    }

    public static long part2(Stones input) {
        return input.blonk(75);
    }
}
