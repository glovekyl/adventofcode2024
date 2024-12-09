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

public class Part1 {
  /** Answer: 4135 */
  public static void main(String[] args) {
    // Data contains lines of rules, and reports. Rules follow format "X|Y", and
    // reports contain a string of integers "X,Y,Z". Rules and reports are
    // separated by a single blank line.
    List<String> data = parseFile("src/day5/test.txt");
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
    // Process the reports based on the graph. If it is a valid report, grab the
    // middle value and add it to the total.
    while(it.hasNext()) {
      int[] report = Arrays.stream(it.next().split(","))
        .mapToInt(str -> Integer.parseInt(str))
        .toArray();
      int x = processReport(graph, report);
      if (x > 0) {
        System.out.println(Arrays.toString(report));
      }
      total += x;
    }
    System.out.println(total);
  }

  /** Process a report and return the middle value */
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