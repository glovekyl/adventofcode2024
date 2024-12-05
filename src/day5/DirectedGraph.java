package day5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DirectedGraph {
  private final Map<Integer, List<Integer>> graph;

  public DirectedGraph(Map<Integer, List<Integer>> graph) {
    this.graph = graph;
  }

  public List<Integer> addNode(int node) {
    if (!this.graph.containsKey(node)) {
      return this.graph.put(node, new ArrayList<>());
    }
    return this.graph.get(node);
  }

  public void addEdge(int from, int to) {
    if (!graph.containsKey(from)) {
      this.graph.put(from, new ArrayList<>());
    }
    this.graph.get(from).add(to);
  }
}
