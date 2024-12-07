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
        //logger.info("part 1: " + part1(mapLocGrid)); // 4722
        logger.info("part 2: " + part2(mapLocGrid)); // 4709 is too high
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
        // return points.size();

        var potentialObstaclePoints = new HashSet<>(points);
        potentialObstaclePoints.remove(init); // cannot place an obstacle where the gnome started

        Map<Grid.Point, Set<String>> gridDirs;// = new HashMap<>();

        Set<Grid.Point> obstacles = new HashSet<>();

        System.out.println("potentialObstaclePoints" + potentialObstaclePoints.size());
        for (var pointBeen : potentialObstaclePoints) {
            gridDirs = new HashMap<>();
            var newMapLocGrid = mapLocGrid.replace(pointBeen, "O");
            System.out.println("newMapLocGrid terminates " + terminates(newMapLocGrid) + "\n" + newMapLocGrid);
            ;
            point = init;
            xOff = 0;
            yOff = -1;
            gridDirs.computeIfAbsent(point, _ -> new HashSet<>()).add(dir);
            int iter = 0;
            while (true) {
                iter++;
                if (iter > 4000) {
                    var currentPath = gridDirs.get(point);
                    if (currentPath != null && currentPath.contains(dir)) {
                        System.out.println("we loopin' bois");
                        obstacles.add(pointBeen);
                        break;
                    }
                }

                var newPoint = point.offset(xOff, yOff);



                if (newMapLocGrid.contains(newPoint, ".") || newMapLocGrid.contains(newPoint, "^")) {
                    point = newPoint;

                    gridDirs.computeIfAbsent(point, _ -> new HashSet<>()).add(dir);
                } else if (newMapLocGrid.contains(newPoint, "#") || newMapLocGrid.contains(newPoint, "O")) {
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
                    System.out.println("You're off the grid! You're out of control! " + newPoint);
                    break;
                }
            }
        }

        System.out.println("OBSTACLES = " + obstacles.size());
        System.out.println(mapLocGrid.replaceAll(obstacles, "O"));
        return obstacles.size();
    }

    public static boolean terminates(Grid<String> mapLocGrid) {
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
            } else if (mapLocGrid.contains(newPoint, "#") || mapLocGrid.contains(newPoint, "O")) {
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
        return true;
    }
}
