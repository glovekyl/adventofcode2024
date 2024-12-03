package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import core.Result;

public class Part1 {
  /** Answer: 173419328 */
  public static void main(String[] args) {
    final String[] data = parseFile("src/day3/input.txt");
    final Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
    int total = 0;
    for (String line : data) {
      final Matcher matcher = pattern.matcher(line);
      while (matcher.find()) {
        final String match = matcher.group();
        final String[] nums = match.substring(4, match.length()-1).split(",");
        total += Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);
      }
    }
    System.out.println(total);
  }

  /** Read complete file contents. */
  public static Result<String[], IOException> readFile(String path) {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return Result.success(lines.toArray(String[]::new));
    } catch (IOException error) {
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static String[] parseFile(String filePath) {
    final Result<String[], IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new String[0];
    }
    return result.getSuccess();
  }
}