package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import core.Result;

public class Part1 {
  /** Answer: 4789 */
  public static void main(String[] args) {
    final char[][] data = parseFile("src/day6/input.txt");
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
    // Compute guard route, turning right when encountering object
    while (guardRow >= 0 && guardRow < data.length
      && guardCol >= 0 && guardCol < data[0].length
    ) {
      // Mark the guard's distinct position
      if (data[guardRow][guardCol] != 'X') {
        data[guardRow][guardCol] = 'X';
        counter++;
      }

      // Update guard position
      if (guardDir == '^') {
        if (guardRow - 1 >= 0 && data[guardRow - 1][guardCol] == '#') {
          guardDir = turnRight(guardDir);
        } else {
          guardRow--;
        }
      } else if (guardDir == '>') {
        if (guardCol + 1 < data[0].length && data[guardRow][guardCol + 1] == '#') {
          guardDir = turnRight(guardDir);
        } else {
          guardCol++;
        }
      } else if (guardDir == 'v') {
        if (guardRow + 1 < data.length && data[guardRow + 1][guardCol] == '#') {
          guardDir = turnRight(guardDir);
        } else {
          guardRow++;
        }
      } else if (guardDir == '<') {
        if (guardCol - 1 >= 0 && data[guardRow][guardCol - 1] == '#') {
          guardDir = turnRight(guardDir);
        } else {
          guardCol--;
        }
      }
    }
    System.out.println(counter);

    System.out.println(String.format(
      "%dμs",
      (System.nanoTime() - start) / 1000)
    );
  }

  public static char turnRight(char dir) {
    switch(dir) {
      case '^': return '>';
      case '>': return 'v';
      case 'v': return '<';
      case '<': return '^';
      default: {
        System.out.println("Invalid input");
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