package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.TStartEvent;

public class StartEventNode extends FlowNode<TStartEvent> {

    public StartEventNode(String id, TStartEvent el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }
}
