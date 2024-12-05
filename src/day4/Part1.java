package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import core.Result;

public class Part1 {
  /** Answer: 2551 */
  public static void main(String[] args) {
    final char[][] data = parseFile("src/day4/input.txt");

    int count = 0;
    for (int row = 0; row < data.length; row++) {
      for (int col = 0; col < data[row].length; col++) {
        if (data[row][col] == 'X') {
          count += dirs(data, row, col, -1, 0); // up
          count += dirs(data, row, col, 1, 0); // down
          count += dirs(data, row, col, 0, -1); // left
          count += dirs(data, row, col, 0, 1); // right
          count += dirs(data, row, col, -1, -1); // up-left
          count += dirs(data, row, col, -1, 1); // up-right
          count += dirs(data, row, col, 1, -1); // down-left
          count += dirs(data, row, col, 1, 1); // down-right
        }
      }
    }

    System.out.println(count);
  }

  /**
   * Check line for characters using scalar and validate if the direction stays
   * within bounds.
   */
  public static int dirs(char[][] data, int row, int col, int vir, int hor) {
    if (row + 3*vir >= data.length || row + 3*vir < 0) {
      return 0;
    }
    
    if (col + 3*hor >= data[row].length || col + 3*hor < 0) {
      return 0;
    }

    if (data[row+1*vir][col+1*hor] == 'M'
      && data[row+2*vir][col+2*hor] == 'A'
      && data[row+3*vir][col+3*hor] == 'S'
    ) {
      return 1;
    }
    return 0;
  }

  /** Read complete file contents. */
  public static Result<char[][], IOException> readFile(String path) {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return Result.success(lines.map(line -> line.toCharArray())
        .toArray(char[][]::new)
      );
    } catch (IOException error) {
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static char[][] parseFile(String filePath) {
    final Result<char[][], IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new char[0][0];
    }
    return result.getSuccess();
  }
}