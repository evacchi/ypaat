package io.github.evacchi.bpmn.draw;

import io.github.evacchi.bpmn.TEndEvent;
import io.github.evacchi.bpmn.graph.nodes.FlowNode;
import io.github.evacchi.bpmn.graph.GraphVisitor;

public class EndEventNode extends FlowNode<TEndEvent> {

    public EndEventNode(String id, TEndEvent el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
