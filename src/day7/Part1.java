package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.Arrays;

import core.Result;

public class Part1 {
  /** Answer: 2664460013123 */
  public static void main(String[] args) {
    final long[][] data = parseFile("src/day7/input.txt");
    final long start = System.nanoTime();

    long output = Arrays.stream(data)
      .filter(Part1::isValid)
      .mapToLong(arr -> arr[0])
      .sum();
    System.out.println(output);

    System.out.println(String.format(
      "%dμs",
      (System.nanoTime() - start) / 1000)
    );
  }

  /** Validate first number in array is achieveable using subsequent values */
  private static boolean isValid(final long[] nums) {
    boolean valid = isValid(nums, 1, 0);
    return valid;
  }

  private static boolean isValid(final long[] nums, int index, long curr) {
    if (index == nums.length) {
      return curr == nums[0];
    }
    long num = nums[index];
    return isValid(nums, index + 1, curr + num) // addition
      || isValid(nums, index + 1, curr * num); // multiplication
  }

  /** Read complete file contents. */
  public static Result<long[][], IOException> readFile(String path) {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return Result.success(lines.map(line -> Arrays.stream(
          line
            .replaceFirst(":", "")
            .split("\\s+")
        ).mapToLong(Long::parseLong).toArray()
      ).toArray(long[][]::new));
    } catch (IOException error) {
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static long[][] parseFile(String filePath) {
    final Result<long[][], IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new long[0][0];
    }
    return result.getSuccess();
  }
}