package io.github.evacchi.bpmn.draw;

import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphVisitor;
import io.github.evacchi.bpmn.graph.SubGraph;

public class SubProcessNode extends SubGraph {

    public SubProcessNode(String id, EngineGraph el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
