package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Drive {

    private static final Logger logger = Logger.getLogger(Drive.class);
    private final List<Block> blocks;

    private Drive(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        return blocks.stream().map(Block::toString).collect(Collectors.joining(""));
    }

    sealed interface Block {
        record File(int id) implements Block {
            @Override
            public String toString() {
                return "" + id;
            }
        }
        final class FreeSpace implements Block {
            private FreeSpace() {}
            private static final FreeSpace EMPTY = new FreeSpace();
            public static FreeSpace empty() { return EMPTY; }

            @Override
            public String toString() {
                return ".";
            }
        }
    }

    public static Drive parse(String input) {
        List<Block> blocks = Streams.index(input.chars().boxed()).filter(indexedInput -> !"\n".equals(new String(Character.toChars(indexedInput.obj())))).flatMap(indexedInput -> {
            int index = indexedInput.index();
            int length = Integer.parseInt(new String(Character.toChars(indexedInput.obj())));
            if (index % 2 == 0) {
                // even
                return Stream.generate(() -> new Block.File(index / 2)).limit(length);
            } else {
                return Stream.generate(Block.FreeSpace::empty).limit(length);
            }
        }).toList();
        return new Drive(blocks);
    }

    public Drive reorder() {
        var scratchList = new ArrayList<>(blocks);
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).equals(Block.FreeSpace.empty())) {
                // todo: fill it in
                var blockToMove = findLastFileBlock(scratchList);
                logger.debug("Found block " + blockToMove);
                var blewk = blockToMove.obj();
                var index = blockToMove.index();
                if (index < i) {
                    break;
                }
                scratchList.set(i, blewk);
                scratchList.set(index, Block.FreeSpace.empty());
                logger.debug(new Drive(scratchList));
            } else {
                continue;
            }
        }
        return new Drive(scratchList);
    }

    private static Streams.Indexed<Block> findLastFileBlock(List<Block> blocks) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            if (blocks.get(i).equals(Block.FreeSpace.empty())) {
                continue;
            } else {
                return new Streams.Indexed(blocks.get(i), i);
            }
        }
        throw new RuntimeException("Could not find last file block");
    }

    private static Streams.Indexed<Block> findLastFileBlockWithIndex(List<Block> blocks) {
        return Streams.index(blocks.reversed())
                .filter(block -> !block.obj().equals(Block.FreeSpace.empty()))
                .findFirst()
                .orElseThrow();
    }

    public long checksum() {
        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);
            if (block instanceof Block.File(int id)) {
                sum += (long) id * (long) i;
            }
        }
        return sum;
    }
}
