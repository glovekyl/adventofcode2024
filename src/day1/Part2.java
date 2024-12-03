package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import core.Result;

public class Part2 {
  /**
   * Answer: 23741109
   */
  public static void main(String[] args) {
    Result<String, IOException> result = readFile("src/day1/input.txt");
    if (result.isError()) {
      return;
    }

    String[] lines = result.getSuccess().trim().split("\n");
    int[] left = new int[lines.length];
    HashMap<Integer, Integer> right = new HashMap<>();
    for (int i = 0; i < lines.length; i++) {
      String[] parts = lines[i].split("\s+");
      left[i] = Integer.parseInt(parts[0]);
      right.merge(Integer.parseInt(parts[1]), 1, Integer::sum);
    }
    
    int total = 0;
    for (int n : left) {
      total += n * right.getOrDefault(n, 0);
    }
    System.out.println(total);
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