package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {
    public static <E> List<E> filterIndex(List<E> list, int index) {
        var copy = new ArrayList<>(list);
        copy.remove(index);
        return Collections.unmodifiableList(copy);
    }
}
