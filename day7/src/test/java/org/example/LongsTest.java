package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongsTest {
    @Test
    public void testMultiply() {
        assertEquals(6 * 8, Longs.multiply(6, 8));
    }

    @Test
    public void testConcat() {
        assertEquals(156, Longs.concat(15, 6));
    }

    @Test
    public void testConcat2() {
        assertEquals(156, Longs.concat2(15, 6));
    }

    @Test
    public void testConcat3() {
        assertEquals(153612, Longs.concat2(153, 612));
    }
}
