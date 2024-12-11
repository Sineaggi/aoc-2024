package org.example;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final Logger logger = Logger.getLogger(Day3.class);

    public static void main(String[] args) throws Exception {
        String input = load();
        // prints 184576302
        logger.info("part 1: " + part1(input));
        // prints 118173507
        logger.info("part 2: " + part2(input));
    }

    public static String load() {
        return Classes.loadResource(Day3.class, "/input");
    }

    public static int part1(String input) {
        Pattern p = Pattern.compile("mul\\((?<left>\\d{1,3}),(?<right>\\d{1,3})\\)");

        Matcher matcher = p.matcher(input);
        return matcher.results()
                .mapToInt(res -> {
                    int left = Integer.parseInt(res.group("left"));
                    int right = Integer.parseInt(res.group("right"));
                    return left * right;
                }).sum();
    }

    public static int part2(String input) {
        Pattern p = Pattern.compile("mul\\((?<left>\\d{1,3}),(?<right>\\d{1,3})\\)");
        Pattern y = Pattern.compile("do\\(\\)");
        Pattern n = Pattern.compile("don't\\(\\)");

        Matcher matcher = p.matcher(input);
        List<Integer> dos = y.matcher(input).results().map(MatchResult::start).toList();
        System.out.println("dos: " + dos);
        List<Integer> donts = n.matcher(input).results().map(MatchResult::start).toList();
        System.out.println("donts: " + donts);
        return matcher.results()
                .mapToInt(res -> {
                    System.out.println(res.start());
                    if (inDos(res.start(), dos, donts, res.group("left") + "*" + res.group("right"))) {
                        int left = Integer.parseInt(res.group("left"));
                        int right = Integer.parseInt(res.group("right"));
                        System.out.println("left: " + left + " right: " + right);
                        return left * right;
                    }
                    return 0;
                }).sum();
    }

    sealed interface Nearest {
        final class Unset implements Nearest {
            private Unset() {
            }
            private static final Unset INSTANCE = new Unset();
        }
        value record Set(int start) implements Nearest {

        }
        static Nearest set(int start) {
            return new Set(start);
        }
        static Nearest unset() {
            return Unset.INSTANCE;
        }
    }

    private static boolean inDos(int start, List<Integer> dos, List<Integer> donts, String debugString) {
        Predicate<Integer> beforeStart = x -> x < start;
        Nearest nearestDo = dos.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        Nearest nearestDont = donts.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        if (nearestDont instanceof Nearest.Set dontSet) {
            if (nearestDo instanceof Nearest.Set doSet) {
                return doSet.start() > dontSet.start();
            } else {
                return false;
            }
        } else {
            if (nearestDo instanceof Nearest.Set doSet) {
                return true;
            } else {
                return true;
            }
        }
    }
}
