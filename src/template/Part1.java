package template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import core.Result;

public class Part1 {
  /** Answer: n/a */
  public static void main(String[] args) {
    final String[] data = parseFile("src/template/input.txt");
    System.out.println("Hello world!");
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