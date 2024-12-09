package org.example;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
    public Grid() {
        this.map = new HashMap<>();
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        var x = map.keySet().stream().mapToInt(Point::x).max().orElseThrow() + 1;
        var y = map.keySet().stream().mapToInt(Point::y).max().orElseThrow() + 1;
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                sb.append(map.get(new Point(i, j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String debug() {
        return map.toString();
    }

    public Point find(E s) {
        return map.entrySet().stream().filter(i -> i.getValue().equals(s)).reduce((a, b) -> {
            throw new IllegalStateException("Multiple elements: " + a + ", " + b);
        }).map(Map.Entry::getKey).orElseThrow();
    }

    public Grid<E> replace(Grid.Point point, E x) {
        var newMap = new HashMap<>(map);
        newMap.put(point, x);
        return new Grid<>(newMap);
    }

    public Grid<E> replaceAll(Set<Grid.Point> points, E x) {
        var newMap = new HashMap<>(map);
        points.forEach(point -> {
            newMap.put(point, x);
        });
        return new Grid<>(newMap);
    }

    public List<Point> findAll(E s) {
        return map.entrySet().stream().filter(i -> i.getValue().equals(s)).map(Map.Entry::getKey).toList();
    }

    public Grid.Point max() {
        var maxX = map.keySet().stream().mapToInt(Point::x).max().orElseThrow();
        var maxY = map.keySet().stream().mapToInt(Point::y).max().orElseThrow();
        return new Grid.Point(maxX, maxY);
    }
}
