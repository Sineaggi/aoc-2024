package org.example;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
    @Test
    public void test() {
        var input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9
                """;
        System.out.println(Day2.part1impl2(Day2.parse(input)));
    }

    @Test
    public void testPart1() {
        List<List<Integer>> input = Day2.parse(Day2.load());
        assertEquals(591, Day2.part1impl2(input));
    }

    @Test
    public void testPart2() {
        List<List<Integer>> input = Day2.parse(Day2.load());
        assertEquals(621, Day2.part2(input));
    }
}
