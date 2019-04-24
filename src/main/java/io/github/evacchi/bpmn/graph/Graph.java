package io.github.evacchi.bpmn.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import io.github.evacchi.bpmn.TSequenceFlow;
import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.graph.nodes.StartEventNode;

public class Graph {

    private Map<String, Node<?>> nodes = new HashMap<>();
    private List<Edge> edges = new ArrayList<>();

    private String name;
    private StartEventNode start;

    public Graph(String name) {
        this.name = name;
    }

    public void add(Node<?> node) {
        nodes.put(node.id(), node);
        if (node.element() instanceof TStartEvent) {
            start = (StartEventNode) node;
        }
    }

    public void addEdge(String id, TSequenceFlow seq, String leftId, String rightId) {
        edges.add(new Edge(id, seq, nodes.get(leftId), nodes.get(rightId)));
    }

    @Override
    public String toString() {
        return String.format("GraphBuilder(nodes=%s, edges=%s)", nodes, edges);
    }

    public Node<?> node(String id) {
        return nodes.get(id);
    }

    public Collection<? extends Node<?>> nodes() {
        return nodes.values();
    }

    public StartEventNode start() {
        return start;
    }

    public String name() {
        return name;
    }

    public Stream<Node<?>> outgoing(Node<?> node) {
        return edges.stream().filter(e -> e.left() == node).map(Edge::right);
    }

    public Collection<Edge> edges() {
        return Collections.unmodifiableCollection(edges);
    }
}
