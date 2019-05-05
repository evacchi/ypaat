package io.github.evacchi.bpmn.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.github.evacchi.bpmn.graph.Edge;
import io.github.evacchi.bpmn.graph.GraphBuilder;
import io.github.evacchi.bpmn.graph.Node;
import io.github.evacchi.bpmn.graph.nodes.StartEventNode;

public class EngineGraph {

    private final String name;
    private final StartEventNode start;
    private final Map<Node<?>, Collection<Node<?>>> outgoingEdges;
    private final Collection<? extends Node<?>> nodes;
    private final Collection<Edge> edges;

    public static EngineGraph of(GraphBuilder graphBuilder) {
        Map<Node<?>, Collection<Node<?>>> outgoingEdges = new HashMap<>();
        List<Node<?>> pending = new ArrayList<>(graphBuilder.nodes());
        HashSet<Node<?>> done = new HashSet<>();
        while (!pending.isEmpty()) {
            Node<?> curr = pending.remove(0);
            if (done.contains(curr)) {
                continue;
            }
            done.add(curr);
            graphBuilder.outgoing(curr).forEach(e -> {
                outgoingEdges.computeIfAbsent(curr, n -> new ArrayList<>()).add(e);
                pending.add(e);
            });
        }
        return new EngineGraph(
                graphBuilder.name(),
                graphBuilder.start(),
                outgoingEdges,
                graphBuilder.nodes(),
                graphBuilder.edges()
        );
    }

    public EngineGraph(
            String name,
            StartEventNode start,
            Map<Node<?>, Collection<Node<?>>> outgoingEdges,
            Collection<? extends Node<?>> nodes,
            Collection<Edge> edges) {
        this.name = name;
        this.start = start;
        this.outgoingEdges = outgoingEdges;
        this.nodes = nodes;
        this.edges = edges;
    }

    public Collection<Node<?>> outgoing(Node<?> node) {
        return outgoingEdges.get(node);
    }

    public StartEventNode start() {
        return start;
    }

    public String name() {
        return name;
    }

    public Collection<? extends Node<?>> nodes() {
        return nodes;
    }

    public Collection<Edge> edges() {
        return edges;
    }
}
