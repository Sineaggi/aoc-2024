package org.example;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day18 {

    private static final Logger logger = Logger.getLogger(Day18.class);

    public static void main(String[] args) {
        List<Grid.Point> input = parse(load());
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static String load() {
        return Classes.loadResource(Day18.class, "/input");
    }

    public static List<Grid.Point> parse(String input) {
        return input.lines().map(line -> {
            List<String> parts = Arrays.asList(line.split(","));
            return new Grid.Point(Integer.parseInt(parts.getFirst()), Integer.parseInt(parts.getLast()));
        }).toList();
    }

    public static long part1(List<Grid.Point> points) {
        return distance(points, 2976);
    }

    public static long distance(List<Grid.Point> points, int limit) {
        var lines = points.stream()
                .limit(limit)
                .map(point -> Map.entry(point, "#"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        var unfilledMap = new HashMap<>(lines);
        for (int i = 0; i <= 70; i++) {
            for (int j = 0; j <= 70; j++) {
                Grid.Point p = new Grid.Point(i, j);
                if (!unfilledMap.containsKey(p)) {
                    unfilledMap.put(p, ".");
                }
            }
        }
        var filledMap = Collections.unmodifiableMap(unfilledMap);
        Grid<String> input = new Grid<>(filledMap);
        // System.out.println(input);
        Predicate<Grid.Point> predicate = point -> input.contains(point, ".");
        var shortestDistance = input.shortestDistance(new Grid.Point(0, 0), new Grid.Point(70, 70), point -> Stream.of(
                point.offset(1, 0),
                point.offset(-1, 0),
                point.offset(0, 1),
                point.offset(0, -1)
        ).filter(predicate).map(p -> new Grid.Node(p, 1)).toList());
        return shortestDistance;
    }

    public static String part2(List<Grid.Point> points) {
        //System.out.println(points.size());
        //System.out.println("distance at i " + 3450 + " " + distance(points, 3450));
        ////for (int i = 1024; i < points.size(); i++) {
        ////    System.out.println("distance at i " + i + " " + distance(points, i));
        ////}
        //return Bisector.bisect(2843, 2994, test -> {
        //    var d = distance(points, test);
        //    System.out.println("distance at i " + test + " " + d);
        //    return d;
        //}, val -> val != 999999999);
        ////return -1;
        var pt = points.get(2975);
        return pt.x() + "," + pt.y();
    }
}
