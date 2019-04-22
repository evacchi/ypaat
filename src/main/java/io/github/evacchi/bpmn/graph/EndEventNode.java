package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.TEndEvent;

public class EndEventNode extends FlowNode<TEndEvent> {

    public EndEventNode(String id, TEndEvent el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
