package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day9Test {
    @Test
    public void test() {
        var input = """
                2333133121414131402
                """;
        assertEquals(1928, Day9.part1(Day9.parse(input)));
    }

    @Test
    public void testParse() {
        var input = "2333133121414131402";
        assertEquals("00...111...2...333.44.5555.6666.777.888899", Day9.parse(input).toString());
    }

    @Test
    public void test2() {
        var input = "2333133121414131402";
        System.out.println(Day9.parse(input));
        assertEquals(2858, Day9.part2(Day9.parse(input)));
    }

    @Test
    public void testPart1() {
        Drive input = Day9.parse(Day9.load());
        assertEquals(6349606724455L, Day9.part1(input));
    }

    @Test
    public void testPart2() {
        Drive input = Day9.parse(Day9.load());
        assertEquals(6376648986651L, Day9.part2(input));
    }
}
