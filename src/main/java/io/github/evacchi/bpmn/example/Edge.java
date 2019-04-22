package io.github.evacchi.bpmn.example;

public class Edge {
    private final Node<?> left, right;

    public Edge(Node<?> left, Node<?> right) {
        this.left = left;
        this.right = right;
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
