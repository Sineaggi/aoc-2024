package org.example;

import java.util.List;
import java.util.stream.Collectors;

class Stones {
    private static final Logger logger = Logger.getLogger(Stones.class);
    private final List<Stone> stones;
    public Stones(List<Stone> stones) {
        this.stones = stones;
    }
    public static Stones ofLongs(List<Long> stones) {
        return new Stones(stones.stream().map(i -> new Stone(i)).toList());
    }
    public Stones blink() {
        return new Stones(stones.stream().flatMap(stone -> stone.blink().stream()).toList());
    }
    public Stones blink(int times) {
        var stones = this;
        logger.debug(this);
        for (int i = 0; i < times; i++) {
            stones = stones.blink();
            logger.debug(stones);
        }
        return stones;
    }
    public int blonk(int times) {
        //if (times == 0) {
        //    return 0;
        //}
        var totalBlonkage = 0;
        for (int i = 0; i < stones.size(); i++) {
            if (times == 0) {
                totalBlonkage++;
            } else {
                var stonez = new Stones(stones.get(i).blink());
                totalBlonkage += stonez.blonk(times - 1);
                //totalBlonkage += blonk(times - 1);
            }
            ////totalBlonkage++;
            //var stonez = new Stones(stones.get(i).blink());
            //System.out.println(stonez.stones.size());
            //var blonkage = stonez.blonk(times - 1);
            //totalBlonkage += blonkage;
        }
        return totalBlonkage;
    }
    @Override
    public String toString() {
        return stones.stream().map(i -> "" + i.engravedNumber()).collect(Collectors.joining(" "));
    }
    value record Stone(long engravedNumber) {
        public List<Stone> blink() {
            if (engravedNumber == 0) {
                return List.of(new Stone(1));
            } else if (("" + engravedNumber).length() % 2 == 0) {
                var str = "" + engravedNumber;
                var len = str.length() / 2;
                return List.of(new Stone(Long.parseLong(str.substring(0, len))), new Stone(Long.parseLong(str.substring(len))));
            } else {
                return List.of(new Stone(engravedNumber * 2024));
            }
        }
    }

    public int count() {
        return stones.size();
    }
}
