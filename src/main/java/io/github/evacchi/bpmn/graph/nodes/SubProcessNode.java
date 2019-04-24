package io.github.evacchi.bpmn.graph.nodes;

import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphVisitor;

public class SubProcessNode extends SubGraph {

    public SubProcessNode(String id, EngineGraph el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
