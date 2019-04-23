package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.engine.EngineGraph;

public class SubProcessNode extends SubGraph {

    public SubProcessNode(String id, EngineGraph el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
