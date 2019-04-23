package io.github.evacchi.bpmn.graph.bpmn;

import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.graph.FlowNode;
import io.github.evacchi.bpmn.graph.GraphVisitor;

public class StartEventNode extends FlowNode<TStartEvent> {

    public StartEventNode(String id, TStartEvent el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
