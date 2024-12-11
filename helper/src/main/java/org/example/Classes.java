package org.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public class Classes {
    public static String loadResource(Class<?> clazz, String fileName) {
        try (var is = clazz.getResourceAsStream(fileName)) {
            return new String(Objects.requireNonNull(is).readAllBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
