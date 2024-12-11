package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day10Test {
    @Test
    public void test() {
        var input = """
                0123
                1234
                8765
                9876
                """;
        assertEquals(1928, Day10.part1(Day10.parse(input)));
    }
}
