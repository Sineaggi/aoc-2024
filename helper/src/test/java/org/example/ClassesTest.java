package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassesTest {
    @Test
    public void test() {
        var expected = """
                test
                """;
        assertEquals(expected, Classes.loadResource(ClassesTest.class, "/input"));
    }
}
