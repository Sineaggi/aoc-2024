package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    private static final Logger logger = Logger.getLogger(Day3.class);

    public static void main(String[] args) throws Exception {
        String input;
        try (var is = Day3.class.getResourceAsStream("/input")) {
            input = new String(is.readAllBytes());
        }
        //logger.info("part 1: " + part1(input));
        logger.info("part 2: " + part2(input));
    }

    private static int part1(String input) {
        //return input.stream().mapToInt(Day3::safe).sum();
        Pattern p = Pattern.compile("mul\\((?<left>\\d{1,3}),(?<right>\\d{1,3})\\)");

        Matcher matcher = p.matcher(input);
        //while (m.find()) {
        //    System.out.println(++cnt + ": G1: " + m.group(1));
        //}
        return matcher.results()
                .mapToInt(res -> {
                    System.out.println(res.start());
                    int left = Integer.parseInt(res.group("left"));
                    int right = Integer.parseInt(res.group("right"));
                    System.out.println("left: " + left + " right: " + right);
                    return left * right;
                }).sum();
        //184576302

    }

    private static int part2(String input) {
        //return input.stream().mapToInt(Day3::safe).sum();
        Pattern p = Pattern.compile("mul\\((?<left>\\d{1,3}),(?<right>\\d{1,3})\\)");
        Pattern y = Pattern.compile("do\\(\\)");
        Pattern n = Pattern.compile("don't\\(\\)");

        Matcher matcher = p.matcher(input);
        //while (m.find()) {
        //    System.out.println(++cnt + ": G1: " + m.group(1));
        //}
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
        // indexedResults.stream().filter()

        //184576302
    }

    sealed interface Nearest {
        final class Unset implements Nearest {
            private Unset() {
            }
            public static final Unset INSTANCE = new Unset();
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
        //Nearest nearestDo = Nearest.Unset.INSTANCE;
        //Nearest nearestDont = Nearest.Unset.INSTANCE;
        //System.out.println("start: " + start);
        Predicate<Integer> beforeStart = x -> x < start;
        //System.out.println(Optional.class.isValue());
        //Nearest nearestDo2 = dos.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        //Nearest nearestDont2 = donts.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        //for (int i = 0; i < dos.size(); i++) {
        //    var doStart = dos.get(i);
        //    if (doStart > start) {
        //        if (i == 0) {
        //            // nearestDo = 0;
        //        } else {
        //            nearestDo = new Nearest.Set(dos.get(i - 1));
        //        }
        //    }
        //}
        //for (int i = 0; i < donts.size(); i++) {
        //    var dontStart = donts.get(i);
        //    if (dontStart > start) {
        //        if (i == 0) {
        //            // can't start at dont
        //            // nearestDont = 0;
        //        } else {
        //            nearestDont = new Nearest.Set(donts.get(i - 1));
        //        }
        //    }
        //}
        //if (!nearestDo2.equals(nearestDo)) {
        //    throw new RuntimeException("nearest doesn't match " + nearestDo2 + "," + nearestDo);
        //}
        //if (!nearestDont2.equals(nearestDont)) {
        //    throw new RuntimeException("nearest doesn't match " + nearestDont2 + "," + nearestDont);
        //}
        //System.out.println(nearestDont + " " + nearestDo);
        Nearest nearestDo = dos.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        Nearest nearestDont = donts.stream().filter(beforeStart).max(Comparator.naturalOrder()).map(Nearest::set).orElseGet(Nearest::unset);
        if (nearestDont instanceof Nearest.Unset) {
            if (nearestDo instanceof Nearest.Unset) {
                // if neither, we assume start
                //System.out.println("donts " + donts);
                //System.out.println("true cuz dont isn't set and do isn't set for " + debugString);
                return true;
            } else if (nearestDo instanceof Nearest.Set doSet) {
                //System.out.println("true cuz dont isn't set and do is set for " + debugString);
                return true;
            } else {
                throw new IllegalStateException("can't reach");
            }
        } else if (nearestDont instanceof Nearest.Set dontSet) {
            if (nearestDo instanceof Nearest.Unset) {
                // if neither, we assume start
                //System.out.println("false cuz dont is set and do isn't set for " + debugString);
                return false;
            } else if (nearestDo instanceof Nearest.Set doSet) {
                if (doSet.start() > dontSet.start()) {
                    //System.out.println("false doSet " + doSet + " is closer than dontSet " + dontSet + " " + debugString);
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new IllegalStateException("can't reach");
            }
        } else {
            throw new IllegalStateException("can't reach");
        }
        //int dontDist = start - nearestDont;
        //int doDist = start - nearestDo;
        //if (doDist > dontDist) {
        //    System.out.println("do nearer");
        //    return true;
        //}
        //System.out.println("dont nearer");
        //return false;
    }
}
