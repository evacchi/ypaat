package io.github.evacchi.bpmn.graph.nodes;

import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.graph.Node;

public class FlowNode<T extends TFlowElement> extends Node<T> {

    public FlowNode(String id, T el) {
        super(id, el);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", id(), element().getName());
    }
}
