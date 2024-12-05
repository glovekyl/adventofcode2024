package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import core.Result;

public class Part2 {
  /** Answer: 1985 */
  public static void main(String[] args) {
    final char[][] data = parseFile("src/day4/input.txt");
    int count = 0;
    for (int row = 1; row < data.length-1; row++) {
      for (int col = 1; col < data[row].length-1; col++) {
        if (data[row][col] == 'A') {
          /* An 'X' if formed if both directions have a valid "MAS" character
           * chain. Accomodate a reversed */
          boolean aa = dirs(data, row, col, -1, -1) // up-left
            || dirs(data, row, col, 1, 1); // down-right
          boolean bb = dirs(data, row, col, -1, 1) // up-right
            || dirs(data, row, col, 1, -1); // down-left
          count += aa && bb ? 1 : 0;
        }
      }
    }
    System.out.println(count);
  }

  /** Check line for characters using scalar */
  public static boolean dirs(char[][] data, int row, int col, int vir, int hor) {
    return data[row-1*vir][col-1*hor] == 'M'
      && data[row+1*vir][col+1*hor] == 'S';
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