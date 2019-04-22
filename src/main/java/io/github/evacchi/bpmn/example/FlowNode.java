package io.github.evacchi.bpmn.example;

import io.github.evacchi.bpmn.TFlowElement;

class FlowNode extends Node<TFlowElement> {

    public FlowNode(String id, TFlowElement el) {
        super(id, el);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", id(), element().getName());
    }
}
