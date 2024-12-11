package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {
    @Test
    public void testPart1() {
        var input = Day3.load();
        assertEquals(184576302, Day3.part1(input));
    }

    @Test
    public void testPart2() {
        var input = Day3.load();
        assertEquals(118173507, Day3.part2(input));
    }
}
