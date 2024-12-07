package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    private static final Logger logger = Logger.getLogger(Day6.class);



    public static void main(String[] args) throws Exception {
        Grid<String> mapLocGrid;
        try (var is = Day6.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            var map = Streams.index(str.lines()).flatMap(indexedLine -> {
                int y = indexedLine.index();
                String line = indexedLine.obj();
                return Streams.index(line.chars().boxed()).map(indexedChar -> {
                    int x = indexedChar.index();
                    String ch = new String(Character.toChars(indexedChar.obj()));
                    return Map.entry(new Grid.Point(x, y), ch);
                });
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            mapLocGrid = new Grid<>(map);
        }
        logger.info("part 1: " + part1(mapLocGrid));
    }

    public static int part1(Grid<String> mapLocGrid) {
        System.out.println(mapLocGrid);
        String dir = "^";
        var point = mapLocGrid.find(dir);
        int xOff = 0;
        int yOff = -1;
        Set<Grid.Point> points = new HashSet<>();
        points.add(point); // add guard's starting location
        while (true) {
            var newPoint = point.offset(xOff, yOff);
            if (mapLocGrid.contains(newPoint, ".") || mapLocGrid.contains(newPoint, "^")) {
                point = newPoint;
                points.add(point);
                continue;
            } else if (mapLocGrid.contains(newPoint, "#")) {
                switch (dir) {
                    case "^" -> {
                        xOff = 1;
                        yOff = 0;
                        dir = ">";
                        //point = newPoint;
                        continue;
                    }
                    case ">" -> {
                        xOff = 0;
                        yOff = 1;
                        dir = "v";
                        //point = newPoint;
                    }
                    case "v" -> {
                        xOff = -1;
                        yOff = 0;
                        dir = "<";
                        //point = newPoint;
                    }
                    case "<" -> {
                        xOff = 0;
                        yOff = -1;
                        dir = "^";
                        //point = newPoint;
                    }
                    default -> {
                        throw new IllegalStateException();
                    }
                }
            } else {
                System.out.println("You're off the grid! You're out of control! " + newPoint);
                break;
            }
        }
        System.out.println(mapLocGrid.replaceAll(points, "X"));
        return points.size();
    }
}
