package day1;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

import core.Result;

public class Part1 {
  /** Answer: 2166959 */
  public static void main(String[] args) {
    final int[][] data = parseFile("src/day1/input.txt");

    System.out.println(Arrays.toString(data[0]));
    
    int[] left = new int[data.length];
    int[] right = new int[data.length];
    for (int i = 0; i < data.length; i++) {
      left[i] = data[i][0];
      right[i] = data[i][1];
    }

    Arrays.sort(left);
    Arrays.sort(right);
    
    int total = 0;
    for (int i = 0; i < data.length; i++) {
      total += Math.abs(left[i] - right[i]);
    }

    System.out.println(total);
  }

  /** Read complete file contents. */
  public static Result<int[][], IOException> readFile(String path) {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return Result.success(lines.map(x -> Arrays.stream(x.split("\\s+"))
          .mapToInt(Integer::parseInt)
          .toArray())
        .toArray(int[][]::new));
    } catch (IOException error) {
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static int[][] parseFile(String filePath) {
    final Result<int[][], IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new int[0][0];
    }
    return result.getSuccess();
  }
}