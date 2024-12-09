package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import core.Result;

public class Part2 {
  /** Answer: 1436? (too high) */
  public static void main(String[] args) {
    final char[][] data = parseFile("src/day6/test.txt");
    final long start = System.nanoTime();

    // Find guard coords to start
    int guardRow = 0;
    int guardCol = 0;
    char guardDir = '^';
    for (int row = 0; row < data.length; row++) {
      for (int col = 0; col < data[row].length; col++) {
        if (data[row][col] == '^'
          || data[row][col] == 'v'
          || data[row][col] == '<'
          || data[row][col] == '>') {
          guardRow = row;
          guardCol = col;
          guardDir = data[row][col];
          break;
        }
      } 
    }

    int counter = 0;
    while (guardRow >= 0 && guardRow < data.length
      && guardCol >= 0 && guardCol < data[0].length
    ) {
      // Should we turn right?
      if (peek(data, guardRow, guardCol, guardDir) == '#') {
        guardDir = turnRight(guardDir);
      }
      // Mark the guard's distinct position and direction
      data[guardRow][guardCol] = guardDir;

      // Move the guard
      if (data[guardRow][guardCol] == '^') {
        if (checkRight(data, guardRow, guardCol, 0, 1, guardDir)) { // right
          counter++;
        }
        guardRow--;
      } else if (data[guardRow][guardCol] == '>') {
        if (checkRight(data, guardRow, guardCol, 1, 0, guardDir)) { // down
          counter++;
        }
        guardCol++;
      } else if (data[guardRow][guardCol] == 'v') {
        if (checkRight(data, guardRow, guardCol, 0, -1, guardDir)) { // left
          counter++;
        }
        guardRow++;
      } else if (data[guardRow][guardCol] == '<') {
        if (checkRight(data, guardRow, guardCol, -1, 0, guardDir)) { // up
          counter++;
        }
        guardCol--;
      }
    }

    System.out.println(String.format(
      "%dμs",
      (System.nanoTime() - start) / 1000)
    );
  }

  public static char peek(char[][] data, int row, int col, char dir) {
    try {
      switch(dir) {
        case '^': return data[row - 1][col];
        case '>': return data[row][col + 1];
        case 'v': return data[row + 1][col];
        case '<': return data[row][col - 1];
        default: {
          System.out.println("Peek: Invalid input");
          return dir;
        }
      }
    } catch (ArrayIndexOutOfBoundsException error) {
      return 'x';
    }
  }

  /**
   * Check line for characters using scalar. Return true if the character is
   * found in the line, is a wall, or a turn.
   */
  public static boolean checkRight(char[][] data, int row, int col, int vir, int hor, char dir) {
    int x = 1;
    char prev = data[row][col];
    final char turn = turnRight(dir);
    final char opposite = turnRight(turn);
    while (row + x*vir >= 0 && row + x*vir < data.length
      && col + x*hor >= 0 && col + x*hor < data[row].length
    ) {
      final char pos = data[row+x*vir][col+x*hor];
      if ((pos == '#' && prev == opposite) || pos == turn) {
        return true;
      }
      prev = pos;
      x++;
    }
    return false;
  }

  public static char turnRight(char dir) {
    switch(dir) {
      case '^': return '>';
      case '>': return 'v';
      case 'v': return '<';
      case '<': return '^';
      default: {
        System.out.println("turnRight: Invalid input");
        return dir;
      }
    }
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