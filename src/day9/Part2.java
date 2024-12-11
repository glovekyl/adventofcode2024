package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Stream;

public class Part2 {
  private static final int COUNT_INDEX = 0;
  private static final int DATA_INDEX = 1;

  /**
   * Answers (recent -> older):
   * - 6335973512055? (too high)
   * - 896750455? (too low)
   * 
   * Unsure why the answer is too high.
   */
  public static void main(String[] args) throws IOException {
    final int[][] data = readFile("src/day9/test.txt");
    final long start = System.nanoTime();

    final int[] blocks = new int[data[COUNT_INDEX][0]];
    final Stack<int[]> fileStack = new Stack<>(); // [start, end]

    // Using the data array, create a new array with the expanded blocks.
    // (data) 12345 -> (blocks) 0..111....22222
    for (
        int dataIndex = 0, blockIndex = 0, id = 0;
        dataIndex < data[DATA_INDEX].length;
        dataIndex++) {
      final boolean isEven = dataIndex % 2 == 0;

      int blockCount = 0;
      while (blockCount < data[DATA_INDEX][dataIndex]) {
        blocks[blockIndex++] = (isEven) ? id : -1;
        blockCount++;
      }

      if (isEven) {
        fileStack.add(new int[] {
          blockIndex - blockCount,
          blockCount
        });
        id++;
      }
    }


    while(!fileStack.isEmpty()) {
      final int[] file = fileStack.pop();
      final int fileIndex = file[0];
      final int fileSize = file[1];

      int writeIndex = 0;
      for (int readIndex = 0; readIndex < fileIndex; readIndex++) {
        if (readIndex - writeIndex == fileSize) {
          // Swap the empty space with the file.
          swap(blocks, writeIndex, fileIndex, fileSize);
          writeIndex += fileSize;
          break;
        }

        if (blocks[readIndex] != -1) {
          writeIndex = readIndex+1;
        }
      }
    }

    // TODO: Checksum can be calculated while swapping.
    long checksum = 0;
    for (int i = 1; i < blocks.length; ++i) {
      if (blocks[i] > 0) {
        checksum += i * blocks[i];
      }
    }

    System.out.println(String.format(
        "%d (%dμs)",
        checksum,
        (System.nanoTime() - start) / 1000));
  }

  /** Swap the elements of an array */
  public static void swap(int[] blocks, int firstIndex, int secondIndex, int size) {
    for (int i = 0; i < size; i++) {
      final int temp = blocks[firstIndex + i];
      blocks[firstIndex + i] = blocks[secondIndex + i];
      blocks[secondIndex + i] = temp;
    }
  }

  /** Read complete file contents. */
  public static int[][] readFile(String path) throws IOException {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      final int[] blockCount = new int[] { 0 };
      int[] x = lines.flatMap(lin -> Arrays.stream(lin.split(""))).mapToInt(n -> {
        final int num = Integer.parseInt(n);
        blockCount[0] += num;
        return Integer.parseInt(n);
      }).toArray();
      return new int[][] { blockCount, x };
    }
  }
}