package org.example;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {
    //record Data(Map<String, Integer> map, List<Pair> pairs) {
    //}
    public record Edge(String a, String b) {
        public String not(String s) {
            if (a.equals(s)) {
                return b;
            } else if (b.equals(s)) {
                return a;
            } else {
                throw new RuntimeException("nope");
            }
        }
    }

    private static final Logger logger = Logger.getLogger(Day23.class);

    public static void main(String[] args) {
        List<Edge> input = parse(load());
        System.out.println(part1(input));
    }

    public static String load() {
        return Classes.loadResource(Day23.class, "/input");
    }

    public static List<Edge> parse(String input) {
        var pairs = input.lines().map(line -> {
            var parts = line.split("-");
            return new Edge(parts[0], parts[1]);
        }).toList();
        return pairs;
    }
    public record Triple(String a, String b, String c) implements Comparable<Triple> {
        public Triple {
            var list = List.of(a, b, c);
            var sorted = new ArrayList<>(list);
            Collections.sort(sorted);
            if (!list.equals(sorted)) {
                throw new RuntimeException("onoz");
            }
        }

        @Override
        public int compareTo(Triple o) {
            return Comparator.comparing(Triple::a)
                    .thenComparing(Triple::b)
                    .thenComparing(Triple::c)
                    .compare(this, o);
        }

        public boolean startsWith(String s) {
            return a.startsWith(s) || b.startsWith(s) || c.startsWith(s);
        }
    }

    public static long part1(List<Edge> pairs) {
        Map<Integer, String> map = new HashMap<>();
        Graph graph = new Graph();
        for (var pair : pairs) {
            graph.addVertex(pair.a());
            graph.addVertex(pair.b());
        }
        for (var pair : pairs) {
            graph.addEdge(pair);
            graph.addEdge(pair);
        }
        for (var pair : pairs) {
            var a = graph.adjacencyList.getOrDefault(pair.a(), Collections.emptyList());
            if (!a.isEmpty()) {
                var mapped = a.stream().map(i -> i.not(pair.a())).toList();
            }
            var b = graph.adjacencyList.getOrDefault(pair.b(), Collections.emptyList());
            if (!b.isEmpty()) {
            }
        }
        graph.printGraph();
        Set<Triple> triples = new HashSet<>();
        for (var node : graph.adjacencyList.entrySet()) {
            var key = node.getKey();
            var nodes = node.getValue();
            if (nodes.size() < 2) {
                continue;
            }
            //var all = Streams.unorderedCombinations(List.copyOf(nodes)).map(set -> {
            //    var list = new ArrayList<>(set);
            //    list.add(key);
            //    var pairz = list.stream().sorted().toList();
            //    var t = new Triple(pairz.get(0), pairz.get(1), pairz.get(2));
            //    return t;
            //}).toList();
            //triples.addAll(all);
            for (var con : nodes) {
                if (graph.adjacencyList.get(con).contains(key)) {

                }
            }
        }

        //Set<Triple> triples = new HashSet<>();
        //for (var pair1 : pairs) {
        //    for (var pair2 : pairs) {
        //        for (var pair3 : pairs) {
        //            if (pair1.equals(pair2) || pair2.equals(pair3) || pair3.equals(pair1)) {
        //                continue;
        //            }
        //            var list = Set.copyOf(List.of(pair1.a(), pair1.b(), pair2.a(), pair2.b(), pair3.a(), pair3.b()));
        //            if (list.size() != 3) {
        //                continue;
        //            }
        //            List<String> sorted = sort(list);
        //            triples.add(new Triple(sorted.get(0), sorted.get(1), sorted.get(2)));
        //        }
        //    }
        //}
        List<Triple> striples = sort(triples);
        System.out.println(striples.size());
        for (var triple : striples) {
            System.out.println(triple.a() + "," + triple.b() + "," + triple.c());
        }
        return striples.stream().filter(i -> i.startsWith("t")).count();
    }

    public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection) {
        List<T> list = new ArrayList<>(collection);
        Collections.sort(list);
        return list;
    }

    private static class Graph {
        private Map<String, List<Edge>> adjacencyList;

        public Graph() {
            adjacencyList = new HashMap<>();
        }

        public void addVertex(String vertex) {
            adjacencyList.put(vertex, new ArrayList<>());
        }

        public void addEdge(Edge edge) {
            adjacencyList.get(edge.a()).add(edge);
            adjacencyList.get(edge.b()).add(edge);
        }

        public void printGraph() {
            //for (Map.Entry<String, Set<Edge>> entry : adjacencyList.entrySet()) {
            //    System.out.println(entry.getKey() + " -> " + entry.getValue());
            //}
            System.out.println(adjacencyList);
        }

        //public static void main(String[] args) {
        //    Graph graph = new Graph();
        //    graph.addVertex(0);
        //    graph.addVertex(1);
        //    graph.addVertex(2);
        //    graph.addVertex(3);
//
        //    graph.addEdge(0, 1);
        //    graph.addEdge(0, 2);
        //    graph.addEdge(1, 2);
        //    graph.addEdge(2, 3);
//
        //    graph.printGraph();
        //}
    }

}

