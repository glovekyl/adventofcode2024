package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class Part1 {
  /**
   * Answer: 6310675819476
   */
  public static void main(String[] args) throws IOException {
    final int[] data = readFile("src/day9/input.txt");
    final long start = System.nanoTime();
    
    int blockLength = 0;
    for (int num : data) {
      blockLength += num;
    }
    int[] blocks = new int[blockLength];

    // Using the data array, create a new array with the expanded blocks.
    // (data) 12345 -> (blocks) 0..111....22222
    for (
      int dataIndex = 0, blockIndex = 0, id = 0;
      dataIndex < data.length;
      dataIndex++
      ) {
      final boolean isEven = dataIndex % 2 == 0;
      for (int blockCount = 0; blockCount < data[dataIndex]; blockCount++) {
        blocks[blockIndex++] = (isEven) ? id : -1;
      }
      if (isEven) {
        id++;
      }
    }

    long checksum = 0;
    // Rearrange the blocks array such that empty space (-1) is filled with
    // blocks from the end of the array. Values switched in-place. While
    // iterating, sum the total based on the value of the index, multiplied by
    // the value of the block.
    for (
      int readIndex = 0, writeIndex = blocks.length - 1;
      readIndex < writeIndex;
      readIndex++
      ) {
      if (blocks[readIndex] == -1) {
        while (blocks[writeIndex] == -1) {
          writeIndex--;
        }
        if (writeIndex <= readIndex) {
          break;
        }
        blocks[readIndex] = blocks[writeIndex--];
      }
      checksum += readIndex * blocks[readIndex];
    }


    System.out.println(String.format(
      "%d (%dμs)",
      checksum,
      (System.nanoTime() - start) / 1000)
    );
  }

  /** Read complete file contents. */
  public static int[] readFile(String path) throws IOException {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return lines.flatMap(line -> Arrays.stream(line.split(""))).mapToInt(Integer::parseInt).toArray();
    }
  }
}