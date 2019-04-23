package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.TSequenceFlow;

public class Edge {

    private final String id;
    private final TSequenceFlow seq;
    private final Node<?> left;
    private final Node<?> right;

    public Edge(String id, TSequenceFlow seq, Node<?> left, Node<?> right) {
        this.id = id;
        this.seq = seq;
        this.left = left;
        this.right = right;
    }

    public String id() {
        return id;
    }

    public Node<?> left() {
        return left;
    }

    public Node<?> right() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("%s->%s", left.id(), right.id());
    }
}
