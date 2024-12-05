package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import core.Result;

public class Part2 {
  /** Answer: 5285 */
  public static void main(String[] args) {
    // Data contains lines of rules, and reports. Rules follow format "X|Y", and
    // reports contain a string of integers "X,Y,Z". Rules and reports are
    // separated by a single blank line.
    List<String> data = parseFile("src/day5/input.txt");
    Iterator<String> it = data.iterator();

    Map<Integer, List<Integer>> graph = new HashMap<>();
    DirectedGraph directed = new DirectedGraph(graph);

    // Read in the rules and add them to the graph
    while (it.hasNext()) {
      String rule = it.next();
      if (rule.isEmpty()) {
        break;
      }

      int[] nums = Arrays.stream(rule.split("\\|"))
        .mapToInt(str -> Integer.parseInt(str))
        .toArray();
      directed.addNode(nums[0]);
      directed.addNode(nums[1]);
      directed.addEdge(nums[0], nums[1]);
    }

    int total = 0;
    while(it.hasNext()) {
      int[] report = Arrays.stream(it.next().split(","))
        .mapToInt(str -> Integer.parseInt(str))
        .toArray();
      
        if (processReport(graph, report) != 0) {
          continue;
        }
        total += processInvalidReport(graph, report);
    }
    System.out.println(total);
  }

  public static int processReport(
    Map<Integer, List<Integer>> graph,
    int[] report
  ) {
    for (int i=1; i < report.length; i++) {
      if (!graph.get(report[i-1]).contains(report[i])) {
        return 0;
      }
    }
    return report[(report.length+1) / 2 - 1];
  }

  /**
   * Process an assumed invalid report with modified bubble sort and return the
   * value of the middle number.
   * 
   * TODO: Implement a faster sorting algorithm.
   * https://rcoh.me/posts/linear-time-median-finding/
   */
  public static int processInvalidReport(
    Map<Integer, List<Integer>> graph,
    int[] report
  ) {
    for (int i=0; i < report.length; i++) {
      boolean isSwapped = false;
      for (int j=0; j < report.length; j++) {
        if (!graph.get(report[i]).contains(report[j])) {
          int temp = report[j];
          report[j] = report[i];
          report[i] = temp;
          isSwapped = true;
        }
      }
      if (!isSwapped) {
        break;
      }
    }
    return report[(report.length+1) / 2 - 1];
  }

  /** Read complete file contents. */
  public static Result<List<String>, IOException> readFile(String path) {
    try (Stream<String> lines = Files.lines(Path.of(path))) {
      return Result.success(lines.toList());
    } catch (IOException error) {
      return Result.error(error);
    }
  }

  /** Parse file contents and format into usable data. */
  public static List<String> parseFile(String filePath) {
    final Result<List<String>, IOException> result = readFile(filePath);
    if (result.isError()) {
      System.out.println("An error occurred while reading the file.");
      return new ArrayList<>(0);
    }
    return result.getSuccess();
  }
}
