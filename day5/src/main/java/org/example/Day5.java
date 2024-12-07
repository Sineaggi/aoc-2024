package org.example;

import java.io.Console;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {

    private static final Logger logger = Logger.getLogger(Day5.class);

    value record PageOrderingRule(int lowerPage, int laterPage) {

        public static PageOrderingRule ofLine(String line) {
            var split = line.split("\\|");
            return new PageOrderingRule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }

    value record Update(List<Integer> pages) {
        public static Update ofLine(String line) {
            var split = line.split(",");
            return new Update(Arrays.stream(split).map(Integer::valueOf).toList());
        }
    }

    public static void main(String[] args) throws Exception {
        List<PageOrderingRule> rules = new ArrayList<>();
        List<Update> pages = new ArrayList<>();
        try (var is = Day5.class.getResourceAsStream("/input")) {
            String str = new String(is.readAllBytes());
            //logger.debug(input);
            boolean start = true;
            for (var line : str.lines().toList()) {
                if (line.isEmpty()) {
                    start = false;
                } else {
                    if (start) {
                        rules.add(PageOrderingRule.ofLine(line));
                    } else {
                        pages.add(Update.ofLine(line));
                    }
                }
            }
        }
        logger.info("part 1: " + part1(rules, pages));
        logger.info("part 2: " + part2(rules, pages));
    }

    public static int part1(List<PageOrderingRule> rules, List<Update> updates) {
        var passedPages = new ArrayList<Update>();
        spluk:
        for (var update : updates) {
            /*
            System.out.println("b4: " + update.pages());
            var pages = new ArrayList<>(update.pages());
            Collections.sort(pages, (Comparator<Integer>) (o1, o2) -> {
                for (var rule : rules) {
                    if (o1 == rule.lowerPage() && o2 == rule.laterPage()) {
                        return 1;
                    } else if (o1 == rule.laterPage() && o2 == rule.lowerPage()) {
                        return -1;
                    }
                }
                // no rule matched
                return 0;
            });
            System.out.println("afta: " + pages);
             */
            //System.out.println(rules);
            logger.debug("update: " + update);
            for (var rule : rules) {
                var low = rule.lowerPage();
                var hi = rule.laterPage();
                List<Integer> pages = update.pages();
                var i = pages.indexOf(low);
                var j = pages.indexOf(hi);
                logger.debug("i: " + i + " j: " + j + " low: " + low + " hi: " + hi + " pages: " + pages);
                if (i == -1 || j == -1) {
                    logger.debug("rule not found " + update);
                    continue;
                } else if (i < j) {
                    logger.debug("rule passed " + update);
                    //passedPages.add(update);
                    continue;
                } else {
                    logger.debug("out of order " + update);
                    continue spluk;
                }
            }
            passedPages.add(update);
        }
        logger.debug(passedPages);
        return passedPages.stream().mapToInt(l -> {
            List<Integer> pages = l.pages();
            return pages.get(pages.size() / 2);
        }).sum();
        //return -1;
    }

    public static int part2(List<PageOrderingRule> rules, List<Update> updates) {
        var updatedPages = new ArrayList<Update>();
        spluk:
        for (var update : updates) {
            logger.debug("b4: " + update.pages());
            var pages = new ArrayList<>(update.pages());
            Collections.sort(pages, (Comparator<Integer>) (o1, o2) -> {
                for (var rule : rules) {
                    if (o1 == rule.lowerPage() && o2 == rule.laterPage()) {
                        return -1;
                    } else if (o1 == rule.laterPage() && o2 == rule.lowerPage()) {
                        return 1;
                    }
                }
                // no rule matched
                return 0;
            });
            logger.debug("afta: " + pages);
            if (!pages.equals(update.pages())) {
                updatedPages.add(new Update(pages));
            }
            //System.out.println(rules);
            //logger.debug("update: " + update);
            //for (var rule : rules) {
            //}
        }
        return updatedPages.stream().mapToInt(l -> {
            List<Integer> pages = l.pages();
            return pages.get(pages.size() / 2);
        }).sum();
    }
}
