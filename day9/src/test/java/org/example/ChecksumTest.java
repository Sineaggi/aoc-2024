package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChecksumTest {
    @Test
    public void test() {
        var input = "0099811188827773336446555566..............";
        var checksum = Checksum.checksum(input);
        assertEquals(1928, checksum);
    }
}
