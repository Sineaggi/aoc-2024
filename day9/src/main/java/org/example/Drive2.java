package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Drive2 {

    private static final Logger logger = Logger.getLogger(Drive2.class);
    private final List<Block> blocks;

    private Drive2(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public String toString() {
        return blocks.stream().map(Block::toString).collect(Collectors.joining(""));
    }

    sealed interface Block {
        record File(int id, int size) implements Block {
            @Override
            public String toString() {
                return IntStream.range(0, size).boxed().map(_ -> "" + id).collect(Collectors.joining());
            }
        }
        record FreeSpace(int size) implements Block {
            @Override
            public String toString() {
                return IntStream.range(0, size).boxed().map(_ -> ".").collect(Collectors.joining());
            }
        }
    }

    public static Drive2 parse(String input) {
        List<Block> blocks = Streams.index(input.chars().boxed())
                .filter(indexedInput -> !"\n".equals(new String(Character.toChars(indexedInput.obj()))))
                .map(indexedInput -> {
            int index = indexedInput.index();
            int length = Integer.parseInt(new String(Character.toChars(indexedInput.obj())));
            if (index % 2 == 0) {
                // even
                return (Block) new Block.File(index / 2, length);
            } else {
                return (Block) new Block.FreeSpace(length);
            }
        }).toList();
        return new Drive2(blocks);
    }

    public Drive2 reorder() {
        var scratchList = new ArrayList<>(blocks);
        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);
            if (block instanceof Block.FreeSpace(int size)) {
                // todo: fill it in
                Streams.Indexed<Block.File> indexedBlockToMove = findLastFileBlock(scratchList, size);
                System.out.println("Found block " + indexedBlockToMove);
                Block.File blockToMove = indexedBlockToMove.obj();
                int index = indexedBlockToMove.index();
                if (index < i) {
                    break;
                }
                if (size == blockToMove.size()) {
                    scratchList.set(i, blockToMove);
                    scratchList.set(index, new Block.FreeSpace(size));
                    i += blockToMove.size()-1;
                } else {
                    if (blockToMove.size() > size) {
                        throw new RuntimeException("not a perfect fit");
                    }
                    scratchList.set(i, blockToMove);
                    scratchList.set(index, new Block.FreeSpace(blockToMove.size()));
                    scratchList.add(i + 1, new Block.FreeSpace(size - blockToMove.size()));
                    i += blockToMove.size()-1;
                }
                System.out.println(new Drive2(scratchList));
            } else {
                continue;
            }
        }
        return new Drive2(scratchList);
    }

    private static Streams.Indexed<Block.File> findLastFileBlock(List<Block> blocks, int size) {
        for (int i = blocks.size() - 1; i >= 0; i--) {
            var block = blocks.get(i);
            if (block instanceof Block.File(int id, int size1) && size1 <= size) {
                return new Streams.Indexed<>((Block.File) block, i);
            }
        }
        throw new RuntimeException("Could not find last file block");
    }

    public long checksum() {
        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            var block = blocks.get(i);
            if (block instanceof Block.File(int id, int size)) {
                sum += (long) id * (long) i * size;
            }
        }
        return sum;
    }

    public record IndexedWithSize<T>(T obj, int index) {

    }
}
