package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class Day8Test {
    @Test
    public void test() {
        var input = """
                ..........
                ..........
                ..........
                ....a.....
                ..........
                .....a....
                ..........
                ..........
                ..........
                ..........
                """;
        assertEquals(2, Day8.part1(Day8.parse(input)));
    }

    @Test
    public void test2() {
        var points = Set.of(new Grid.Point(1, 3), new Grid.Point(4, 6));
        assertEquals(Set.of(new Grid.Point(-2, 0), new Grid.Point(7, 9)), Set.copyOf(Day8.anti(points)));
    }

    @Test
    public void testPart1() throws IOException {
        String input;
        try (var is = Day8.class.getResourceAsStream("/input")) {
            input = new String(is.readAllBytes());
        }
        assertEquals(247, Day8.part1(Day8.parse(input)));
    }
}
