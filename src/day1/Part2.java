package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

import core.Result;

public class Part2 {
  /** Answer: 23741109 */
  public static void main(String[] args) {
    final int[][] data = parseFile("src/day1/input.txt");

    HashMap<Integer, Integer> right = new HashMap<>();
    for (int[] line : data) {
      right.merge(line[1], 1, Integer::sum);
    }
    
    int total = 0;
    for (int[] line : data) {
      total += line[0] * right.getOrDefault(line[0], 0);
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