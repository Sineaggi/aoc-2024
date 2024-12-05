package org.example;

import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

public class Grid<E> {
    public value record Point(int x, int y) {
        Point offset(int xOffset, int yOffset) {
            return new Point(x + xOffset, y + yOffset);
        }
    }

    private final Map<Point, E> map;
    public Grid(Map<Point, E> map) {
        this.map = map;
    }

    public Stream<Map.Entry<Point, E>> entries() {
        return map.entrySet().stream();
    }

    public boolean contains(int x, int y, E test) {
        return contains(new Point(x, y), test);
    }

    public boolean contains(Point point, E test) {
        E e = map.get(point);
        if (e == null) {
            return false;
        }
        return test.equals(e);
    }

    public E get(int x, int y) {
        return get(new Point(x, y));
    }

    public E get(Point point) {
        return map.get(point);
    }
}
