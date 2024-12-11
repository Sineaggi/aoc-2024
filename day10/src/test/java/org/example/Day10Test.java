package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day10Test {
    @Test
    public void test() {
        var input = """
                ...0...
                ...1...
                ...2...
                6543456
                7.....7
                8.....8
                9.....9
                """;
        assertEquals(2, Day10.part1(Day10.parse(input)));
    }

    @Test
    public void test1() {
        var input = """
                ..90..9
                ...1.98
                ...2..7
                6543456
                765.987
                876....
                987....
                """;
        assertEquals(4, Day10.part1(Day10.parse(input)));
    }
}
