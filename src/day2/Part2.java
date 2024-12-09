package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import core.Result;

public class Part2 {
  /**
   * Answer: 400
   */
  public static void main(String[] args) {
    final int[][] reports = parseFile("src/day2/input.txt");

    // final int safeReports = (int) Arrays.stream(reports)
    //   .filter((report) -> isSafe(report) || isDampenedSafe(report))
    //   .count();

    // Convert to array of unsafe reports. Dampeing is not required.
    final int[][] unsafeReports = Arrays.stream(reports)
      .filter((report) -> !isSafe(report))
      .toArray(int[][]::new);
    
    final int[][] dampenedReports = Arrays.stream(unsafeReports)
      .filter((report) -> isDampenedSafe(report))
      .toArray(int[][]::new);

    System.out.println(Arrays.toString(dampenedReports[0]) + " " + dampenedReports.length);
  }

  /**
   * Check if the report is safe. A report should contain a range of values that
   * are either increasing or decreasing, and differ by a range 1 to 3. Unsafe
   * reports are those that do not meet these criteria.
   */
  public static boolean isSafe(int[] report) {
    final int direction = (report[0] > report[1]) ? -1 : 1;
    for (int i = 1; i < report.length; i++) {
      final int diff = (report[i-1] - report[i]) * -direction;
      if (diff <= 0 || diff > 3) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the report is safe with one dampened value. Uses a brute force
   * approach to check all possible dampened values.
   * 
   * <p>TODO: Optimize this method.
   * 
   * <p>I believe this method can be optimized by using two pointers a sliding
   * window.
   */
  public static boolean isDampenedSafe(int[] report) {
    for (int i = 0; i < report.length; i++) {
      List<Integer> dampened = Arrays.stream(report)
        .boxed()
        .collect(Collectors.toCollection(ArrayList::new));
      dampened.remove(i);
      if (isSafe(dampened.stream().mapToInt(x -> x).toArray())) {
        return true;
      }
    }
    return false;
  }

  public static boolean isDampenedSafe2(int[] report) {
    final int direction = (report[0] > report[1]) ? -1 : 1;
    for (int i = 1; i < report.length; i++) {
      final int diff = (report[i-1] - report[i]) * -direction;
      if (diff <= 0 || diff > 3) {
        return false;
      }
    }
    return true;
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