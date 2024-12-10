package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

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
    public void testPart1() throws IOException {
        String input;
        try (var is = Day9.class.getResourceAsStream("/input")) {
            input = new String(is.readAllBytes());
        }
        assertEquals(6349606724455L, Day9.part1(Day9.parse(input)));
    }
}
