package org.example;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Day8 {

    private static final Logger logger = Logger.getLogger(Day8.class);

    public static void main(String[] args) throws Exception {
        Grid<String> input;
        try (var is = Day8.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            input = parse(str);
        }
        logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    public static Grid<String> parse(String input) {
        var map = Streams.index(input.lines()).flatMap(indexedLine -> {
            int y = indexedLine.index();
            String line = indexedLine.obj();
            return Streams.index(line.codePoints().mapToObj(Character::toString)).map(indexedChar -> {
                int x = indexedChar.index();
                String ch = indexedChar.obj();
                return Map.entry(new Grid.Point(x, y), ch);
            });
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new Grid<>(map);
    }

    public static long part1(Grid<String> input) {
        System.out.println("input " + input);
        var frequencies = input.entries().map(Map.Entry::getValue).filter(str -> !str.equals(".")).collect(Collectors.toSet());
        System.out.println("frequencies " + frequencies);
        Set<Grid.Point> allAntiNodes = new HashSet<>();

        var size = input.max();
        System.out.println("size " + size);

        Predicate<Grid.Point> inBounds = point -> point.x() >= 0 && point.y() >= 0 && point.x() <= size.x() && point.y() <= size.y();

        for (var frequency : frequencies) {
            var nodes = input.findAll(frequency);
            if (nodes.size() < 2) {
                throw new RuntimeException("doesn't make sense, frequency " + frequency + " only has " + nodes.size() + " nodes");
            }
            System.out.println("nodes " + nodes);
            var antiNodes = Streams.unorderedCombinations(nodes)
                    .map(pair -> Day8.anti(pair, inBounds))
                    .flatMap(Collection::stream)
                    .filter(inBounds)
                    .collect(Collectors.toSet());
            System.out.println("anti nodes " + antiNodes);
            allAntiNodes.addAll(antiNodes);
        }
        return allAntiNodes.size();
        // return 2;
    }

    public static List<Grid.Point> anti(Set<Grid.Point> set, Predicate<Grid.Point> inBounds) {
        var nodes = set.stream().toList();
        var node1 = nodes.get(0);
        var node2 = nodes.get(1);
        var xOffset = node1.x() - node2.x();
        var yOffset = node1.y() - node2.y();
        return Stream.of(node1.offset(xOffset, yOffset), node2.offset(-xOffset, -yOffset))
                .filter(inBounds)
                .toList();
    }

    public static long part2(Grid<String> input) {
        System.out.println("input " + input);
        var frequencies = input.entries().map(Map.Entry::getValue).filter(str -> !str.equals(".")).collect(Collectors.toSet());
        System.out.println("frequencies " + frequencies);
        Set<Grid.Point> allAntiNodes = new HashSet<>();

        var size = input.max();
        System.out.println("size " + size);

        Predicate<Grid.Point> inBounds = point -> point.x() >= 0 && point.y() >= 0 && point.x() <= size.x() && point.y() <= size.y();

        for (var frequency : frequencies) {
            var nodes = input.findAll(frequency);
            if (nodes.size() < 2) {
                throw new RuntimeException("doesn't make sense, frequency " + frequency + " only has " + nodes.size() + " nodes");
            }
            System.out.println("nodes " + nodes);
            var antiNodes = Streams.unorderedCombinations(nodes)
                    .map(pair -> Day8.antiHarmonics(pair, inBounds))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
            System.out.println("anti nodes " + antiNodes);
            allAntiNodes.addAll(antiNodes);
        }
        return allAntiNodes.size();
        // return 2;
    }

    public static Set<Grid.Point> antiHarmonics(Set<Grid.Point> set, Predicate<Grid.Point> inBounds) {
        var nodes = set.stream().toList();
        var node1 = nodes.get(0);
        var node2 = nodes.get(1);
        var xOffset = node1.x() - node2.x();
        var yOffset = node1.y() - node2.y();
        Set<Grid.Point> points = new HashSet<>(set);
        while (true) {
            var newNode1 = node1.offset(xOffset, yOffset);
            if (inBounds.test(newNode1)) {
                points.add(newNode1);
                node1 = newNode1;
            } else {
                break;
            }
        }
        while (true) {
            var newNode2 = node2.offset(-xOffset, -yOffset);
            if (inBounds.test(newNode2)) {
                points.add(newNode2);
                node2 = newNode2;
            } else {
                break;
            }
        }
        return points;
    }
}
