package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
    @Test
    public void testPart1() throws IOException {
        Grid<String> input;
        try (var is = Day4.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = Day4.parse(str);
        }
        assertEquals(2642, Day4.part1(input));
    }

    @Test
    public void testPart2() throws IOException {
        Grid<String> input;
        try (var is = Day4.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = Day4.parse(str);
        }
        assertEquals(1974, Day4.part2(input));
    }
}
