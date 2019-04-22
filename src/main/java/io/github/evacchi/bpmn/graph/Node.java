package io.github.evacchi.bpmn.graph;

public class Node<T> {

    private final String id;
    private final T el;

    public Node(String id, T el) {
        this.id = id;
        this.el = el;
    }

    public String id() {
        return id;
    }

    public T element() {
        return el;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
