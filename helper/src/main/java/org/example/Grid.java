package org.example;

import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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

    public boolean has(Point point) {
        return map.containsKey(point);
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

    public Point max() {
        var maxX = map.keySet().stream().mapToInt(Point::x).max().orElseThrow();
        var maxY = map.keySet().stream().mapToInt(Point::y).max().orElseThrow();
        return new Point(maxX, maxY);
    }

    public record Node(Point point, int weight) {

    }

    public int shortestDistance(Point a, Point b, Function<Point, List<Node>> adjacent) {

        int INF = 999999999;
        //System.out.println(map.get(b).toString());
        Map<Point, Integer> dist = new HashMap<>(map.entrySet().stream().map(entry -> Map.entry(entry.getKey(), INF)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        dist.put(a, 0);

        // Comparator lambda function that enables the priority queue to store the nodes
        // based on the distance in the ascending order.
        Comparator<Node> nodeDistComparator = (obj1, obj2) -> Integer.compare(obj2.weight(), obj1.weight());

        PriorityQueue<Node> pq = new PriorityQueue<>(nodeDistComparator);

        pq.add(new Node(a, 0));

        while (!pq.isEmpty()) {
            Node u = pq.poll();

            Point currentSource  = u.point();

            for (Node node : adjacent.apply(currentSource)) {
                Point adjacentPoint = node.point();
                int lengthToAdjacentNode = node.weight();

                if (dist.get(adjacentPoint) > lengthToAdjacentNode + dist.get(currentSource)) {
                    if (dist.get(adjacentPoint) != INF) {
                        pq.remove(new Node(adjacentPoint, dist.get(adjacentPoint)));
                    }
                    dist.put(adjacentPoint, lengthToAdjacentNode + dist.get(currentSource));
                    pq.add(new Node(adjacentPoint, dist.get(adjacentPoint)));
                }
            }
        }

        return dist.get(b);
    }

    static <E> Collector<Map.Entry<Point, E>, ?, Grid<E>> toGrid() {
        return Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue), Grid::new);
    }
}
