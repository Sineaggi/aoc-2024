package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamsTest {
    @Test
    public void testUnorderedCombinations() {
        var combinations = Streams.unorderedCombinations(List.of(1, 2, 3)).collect(Collectors.toSet());
        assertEquals(Set.of(Set.of(1, 2), Set.of(1, 3), Set.of(2, 3)), combinations);
    }
}
