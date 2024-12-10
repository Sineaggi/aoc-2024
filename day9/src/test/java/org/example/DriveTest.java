package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DriveTest {
    @Test
    public void testDrive1() {
        var input = "12345";
        // System.out.println(Drive.parse(input));
        assertEquals("0..111....22222", Drive.parse(input).toString());
    }

    @Test
    public void testDrive2() {
        var input = "2333133121414131402";
        System.out.println(Drive.parse(input));
        assertEquals("00...111...2...333.44.5555.6666.777.888899", Drive.parse(input).toString());
    }

    @Test
    public void testDrive4() {
        var input = "12345";
        System.out.println(Drive.parse(input));
        System.out.println(Drive.parse(input).reorder());
        assertEquals("022111222......", Drive.parse(input).reorder().toString());
    }

    @Test
    public void testDrive5() {
        var input = "2333133121414131402";
        assertEquals("0099811188827773336446555566..............", Drive.parse(input).reorder().toString());
    }

    @Test
    public void testDrive6() {
        var input = "2333133121414131402";
        assertEquals(1928, Drive.parse(input).reorder().checksum());
    }
}
