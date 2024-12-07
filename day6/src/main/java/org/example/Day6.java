package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {

    private static final Logger logger = Logger.getLogger(Day6.class);

    public static void main(String[] args) throws Exception {
        Grid<String> mapLocGrid;
        try (var is = Day6.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            mapLocGrid = parse(str);
        }
        logger.info("part 1: " + part1(mapLocGrid)); // 4722
        logger.info("part 2: " + part2(mapLocGrid)); // 1602
    }

    public static Grid<String> parse(String input) {
        Grid<String> mapLocGrid;
        var map = Streams.index(input.lines()).flatMap(indexedLine -> {
            int y = indexedLine.index();
            String line = indexedLine.obj();
            return Streams.index(line.chars().boxed()).map(indexedChar -> {
                int x = indexedChar.index();
                String ch = new String(Character.toChars(indexedChar.obj()));
                return Map.entry(new Grid.Point(x, y), ch);
            });
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        mapLocGrid = new Grid<>(map);
        return mapLocGrid;
    }

    public static int part1(Grid<String> mapLocGrid) {
        logger.debug(mapLocGrid);
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
                logger.debug("You're off the grid! You're out of control! " + newPoint);
                break;
            }
        }
        logger.debug(mapLocGrid.replaceAll(points, "X"));
        return points.size();
    }

    public static int part2(Grid<String> mapLocGrid) {
        logger.debug(mapLocGrid);
        String dir = "^";
        final var init = mapLocGrid.find(dir);
        var point = init;
        int xOff = 0;
        int yOff = -1;
        Set<Grid.Point> points = new HashSet<>();
        points.add(point); // add guard's starting location
        while (true) {
            var newPoint = point.offset(xOff, yOff);
            if (mapLocGrid.contains(newPoint, ".") || mapLocGrid.contains(newPoint, "^")) {
                point = newPoint;
                points.add(point);
            } else if (mapLocGrid.contains(newPoint, "#")) {
                switch (dir) {
                    case "^" -> {
                        xOff = 1;
                        yOff = 0;
                        dir = ">";
                    }
                    case ">" -> {
                        xOff = 0;
                        yOff = 1;
                        dir = "v";
                    }
                    case "v" -> {
                        xOff = -1;
                        yOff = 0;
                        dir = "<";
                    }
                    case "<" -> {
                        xOff = 0;
                        yOff = -1;
                        dir = "^";
                    }
                    default -> {
                        throw new IllegalStateException();
                    }
                }
            } else {
                logger.debug("You're off the grid! You're out of control! " + newPoint);
                break;
            }
        }
        logger.debug(mapLocGrid.replaceAll(points, "X"));

        var potentialObstaclePoints = new HashSet<>(points);
        potentialObstaclePoints.remove(init); // cannot place an obstacle where the gnome started

        Set<Grid.Point> obstacles = new HashSet<>();

        logger.debug("potentialObstaclePoints" + potentialObstaclePoints.size());
        for (var pointBeen : potentialObstaclePoints) {
            var newMapLocGrid = mapLocGrid.replace(pointBeen, "O");
            logger.debug("newMapLocGrid terminates " + terminates(newMapLocGrid) + "\n" + newMapLocGrid);

            if (!terminates(newMapLocGrid)) {
                obstacles.add(pointBeen);
            }
        }

        System.out.println("OBSTACLES = " + obstacles.size());
        System.out.println(mapLocGrid.replaceAll(obstacles, "O"));
        return obstacles.size();
    }

    public static boolean terminates(Grid<String> mapLocGrid) {
        logger.debug(mapLocGrid);
        String dir = "^";
        var point = mapLocGrid.find(dir);
        int xOff = 0;
        int yOff = -1;
        Set<Grid.Point> points = new HashSet<>();
        points.add(point); // add guard's starting location

        Map<Grid.Point, Set<String>> gridDirs = new HashMap<>();
        gridDirs.computeIfAbsent(point, _ -> new HashSet<>()).add(dir);

        while (true) {
            var newPoint = point.offset(xOff, yOff);
            var been = gridDirs.get(newPoint);
            if (been != null && been.contains(dir)) {
                return false; // we loopin'
            }

            if (mapLocGrid.contains(newPoint, ".") || mapLocGrid.contains(newPoint, "^")) {
                point = newPoint;

                gridDirs.computeIfAbsent(point, _ -> new HashSet<>()).add(dir);

                points.add(point);
            } else if (mapLocGrid.contains(newPoint, "#") || mapLocGrid.contains(newPoint, "O")) {
                switch (dir) {
                    case "^" -> {
                        xOff = 1;
                        yOff = 0;
                        dir = ">";
                    }
                    case ">" -> {
                        xOff = 0;
                        yOff = 1;
                        dir = "v";
                    }
                    case "v" -> {
                        xOff = -1;
                        yOff = 0;
                        dir = "<";
                    }
                    case "<" -> {
                        xOff = 0;
                        yOff = -1;
                        dir = "^";
                    }
                    default -> {
                        throw new IllegalStateException();
                    }
                }
            } else {
                logger.debug("You're off the grid! You're out of control! " + newPoint);
                break;
            }
        }
        logger.debug(mapLocGrid.replaceAll(points, "X"));
        return true;
    }
}
