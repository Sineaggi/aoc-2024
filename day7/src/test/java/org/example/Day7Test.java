package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Day7Test {
    @Test
    public void test() {
        var input = """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20
                """;
        assertEquals(3749, Day7.part1(Day7.parse(input)));
    }

    @Test
    public void test2() {
        var input = """
                156: 15 6
                """;
        assertEquals(156, Day7.part2(Day7.parse(input)));

    }

    @Test
    public void testPart1() throws IOException {
        String input;
        try (var is = Day7.class.getResourceAsStream("/input")) {
            input = new String(is.readAllBytes());
        }
        assertEquals(1289579105366L, Day7.part1(Day7.parse(input)));
    }

    @Test
    public void testPart2() throws IOException {
        String input;
        try (var is = Day7.class.getResourceAsStream("/input")) {
            input = new String(is.readAllBytes());
        }
        assertEquals(92148721834692L, Day7.part2(Day7.parse(input)));
        System.out.println(Long.valueOf("21658230055"));
    }
}
