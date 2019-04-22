package io.github.evacchi.bpmn.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {

    Map<String, Node<?>> nodes = new HashMap<>();
    List<Edge> edges = new ArrayList<>();

    public GraphBuilder(List<Node<?>> nodes) {
        nodes.forEach(n -> this.nodes.put(n.id(), n));
    }

    public void add(FlowNode node) {
        nodes.put(node.id(), node);
    }

    public void addEdge(String leftId, String rightId) {
        edges.add(new Edge(nodes.get(leftId), nodes.get(rightId)));
    }

    @Override
    public String toString() {
        return String.format("GraphBuilder(nodes=%s, edges=%s)", nodes, edges);
    }
}
