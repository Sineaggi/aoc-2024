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

    @Test
    public void test2() {
        var input = """
                10..9..
                2...8..
                3...7..
                4567654
                ...8..3
                ...9..2
                .....01
                """;
        assertEquals(3, Day10.part1(Day10.parse(input)));
    }

    @Test
    public void test3() {
        var input = """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """;
        assertEquals(36, Day10.part1(Day10.parse(input)));
    }

    @Test
    public void testPart1() {
        var input = Day10.parse(Day10.load());
        assertEquals(659, Day10.part1(input));
    }

    @Test
    public void test4() {
        var input = """
                89010123
                78121874
                87430965
                96549874
                45678903
                32019012
                01329801
                10456732
                """;
        assertEquals(81, Day10.part2(Day10.parse(input)));
    }

    @Test
    public void testPart2() {
        var input = Day10.parse(Day10.load());
        assertEquals(1463, Day10.part2(input));
    }
}
