package template;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import core.Result;

public class Part2 {
  /**
   * Answer: null
   */
  public static void main(String[] args) {
    Result<String, IOException> result = readFile("src/template/input.txt");
    if (result.isError()) {
      return;
    }

    System.out.println("Hello world");
  }

  /**
   * Read complete file contents.
   * @param path String Relative path to the file
   * @return String Unformatted file content
   * @throws IOException
   */
  public static Result<String, IOException> readFile(String path) {
    try {
      String content = Files.readString(Path.of(path));
      return Result.success(content);
    } catch (IOException error) {
      System.out.println("An error occurred.");
      error.printStackTrace();
      return Result.error(error);
    }
  }
}