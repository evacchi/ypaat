package io.github.evacchi.bpmn.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.github.evacchi.bpmn.graph.GraphBuilder;
import io.github.evacchi.bpmn.graph.Node;
import io.github.evacchi.bpmn.graph.bpmn.StartEventNode;

public class EngineGraph {

    private final String name;
    private final StartEventNode start;
    private final Map<Node<?>, Collection<Node<?>>> edges;

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
                outgoingEdges);
    }

    public EngineGraph(String name, StartEventNode start, Map<Node<?>, Collection<Node<?>>> outgoingEdges) {
        this.name = name;
        this.start = start;
        this.edges = outgoingEdges;
    }

    public Collection<Node<?>> outgoing(Node<?> node) {
        return edges.get(node);
    }

    public StartEventNode start() {
        return start;
    }

    public String name() {
        return name;
    }
}
