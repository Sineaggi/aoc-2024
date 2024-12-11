package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        return blocks.stream().map(Block::toString).collect(Collectors.joining());
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
        var drive = new Drive(blocks.stream().flatMap(block -> switch (block) {
            case Block.FreeSpace(int size) -> Stream.generate(() -> (Block) new Block.FreeSpace(1)).limit(size);
            case Block.File(int size, int id) -> Stream.generate(() -> new Block.File(1, id)).limit(size);
        }).toList());
        var scratchList = new ArrayList<>(drive.blocks);
        for (int i = 0; i < scratchList.size(); i++) {
            if (scratchList.get(i) instanceof Block.FreeSpace(int size)) {
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
                logger.debug(new Drive(scratchList));
            } else {
                continue;
            }
        }
        return new Drive(scratchList);
    }

    public Drive reorderContinuous() {
        var scratchList = new ArrayList<>(blocks);
        var attempted = new HashSet<Integer>();
        loop:
        while (true) {
            for (int i = scratchList.size() - 1; i >= 0; i--) {
                if (scratchList.get(i) instanceof Block.File file) {
                    int size = file.size();
                    if (attempted.contains(file.id())) {
                        continue;
                    } else {
                        attempted.add(file.id());
                    }
                    var indexedFreeSpace = findLeftmostSpanOfFreeSpace(scratchList, size).orElse(null);
                    if (indexedFreeSpace == null) {
                        continue;
                    }
                    logger.debug("Found block " + indexedFreeSpace);
                    Block.FreeSpace freeSpace = indexedFreeSpace.obj();
                    int index = indexedFreeSpace.index();
                    if (index > i) {
                        continue;
                    }
                    scratchList.set(index, file);
                    scratchList.set(i, new Block.FreeSpace(size));
                    if (freeSpace.size() > file.size()) {
                        scratchList.add(index + 1, new Block.FreeSpace(freeSpace.size() - size));
                    }
                    combineFreeSpace(scratchList);
                    //System.out.println(new Drive(scratchList));
                    continue loop;
                } else {
                    continue;
                }
            }
            break;
        }
        return new Drive(scratchList);
    }

    private void combineFreeSpace(List<Block> blocks) {
        loop:
        while (true) {
            for (int i = 0; i < blocks.size() - 1; i++) {
                if (blocks.get(i) instanceof Block.FreeSpace block) {
                    if (blocks.get(i + 1) instanceof Block.FreeSpace nextBlock) {
                        blocks.remove(i + 1);
                        blocks.set(i, new Block.FreeSpace(block.size() + nextBlock.size()));
                        continue loop;
                    }
                }
            }
            break;
        }
    }

    public static Optional<Indexed<Block.FreeSpace>> findLeftmostSpanOfFreeSpace(List<Block> blocks, int emptySize) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) instanceof Block.FreeSpace block) {
                if (block.size() >= emptySize) {
                    return Optional.of(new Indexed<>(block, i));
                }
            }
        }
        return Optional.empty();
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
        int position = 0;
        for (var block : blocks) {
            if (block instanceof Block.File(int size, int id)) {
                for (int j = 0; j < size; j++) {
                    sum += (long) id * ((long) position + j);
                }
            }
            position += block.size();
        }
        return sum;
    }
}
