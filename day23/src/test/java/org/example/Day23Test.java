package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day18Test {
    @Test
    public void test() {
        var input = "0 1 10 99 999";
        var stones = Day18.parse(input);
        assertEquals("1 2024 1 0 9 9 2021976", stones.blink().toString());
    }

    @Test
    public void test2() {
        var input = "125 17";
        var stones = Day18.parse(input);
        assertEquals("253000 1 7", (stones = stones.blink()).toString());
        assertEquals("253 0 2024 14168", (stones = stones.blink()).toString());
        assertEquals("512072 1 20 24 28676032", (stones = stones.blink()).toString());
        assertEquals("512 72 2024 2 0 2 4 2867 6032", (stones = stones.blink()).toString());
        assertEquals("1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32", (stones = stones.blink()).toString());
        assertEquals("2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2", (stones = stones.blink()).toString());
    }

    @Test
    public void test2try2() {
        var input = "125 17";
        var stones = Day18.parse(input);
        assertEquals(2, stones.blonk(0));
        assertEquals(3, stones.blonk(1));
        assertEquals(4, stones.blonk(2));
        assertEquals(5, stones.blonk(3));
        assertEquals(9, stones.blonk(4));
        assertEquals(13, stones.blonk(5));
        assertEquals(22, stones.blonk(6));
    }

    @Test
    public void test3() {
        var input = "125 17";
        var stones = Day18.parse(input);
        assertEquals(55312, stones.blink(25).count());
    }

    @Test
    public void testPart1() {
        var input = Day18.load();
        var stones = Day18.parse(input);
        assertEquals(173138, stones.blink(25).count());
    }
}
