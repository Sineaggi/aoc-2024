package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        int size();
        private static String toString(String string, int size) {
            return Stream.generate(() -> string).limit(size).collect(Collectors.joining());
        }
        record File(int size, int id) implements Block {
            @Override
            public String toString() {
                return Block.toString("" + id, size);
            }
        }
        record FreeSpace(int size) implements Block {
            @Override
            public String toString() {
                return Block.toString(".", size);
            }
        }
    }

    public static Drive parse(String input) {
        List<Block> blocks = Streams.index(input.codePoints().mapToObj(Character::toString))
                .filter(indexedInput -> !"\n".equals(indexedInput.obj()))
                .map(indexedInput -> {
            int index = indexedInput.index();
            int length = Integer.parseInt(indexedInput.obj());
            if (index % 2 == 0) {
                // even
                return new Block.File(length, index / 2);
            } else {
                return (Block) new Block.FreeSpace(length);
            }
        }).toList();
        return new Drive(blocks);
    }

    public Drive reorder() {
        return new Drive(blocks.stream().flatMap(f -> {
            if (f instanceof Block.FreeSpace(int size)) {
                return Stream.generate(() -> (Block) new Block.FreeSpace(1)).limit(size);
            } else if (f instanceof Block.File(int size, int id)) {
                return Stream.generate(() -> new Block.File(1, id)).limit(size);
            } else {
                throw new IllegalStateException();
            }
        }).toList()).reorderContinuous();
    }

    public Drive reorderContinuous() {
        var scratchList = new ArrayList<>(blocks);
        // todo: reloop
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) instanceof Block.FreeSpace(int size)) {
                // todo: fill it in
                var indexedBlockToMove = findLastFileBlock(scratchList, size).orElse(null);
                if (indexedBlockToMove == null) {
                    continue;
                }
                logger.debug("Found block " + indexedBlockToMove);
                var blockToMove = indexedBlockToMove.obj();
                var index = indexedBlockToMove.index();
                if (index < i) {
                    break;
                }
                scratchList.set(i, blockToMove);
                scratchList.set(index, new Block.FreeSpace(blockToMove.size()));
                if (size > blockToMove.size()) {
                    scratchList.add(i + 1, new Block.FreeSpace(size - blockToMove.size()));
                } else if (size < blockToMove.size()) {
                    throw new IllegalStateException("should not enter here");
                } else {
                    // do nothing: just the standard case, simple swap
                }
                System.out.println(new Drive(scratchList));
            } else {
                continue;
            }
        }
        return new Drive(scratchList);
    }

    private static Optional<Indexed<Block.File>> findLastFileBlock(List<Block> blocks, int emptySize) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            var block = blocks.get(i);
            if (block instanceof Block.File file && file instanceof Block.File(int fileSize, int _)) {
                if (fileSize <= emptySize) {
                    return Optional.of(new Indexed<>(file, i));
                }
            }
        }
        return Optional.empty();
    }

    //private static Indexed<Block> findLastFileBlockWithIndex(List<Block> blocks) {
    //    return Streams.index(blocks.reversed())
    //            .filter(block -> !(block.obj() instanceof Block.FreeSpace))
    //            .findFirst()
    //            .orElseThrow();
    //}

    public long checksum() {
        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);
            if (block instanceof Block.File(int id, int size)) {
                sum += (long) id * (long) i * (long) size;
            }
        }
        return sum;
    }
}
