package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
    private final List<Day1.Pair> input = Day1.parse(Day1.load());

    @Test
    public void testPart1() {
        assertEquals(2166959, Day1.part1(input));
    }

    @Test
    public void testPart2() {
        assertEquals(23741109, Day1.part2(input));
    }
}
