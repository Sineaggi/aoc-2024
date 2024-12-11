package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
    @Test
    public void testPart1() {
        Grid<String> input = Day4.parse(Day4.load());
        assertEquals(2642, Day4.part1(input));
    }

    @Test
    public void testPart2() {
        Grid<String> input = Day4.parse(Day4.load());
        assertEquals(1974, Day4.part2(input));
    }
}
