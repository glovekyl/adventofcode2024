package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import core.Result;

public class Part1 {
  /**
   * Answer: 334
   */
  public static void main(String[] args) {
    final String[] reports = parseFile("src/day2/input.txt");

    final int safeReports = (int) Arrays.stream(reports)
      .filter(Part1::isSafe)
      .count();

    System.out.println(safeReports);
  }

  /**
   * Check if the report is safe. A report should contain a range of values that
   * are either increasing or decreasing, and differ by a range 1 to 3. Unsafe
   * reports are those that do not meet these criteria.
   */
  public static boolean isSafe(String report) {
    final int[] levels = Arrays.stream(report.split("\s+"))
      .mapToInt(Integer::parseInt)
      .toArray();
    final int direction = (levels[0] > levels[1]) ? -1 : 1;
    for (int i = 1; i < levels.length; i++) {
      final int diff = (levels[i-1] - levels[i]) * -direction;
      if (diff <= 0 || diff > 3) {
        return false;
      }
    }
    return true;
  }

  /** Read complete file contents. */
  public static Result<String, IOException> readFile(String path) {
    try {
      final String content = Files.readString(Path.of(path));
      return Result.success(content);
    } catch (IOException error) {
      System.out.println("An error occurred.");
      error.printStackTrace();
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static String[] parseFile(String filePath) {
    final Result<String, IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new String[0];
    }
    return result.getSuccess().trim().split("\n");
  }
}